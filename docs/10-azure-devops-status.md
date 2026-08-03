# Azure DevOps Implementation Status

## Foundation synchronization completed on 2026-08-03

- The repository documentation was confirmed as the authoritative source.
- [`azure-devops/backlog-sync.csv`](../azure-devops/backlog-sync.csv) was imported and Azure DevOps reported that all 65 work items were saved successfully in place for IDs `2`–`66`.
- The full Epic → Feature → User Story hierarchy is materialized through Azure parent relation records.
- All 41 stories contain the documented Acceptance Criteria, Story Points, Priority, requirements in the description, area, iteration and tags.
- Feature Priority is derived from its most urgent child story; Epic Priority is derived from its most urgent descendant story. This supplies Azure's required field without inventing a separate priority model.
- [`azure-devops/Generate-BacklogSync.ps1`](../azure-devops/Generate-BacklogSync.ps1) deterministically regenerates and validates the update file from the authoritative backlog documents.

## Completed on 2026-08-03

- Converted the `MGParts` Azure DevOps project from the Basic process to Agile.
- Created 65 MVP work items in area path `MGParts`.
- Created 7 Epics (`E01`–`E07`) as Azure IDs `2`–`8`.
- Created 17 Features (`F01`–`F17`) as Azure IDs `9`–`25`.
- Created 41 User Stories (`US001`–`US041`) as Azure IDs `26`–`66`.
- Left the pre-existing Azure ID `1`, `test work item`, unchanged because it is outside this scope.
- Materialized all 17 Feature-to-Epic and all 41 Story-to-Feature parent relations.

## Live verification

- Azure's import result reported `Successfully saved 65 work items`.
- Story `US001` / ID `26` was checked with Acceptance Criteria, 5 Story Points, Priority 1, four expected tags, and Feature `F01` / ID `9` as its parent.
- Story `US041` / ID `66` was checked with Acceptance Criteria, 13 Story Points, Priority 2, the RAG and Agentic-AI tags, and Feature `F17` / ID `25` as its parent.
- Feature `F17` / ID `25` was checked with Priority 2 and Epic `E07` / ID `8` as its parent.
- Epic `E01` / ID `2` was checked with its derived Priority 1.
- The live Stories backlog displayed both the first scoped story (`US001`) and the last (`US041`) with their synchronized estimates and tags.

## Azure ID map

| Parent | Child Azure IDs |
|---|---|
| E01 / 2 | F01–F02 / 9–10 |
| E02 / 3 | F03–F05 / 11–13 |
| E03 / 4 | F06–F07 / 14–15 |
| E04 / 5 | F08–F09 / 16–17 |
| E05 / 6 | F10–F12 / 18–20 |
| E06 / 7 | F13–F15 / 21–23 |
| E07 / 8 | F16–F17 / 24–25 |
| F01 / 9 | US001–US003 / 26–28 |
| F02 / 10 | US004–US005 / 29–30 |
| F03 / 11 | US006–US007 / 31–32 |
| F04 / 12 | US008–US009 / 33–34 |
| F05 / 13 | US010–US011 / 35–36 |
| F06 / 14 | US012–US014 / 37–39 |
| F07 / 15 | US015–US017 / 40–42 |
| F08 / 16 | US018–US019 / 43–44 |
| F09 / 17 | US020–US022 / 45–47 |
| F10 / 18 | US023–US025 / 48–50 |
| F11 / 19 | US026–US029 / 51–54 |
| F12 / 20 | US030–US031 / 55–56 |
| F13 / 21 | US032–US033 / 57–58 |
| F14 / 22 | US034–US035 / 59–60 |
| F15 / 23 | US036–US037 / 61–62 |
| F16 / 24 | US038–US039 / 63–64 |
| F17 / 25 | US040–US041 / 65–66 |

## Reproduction and repair

Use [backlog-import.csv](../azure-devops/backlog-import.csv) only as the original source-controlled creation backup. Regenerate [backlog-sync.csv](../azure-devops/backlog-sync.csv) with the PowerShell generator and use that file for later synchronization because it preserves Azure IDs and updates items in place. After any future import, repeat the hierarchy and representative-field checks above.
