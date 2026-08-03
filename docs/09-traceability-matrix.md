# Requirements Traceability and Risk Register

## Traceability matrix

| Requirement group | Business rules / quality | Backlog coverage | Primary verification |
|---|---|---|---|
| Market and localization FR-001–FR-007 | BR-001–BR-003, BR-019; NFR-005–NFR-006 | F01–F02; US001–US005 | UI E2E matrix, localization lint, accessibility and visual checks. |
| Vehicle/catalog FR-010–FR-016 | BR-010–BR-011 | F03–F04; US006–US009 | API contract, fixture integrity, browse E2E. |
| Search FR-020–FR-026 | BR-019 | F06; US012–US014 | Search unit/relevance set, API integration, multilingual E2E. |
| Product/fitment FR-030–FR-036 | BR-010–BR-013 | F05, F08; US010–US011, US018–US019, US022 | Domain unit, API integration, product UI and property-based quantity tests. |
| Price/stock FR-037–FR-039 | BR-002–BR-006, BR-014–BR-016; NFR-008 | F09; US020–US022 | Money property tests, concurrency tests, reconciliation E2E. |
| Cart FR-040–FR-046 | BR-009, BR-013–BR-016 | F10; US023–US025 | API integration, state-transition and cross-locale/currency E2E. |
| Checkout/order FR-050–FR-060 | BR-006–BR-009, BR-014–BR-018 | F11–F12; US026–US031 | Address schema tests, quote tests, idempotency/concurrency and privacy/security E2E. |
| Operator/test FR-070–FR-075 | NFR-007, NFR-009–NFR-012 | F13–F15; US032–US037 | Authorization, schema/import, reset checksum, event and fault-control tests. |
| Knowledge/RAG FR-080–FR-085 | BR-020–BR-022 | F07, F17; US015–US017, US041 | Corpus validation, retrieval metrics, citation/faithfulness/abstention evaluation. |
| Cross-cutting quality | NFR-001–NFR-012 and Definition of Done | F16–F17; US038–US041 | CI gates, performance, accessibility, security and agent regression suites. |

## Dependency map

| Upstream | Downstream |
|---|---|
| Market/context (F01) | All catalog, quote, checkout and knowledge features. |
| Fixture/reset (F13/US033) | Automated functional, NFR and AI evaluation. |
| Vehicle/catalog (F03–F05) | Search, product detail, cart compatibility and RAG cases. |
| Price/stock (F09) | Cart, shipping/tax quote and order. |
| API/error contracts (F15) | UI implementation and all test automation. |
| Knowledge publication (F07/US015) | Retrieval, assistant and golden evaluation. |
| Correlation/events (F14/US034) | Agent evaluation and failure diagnosis. |

## Risk register

| ID | Risk | Probability / impact | Mitigation / owner decision |
|---|---|---|---|
| R-01 | MG trademarks, catalog text or images are used without permission. | Medium / High | Use original demo content and generic imagery until rights are confirmed. Product owner. |
| R-02 | Unknown tax/shipping values are mistaken for real policy. | High / High | Prominent demo labeling, versioned fixture rules, production launch blocked pending legal/commercial confirmation. |
| R-03 | Multi-currency rounding causes UI/API/order mismatch. | Medium / High | Integer minor units, decimal rates, single rounding boundary and property-based tests. |
| R-04 | Arabic translation or RTL defects make critical flows unusable. | Medium / High | Translation completeness CI, native review when available, accessibility/visual matrix. |
| R-05 | Fitment data is inaccurate and the app presents unsafe guidance. | Medium / High | Fixture-only claims, explanation codes, ambiguous status and no production compatibility claim. |
| R-06 | Agents create duplicate orders after timeout. | Medium / High | Idempotency keys, immutable cart version, concurrency tests and scenario A04. |
| R-07 | Test support endpoints leak into production. | Low / Critical | Build/environment kill switch, authorization, automated deployment assertion and security test. |
| R-08 | RAG gives current-price/stock answers from stale documents. | High / High | Enforce live-tool boundary, provenance, effective-date filters and prohibited-claim evals. |
| R-09 | Prompt injection in product/knowledge content influences agents. | Medium / High | Treat content as untrusted, injection fixtures, tool policy and adversarial evals. |
| R-10 | Real personal data is entered during training. | Medium / High | Synthetic fixtures, UI warning, log masking, reset/purge and no production integration. |
| R-11 | Azure process conversion leaves the pre-existing Basic Issue/board mapping inconsistent. | Medium / Low | Keep the test Issue outside MVP, validate Agile backlog levels, manually retire/convert item 1 if needed. |
| R-12 | Backlog scope is too large for an MVP. | Medium / Medium | Preserve the four delivery waves; defer Priority 2 items only with documented impact on test value. |

## Open questions / decisions before implementation

1. Confirm the initial MG model/year/engine fixture list for Egypt and the UK.
2. Confirm whether the demo will be publicly hosted and what trademark/content permissions apply.
3. Approve explicit fictional tax/shipping/FX seed values and the visual “demonstration data” label.
4. Choose the technology stack and deployment target; requirements remain technology-neutral.
5. Decide whether the optional assistant endpoint is implemented in the first release or only the RAG corpus/retrieval/evaluation layer.
6. Choose the order-lookup verification field for synthetic testing (for example email or phone suffix) without creating weak production guidance.
7. Confirm retention and reset cadence for demonstration orders.

## Release acceptance summary

The MVP is acceptable when Priority 1 stories are complete, the anonymous COD path passes in all four market/language combinations (`EG-ar`, `EG-en`, `GB-en`, `GB-ar`), price/order math is reproducible, the environment resets deterministically, critical accessibility/security defects are closed and the first RAG golden run is reproducible with valid citations and abstentions.

