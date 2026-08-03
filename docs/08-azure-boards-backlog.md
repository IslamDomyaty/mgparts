# Azure Boards MVP Backlog

## Configuration

- Organization/project: `Islamabdalazez / MGParts`
- Process: Agile
- Area path: `MGParts`
- Default iteration: `MGParts`
- Suggested release tag: `MGParts-MVP`
- Priority: 1 = required for critical path, 2 = required for MVP quality/operations, 3 = desirable if capacity permits.

The Azure project was originally created with the Basic process and contained one pre-existing test Issue (`test work item`). The process was converted to Agile on 2026-08-03. The legacy Issue is not part of this backlog and should be manually retired or converted by the project owner if it appears broken.

The 65 scoped work items were created as Azure IDs `2`–`66`. On 2026-08-03, all 17 Feature-to-Epic and 41 Story-to-Feature parent relations were synchronized from this document and verified in Azure DevOps. The child descriptions retain the stable parent reference as additional traceability; see [10-azure-devops-status.md](10-azure-devops-status.md) for the live verification record and Azure ID map.

## Hierarchy summary

| Epic | Features | Outcome |
|---|---|---|
| E01 Market Experience and Localization | F01–F02 | Correct market, language, currency, RTL and accessible presentation. |
| E02 Vehicle Catalog and Fitment | F03–F05 | Customers browse a stable MG taxonomy and understand compatibility. |
| E03 Search, Discovery and Knowledge | F06–F07 | Searchable parts plus a versioned RAG-ready knowledge layer. |
| E04 Product, Price and Inventory | F08–F09 | Rich product variants with deterministic price, stock and quantity rules. |
| E05 Cart, COD Checkout and Orders | F10–F12 | Anonymous end-to-end order journey with safe reconciliation and lookup. |
| E06 Operations, Data and Test Controls | F13–F15 | Resettable fixtures, observability, faults, contracts and protected tooling. |
| E07 Quality Engineering and AI Evaluation | F16–F17 | Automated quality gates and repeatable agent/RAG evaluation. |

## E01 — Market Experience and Localization

### F01 — Market, language and currency context

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US001 | As a shopper, I can select Egypt or the UK so the store applies the correct market rules. | Context persists; changing market revalidates vehicle/cart; API exposes `EG`/`GB`. | 5 | 1 | FR-001, FR-006 |
| US002 | As a shopper, I can switch Arabic/English without losing my journey state. | Page/vehicle/cart remain; document language/direction change; no untranslated key is visible. | 5 | 1 | FR-002, FR-003, FR-007 |
| US003 | As a shopper, I can display EGP, GBP, USD or EUR prices. | Market defaults apply; currency switch does not change market; quote exposes FX snapshot and rounding. | 5 | 1 | FR-004–FR-005, FR-037–FR-038 |

### F02 — RTL, responsive and accessible storefront

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US004 | As an Arabic shopper, I receive a correct RTL experience on critical pages. | Layout/control direction is mirrored where meaningful; mixed numbers/references remain readable at target widths. | 5 | 1 | FR-003, NFR-006 |
| US005 | As a keyboard or assistive-technology user, I can complete the critical journey. | Semantic controls, focus, errors and status announcements meet agreed WCAG checks; stable test IDs exist. | 8 | 1 | NFR-005, testability contract |

## E02 — Vehicle Catalog and Fitment

### F03 — Anonymous vehicle context

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US006 | As a shopper, I can select MG model, year and optional engine. | Only valid seeded combinations are offered; incomplete engine data can result in ambiguous fitment. | 5 | 1 | FR-010, FR-032 |
| US007 | As a shopper, my selected vehicle persists and can be changed safely. | Selection appears on results/product pages; cart lines are re-evaluated and flagged, never silently deleted. | 5 | 1 | FR-011–FR-012, FR-045 |

### F04 — Catalog taxonomy and browsing

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US008 | As a shopper, I can browse categories and subcategories for my vehicle. | Stable IDs and localized names are used; breadcrumbs and URLs are deterministic. | 5 | 1 | FR-013–FR-014 |
| US009 | As a shopper, I receive stable pagination and useful empty/retired-category states. | Page size/order tie-breaker are documented; recovery actions are shown; no unhandled error. | 3 | 2 | FR-015–FR-016 |

### F05 — Fitment and part relationships

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US010 | As a shopper, I can see compatibility and an explanation for the selected vehicle. | All four fitment statuses are handled; ambiguous/incompatible are never presented as compatible. | 8 | 1 | FR-032, BR-010–BR-011 |
| US011 | As a shopper, I can understand alternative, replacement and related parts. | Relationship type is explicit; discontinued items are descriptive but not sellable. | 5 | 2 | FR-033, BR-012 |

## E03 — Search, Discovery and Knowledge

### F06 — Search, filter and sort

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US012 | As a shopper or mechanic, I can search by English/Arabic text or part reference. | Reference normalization ignores case/spaces/hyphens; original query is retained; Arabic synonyms are seeded. | 8 | 1 | FR-020–FR-022 |
| US013 | As a shopper, I can filter, sort and page through results. | Category, stock, fitment, price and type filters work with defined sort orders and stable pagination. | 8 | 1 | FR-023–FR-024 |
| US014 | As a shopper/tester, I receive intentional no-result/error states and safe match diagnostics. | Recovery actions exist; diagnostic reason is machine-readable only in authorized non-production context. | 5 | 2 | FR-025–FR-026 |

### F07 — Versioned knowledge and retrieval

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US015 | As a knowledge operator, I can publish versioned English/Arabic market-scoped sources. | Required provenance/checksum/effective/status fields are enforced; published versions are immutable. | 8 | 1 | FR-080–FR-083 |
| US016 | As an AI tester, I can retrieve filtered, ranked chunks with stable citations. | Wrong-market/locale/expired sources are filtered; response includes chunk/source/version metadata. | 8 | 1 | FR-081–FR-083, BR-020 |
| US017 | As an AI quality engineer, I have a golden multilingual RAG dataset. | Dataset covers answerable, unanswerable, conflicting, stale and injection cases with expected/forbidden evidence. | 5 | 2 | FR-084–FR-085 |

## E04 — Product, Price and Inventory

### F08 — Product detail and variants

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US018 | As a shopper, I can review localized product details and fitment. | Product shows references, specs, image, quantity rule and compatible vehicles; fallback/error states are defined. | 5 | 1 | FR-030, FR-036 |
| US019 | As a shopper, I can compare genuine, OE-equivalent and aftermarket variants. | Each variant has SKU, maker, origin/type, price and stock; labels do not imply unsupported authenticity. | 5 | 1 | FR-031, FR-033 |

### F09 — Deterministic price, stock and quantity rules

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US020 | As a shopper/tester, I receive a versioned, reproducible price quote. | Minor-unit arithmetic, FX/tax versions and single-boundary rounding are consistent across API/UI. | 8 | 1 | FR-037–FR-039, BR-002–BR-006 |
| US021 | As a shopper, I see current availability and cannot buy a non-sellable variant. | All stock states render; add is blocked for non-sellable; concurrent decrement is protected. | 8 | 1 | FR-034–FR-035, BR-014–BR-015 |
| US022 | As a shopper, quantity rules and price/stock changes are explicit. | Min/max/increment enforced server-side; stale quote/stock returns a reconciliation action. | 5 | 1 | FR-036, FR-039, BR-013, BR-016 |

## E05 — Cart, COD Checkout and Orders

### F10 — Anonymous cart and reconciliation

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US023 | As a shopper, I can add, update and remove cart lines. | Same SKU consolidates; quantities validated; remove/clear are idempotent; totals are server-calculated. | 8 | 1 | FR-040–FR-042, FR-044 |
| US024 | As a shopper, my cart persists across navigation, language and currency changes. | Lifetime documented; display quote updates without changing SKU/quantity/market. | 5 | 1 | FR-043–FR-044 |
| US025 | As a shopper, I can resolve incompatible, unavailable or repriced cart lines. | Flags and recovery actions are line-specific; checkout remains blocked until resolved; empty cart cannot proceed. | 8 | 1 | FR-045–FR-046 |

### F11 — Guest COD checkout

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US026 | As a guest, I can enter a valid Egypt or UK delivery address. | Market-specific fields/formats and localized errors work; no live address service or real test data is required. | 8 | 1 | FR-050–FR-052 |
| US027 | As a guest, I receive versioned demonstration shipping and tax quotes. | Market/zone/weight/oversize rules drive quote; unknown policy is clearly labeled; unavailable route blocks submission. | 8 | 1 | FR-053–FR-055, BR-006–BR-008 |
| US028 | As a guest, I can review the COD total and accept versioned policies. | Cash is the sole payment option; subtotal/tax/shipping/total and policy versions are visible before submit. | 5 | 1 | FR-050, FR-055–FR-056 |
| US029 | As a guest, I can place one order safely even when a response is retried. | Same idempotency key/cart version returns same order; stock decremented once; stale cart/policy is recoverable. | 13 | 1 | FR-057–FR-059, BR-014–BR-018 |

### F12 — Confirmation and secure order lookup

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US030 | As a guest, I receive a localized order confirmation and immutable reference. | Confirmation snapshots items/totals/rule versions and states clearly that no online payment occurred. | 5 | 1 | FR-058–FR-059 |
| US031 | As a guest, I can securely look up my demonstration order. | Reference plus verification value required; generic failures, throttling and masked output prevent enumeration. | 8 | 2 | FR-060, security requirements |

## E06 — Operations, Data and Test Controls

### F13 — Catalog administration and fixtures

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US032 | As an authorized operator, I can import/manage the fixture catalog and knowledge. | Validation covers IDs, translations, relations and referential integrity; audit event emitted; public users denied. | 13 | 2 | FR-070, FR-075 |
| US033 | As a tester, I can reset a non-production environment to a named fixture. | Authorized reset returns checksum/counts/time; operation is isolated, repeatable and unavailable in production. | 8 | 1 | FR-071, NFR-010–NFR-012 |

### F14 — Observability and controlled failures

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US034 | As a tester, I can trace a journey using correlation IDs and structured events. | Required events/schema versions exist; PII is masked; order retry shares correct causation context. | 8 | 1 | FR-073, NFR-009–NFR-010 |
| US035 | As a tester, I can activate bounded price, stock, latency and knowledge fault scenarios. | Controls are authorized, time-limited, auditable, resettable and disabled in production. | 8 | 2 | FR-072, fault-control contract |

### F15 — API contracts, authorization and privacy

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US036 | As an API consumer, I have OpenAPI contracts and stable error codes. | Contracts validate in CI; errors contain code/correlation/field details; localized text is not the assertion key. | 8 | 1 | FR-074, data/API conventions |
| US037 | As an owner, I can trust protected/test endpoints and customer data boundaries. | Authorization, non-sequential IDs, masking, input validation and secret scanning pass agreed tests. | 8 | 1 | FR-075, NFR-007 and security requirements |

## E07 — Quality Engineering and AI Evaluation

### F16 — Automated functional quality gates

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US038 | As a delivery team, I run unit, integration and contract suites in CI. | Money, fitment, stock, idempotency, search and schema tests cover positive/negative boundaries and publish results. | 8 | 1 | Definition of Done, NFR-008 |
| US039 | As a test team, I run end-to-end journeys across EG/UK and AR/EN. | Critical browse/search/cart/COD paths run on isolated fixtures with stable selectors and capture correlation IDs. | 13 | 1 | FR-001–FR-060, testability contract |

### F17 — Non-functional, RAG and agent evaluation gates

| Ref | User story | AC summary | SP | Priority | Requirements |
|---|---|---|---:|---:|---|
| US040 | As a quality owner, I run accessibility, responsive, performance and security gates. | Agreed thresholds are automated; artifacts are retained; critical/high failures block the release candidate. | 13 | 2 | NFR-001–NFR-008 |
| US041 | As an AI quality engineer, I run reproducible RAG and agent evaluations. | Run records fixture/corpus/model/tool/prompt versions; scores retrieval/citations/faithfulness/abstention/tool safety and compares baseline. | 13 | 2 | FR-080–FR-085, agent strategy |

## Suggested MVP delivery order

1. Foundation: US001–US008, US018, US020, US033, US036.
2. Discovery: US009–US017, US019, US021–US022.
3. Transaction: US023–US030, US034, US037–US039.
4. Hardening and AI lab: US031–US032, US035, US040–US041.

## Backlog readiness rules

A story is ready when dependencies are identified, acceptance criteria are testable, relevant fixture examples exist, Arabic/English and EG/UK applicability is stated, API/UI ownership is clear and no unresolved shipping/tax assumption prevents implementation. Story points are initial BA estimates and should be re-estimated by the delivery team.
