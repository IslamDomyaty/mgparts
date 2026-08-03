[CmdletBinding()]
param(
    [string]$OutputPath
)

$ErrorActionPreference = 'Stop'

if ([string]::IsNullOrWhiteSpace($OutputPath)) {
    $OutputPath = Join-Path $PSScriptRoot 'backlog-sync.csv'
}

$sourcePath = Join-Path $PSScriptRoot 'backlog-import.csv'
$backlogDocumentPath = Join-Path (Split-Path $PSScriptRoot -Parent) 'docs/08-azure-boards-backlog.md'

$sourceItems = @(Import-Csv -LiteralPath $sourcePath)
$sourceByReference = @{}

foreach ($item in $sourceItems) {
    if ($item.Title -notmatch '^(E\d{2}|F\d{2}|US\d{3})\b') {
        throw "Cannot read a stable reference from title '$($item.Title)'."
    }

    $reference = $Matches[1]
    if ($sourceByReference.ContainsKey($reference)) {
        throw "Duplicate stable reference '$reference' in $sourcePath."
    }

    $sourceByReference[$reference] = $item
}

$storyMetadata = @{}
foreach ($line in Get-Content -LiteralPath $backlogDocumentPath -Encoding UTF8) {
    if ($line -match '^\|\s*(US\d{3})\s*\|\s*([^|]+?)\s*\|\s*([^|]+?)\s*\|\s*(\d+)\s*\|\s*(\d+)\s*\|\s*([^|]+?)\s*\|$') {
        $storyMetadata[$Matches[1]] = [pscustomobject]@{
            AcceptanceCriteria = $Matches[3].Trim()
            StoryPoints       = [int]$Matches[4]
            Priority          = [int]$Matches[5]
            Requirements      = $Matches[6].Trim()
        }
    }
}

$epicToFeatures = [ordered]@{
    E01 = @('F01', 'F02')
    E02 = @('F03', 'F04', 'F05')
    E03 = @('F06', 'F07')
    E04 = @('F08', 'F09')
    E05 = @('F10', 'F11', 'F12')
    E06 = @('F13', 'F14', 'F15')
    E07 = @('F16', 'F17')
}

$featureToStories = [ordered]@{
    F01 = @('US001', 'US002', 'US003')
    F02 = @('US004', 'US005')
    F03 = @('US006', 'US007')
    F04 = @('US008', 'US009')
    F05 = @('US010', 'US011')
    F06 = @('US012', 'US013', 'US014')
    F07 = @('US015', 'US016', 'US017')
    F08 = @('US018', 'US019')
    F09 = @('US020', 'US021', 'US022')
    F10 = @('US023', 'US024', 'US025')
    F11 = @('US026', 'US027', 'US028', 'US029')
    F12 = @('US030', 'US031')
    F13 = @('US032', 'US033')
    F14 = @('US034', 'US035')
    F15 = @('US036', 'US037')
    F16 = @('US038', 'US039')
    F17 = @('US040', 'US041')
}

function Get-AzureId {
    param([Parameter(Mandatory)][string]$Reference)

    if ($Reference -match '^E(\d{2})$') {
        return 1 + [int]$Matches[1]
    }
    if ($Reference -match '^F(\d{2})$') {
        return 8 + [int]$Matches[1]
    }
    if ($Reference -match '^US(\d{3})$') {
        return 25 + [int]$Matches[1]
    }

    throw "Unsupported reference '$Reference'."
}

function New-SyncRow {
    param(
        [Parameter(Mandatory)][string]$Reference,
        [string]$Title1 = '',
        [string]$Title2 = '',
        [string]$Title3 = '',
        [string]$AcceptanceCriteria = '',
        [string]$StoryPoints = '',
        [string]$Priority = ''
    )

    $source = $sourceByReference[$Reference]
    if ($null -eq $source) {
        throw "No source item exists for '$Reference'."
    }

    $description = $source.Description
    if ($storyMetadata.ContainsKey($Reference)) {
        $description = "$description Requirements: $($storyMetadata[$Reference].Requirements)."
    }

    [pscustomobject][ordered]@{
        ID                    = Get-AzureId -Reference $Reference
        'Work Item Type'      = $source.'Work Item Type'
        'Title 1'             = $Title1
        'Title 2'             = $Title2
        'Title 3'             = $Title3
        Description           = $description
        'Acceptance Criteria' = $AcceptanceCriteria
        'Story Points'        = $StoryPoints
        Priority              = $Priority
        State                 = 'New'
        'Area Path'           = $source.'Area Path'
        'Iteration Path'      = $source.'Iteration Path'
        Tags                  = $source.Tags
    }
}

if ($sourceByReference.Count -ne 65) {
    throw "Expected 65 source work items, found $($sourceByReference.Count)."
}
if ($storyMetadata.Count -ne 41) {
    throw "Expected metadata for 41 user stories, found $($storyMetadata.Count)."
}

$rows = [System.Collections.Generic.List[object]]::new()

foreach ($epicReference in $epicToFeatures.Keys) {
    $epic = $sourceByReference[$epicReference]
    $rows.Add((New-SyncRow -Reference $epicReference -Title1 $epic.Title))

    foreach ($featureReference in $epicToFeatures[$epicReference]) {
        $feature = $sourceByReference[$featureReference]
        $rows.Add((New-SyncRow -Reference $featureReference -Title2 $feature.Title))

        foreach ($storyReference in $featureToStories[$featureReference]) {
            $story = $sourceByReference[$storyReference]
            $metadata = $storyMetadata[$storyReference]
            $rows.Add((New-SyncRow `
                -Reference $storyReference `
                -Title3 $story.Title `
                -AcceptanceCriteria $metadata.AcceptanceCriteria `
                -StoryPoints $metadata.StoryPoints `
                -Priority $metadata.Priority))
        }
    }
}

if ($rows.Count -ne 65) {
    throw "Expected 65 synchronization rows, generated $($rows.Count)."
}

$rows | Export-Csv -LiteralPath $OutputPath -NoTypeInformation -Encoding UTF8

Write-Output "Generated $($rows.Count) Azure Boards update rows at $OutputPath."
Write-Output "Hierarchy: $($epicToFeatures.Count) epics, $($featureToStories.Count) features, $($storyMetadata.Count) stories."
