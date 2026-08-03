# Azure DevOps Implementation Status

## Completed on 2026-08-03

- Converted the `MGParts` Azure DevOps project from the Basic process to Agile.
- Created 65 MVP work items in area path `MGParts`.
- Created 7 Epics (`E01`–`E07`) as Azure IDs `2`–`8`.
- Created 17 Features (`F01`–`F17`) as Azure IDs `9`–`25`.
- Created 41 User Stories (`US001`–`US041`) as Azure IDs `26`–`66`.
- Left the pre-existing Azure ID `1`, `test work item`, unchanged because it is outside this scope.
- Validated a real Azure parent relation by linking Feature `F01` (ID `9`) to Epic `E01` (ID `2`).

## Hierarchy implementation note

All child items contain their intended parent reference in the description, and the authoritative hierarchy is recorded in [08-azure-boards-backlog.md](08-azure-boards-backlog.md) and [backlog-import.csv](../azure-devops/backlog-import.csv). The remaining parent relations are not yet materialized as Azure relation records because the authenticated UI does not provide a safe bulk-link operation and local file upload was unavailable in the controlled browser session.

This does not affect story content, prioritization, area path, estimates or acceptance summaries. Before sprint planning, materialize the remaining parent links using Azure DevOps CSV import, the REST API or the documented parent map below.

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

## Suggested completion method

Use [backlog-import.csv](../azure-devops/backlog-import.csv) as the source-controlled backup. If importing into the existing project, use stable references in titles to match items rather than creating duplicates, then verify that every Feature has one Epic parent and every User Story has one Feature parent.
