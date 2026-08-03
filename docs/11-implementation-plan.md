# Implementation Plan and Progress

Last updated: 2026-08-03

This document is the persistent delivery handoff for MG Parts. It records approved decisions, feature order, progress, verification, and the exact continuation point for future tasks.

## Current position

| Item | Value |
|---|---|
| Active phase | Project foundation |
| Active branch | `codex/project-foundation` |
| Status | Draft PR open — local foundation complete; Azure import paused for Chrome file-upload permission |
| Base | Freshly fetched `origin/main` at `db1832de15d7377cffc4f2dc0ea1d32bb4851f04` |
| Pull request | [#1 — Document project foundation and Azure backlog sync](https://github.com/IslamDomyaty/mgparts/pull/1) (draft) |
| Application code | Not started |
| Local verification | 65 unique Azure IDs; 41/41 stories with acceptance/points/priority; deterministic CSV hash; `git diff --check` passed |
| Next action | Enable Chrome extension file-URL access, import and save `azure-devops/backlog-sync.csv`, verify hierarchy/story fields, then update PR #1 |
| Start-next-feature gate | F01 must not start until the foundation PR is merged and the product owner confirms continuation |

## Approved product decisions

- The repository documentation is authoritative. Azure Boards is synchronized from it.
- Deliver all 41 stories. Priority 1 defines the first functional release gate; Priority 2 completes later quality, operations, and AI scope. When an Azure Feature mixes priorities, its stories remain in one feature PR, so a related Priority 2 story may land before the release gate.
- The site is an unofficial educational demonstration and never processes real orders, money, payment credentials, or customer data.
- Use original demo copy, original or properly licensed generic imagery, no official MG logo, and a persistent unofficial-project notice.
- Initial vehicle families are MG3, MG4 EV, MG5, ZS, ZS EV, and HS with synthetic market/year/engine fixtures for Egypt and the UK.
- FX, tax, shipping, COD eligibility, price, stock, and fitment are fictional, versioned fixture data and visibly labeled as such.
- Order lookup uses order reference plus email and returns generic failures with masked output.
- Demonstration orders support an authorized manual reset and an automated nightly reset.
- Operator scope begins with protected import and API capabilities; a graphical administration portal is deferred.
- Build the versioned bilingual knowledge corpus, retrieval API, and evaluation dataset. Defer a generative assistant until the commerce path is stable.
- Create an original responsive automotive-parts design with Arabic RTL support.
- Deploy with Google Cloud Run and Neon PostgreSQL.

## Technical architecture

### Runtime and application shape

- Java 26 and Spring Boot 4.1.x. Spring Boot 4.1.0 is the initial baseline because it is a stable release that officially supports Java 26.
- Maven Wrapper with reproducible dependency/plugin versions.
- A modular monolith organized by business capability: context/localization, vehicles, catalog, fitment, products, pricing, inventory, search, cart, checkout/orders, fixtures/operations, observability, knowledge, and evaluation.
- Server-rendered Thymeleaf pages with progressive enhancement through HTMX and minimal JavaScript. This keeps the deployment simple while preserving accessible, testable interactions.
- Semantic HTML, CSS logical properties, design tokens, visible focus, stable `data-testid` values, and explicit loading/empty/error/stale/success states.

### Data, APIs, and security

- PostgreSQL in all persistent environments; Flyway owns schema evolution.
- Neon hosts the deployed database. Local integration tests use a matching PostgreSQL Testcontainer.
- Money uses integer minor units and ISO currency codes. FX uses fixed-precision decimal strings and one documented rounding boundary.
- OpenAPI is generated and validated in CI. Error responses use stable codes plus correlation IDs; translated messages are presentation only.
- Anonymous cart and order identifiers are opaque and non-sequential. Customer and idempotency secrets are masked in logs.
- Operator, reset, and fault-control endpoints require authorization and have a production kill switch.

### Test strategy

- Domain unit/property tests: money, rounding, quantity rules, fitment, state transitions, normalization, and policy selection.
- Persistence/API integration tests: PostgreSQL Testcontainers, Flyway, concurrency, idempotency, authorization, and OpenAPI validation.
- UI tests: Playwright across `EG-ar`, `EG-en`, `GB-en`, and `GB-ar`, with keyboard and critical accessibility checks.
- Quality gates: formatting/static analysis, dependency and secret scanning, accessibility, responsive checks, performance smoke tests, and retained test artifacts.
- Fixtures and clock are injectable. Every test owns an isolated cart/order state.

### Hosting design

- Package as a reproducible multi-stage Docker image and deploy to Google Cloud Run using request-based billing and scale-to-zero.
- Start with zero minimum instances and a low maximum-instance cap to reduce accidental cost. Configure billing alerts before public deployment.
- Store durable data in Neon; never depend on the Cloud Run container filesystem.
- Keep application secrets in deployment/GitHub environment secrets, never in repository files.
- Run the nightly fixture reset from a narrowly authorized scheduled workflow. The manual reset endpoint remains available only in non-production demonstration environments.

## Original design direction

The working design identity is **MG Parts Lab**, an unofficial educational catalog rather than an official MG storefront.

- Visual character: precise, modern automotive workshop; restrained graphite, warm white, and a distinct non-trademark accent color.
- Layout: mobile-first responsive shell, prominent vehicle context, fast part-reference search, readable comparison cards, and calm checkout review.
- Arabic: first-class RTL composition using logical CSS properties, not a transformed LTR afterthought. Part numbers, money, and mixed-direction technical text receive explicit bidi handling.
- Imagery: original illustrations or generic licensed automotive-part images. Record source, license, creator, and modification details in an attribution file when external assets are introduced.
- Required notices: “Unofficial educational demonstration,” “No real orders or payments,” and contextual labels for fictional fitment/pricing/shipping/tax data.

## Initial synthetic vehicle fixture

The fixture represents test cases, not authoritative vehicle or parts guidance.

| Family | Representative demo variants | Primary market coverage |
|---|---|---|
| MG3 | 2018–2024, 1.5L petrol | EG and GB |
| MG4 EV | 2022–2026, standard-range and long-range variants | EG and GB |
| MG5 | 2020–2025, 1.5L petrol sedan and EV estate test variants | Market-scoped EG/GB variants |
| ZS | 2018–2025, 1.5L petrol and 1.0L turbo test variants | EG and GB |
| ZS EV | 2019–2026, standard-range and long-range variants | Primarily GB, with controlled cross-market cases |
| HS | 2019–2026, 1.5L turbo and plug-in-hybrid test variants | EG and GB |

Exact fixture identifiers and compatibility records will be reviewed as part of F03/F05. No compatibility is inferred from these display labels.

## Delivery sequence

Each feature ends with local verification, a pushed feature branch, a pull request, green GitHub checks, review-comment resolution, product-owner merge, and explicit confirmation before the next row starts.

| Order | Feature / stories | Branch | Complete working increment | Dependencies | Status |
|---:|---|---|---|---|---|
| 0 | Project foundation | `codex/project-foundation` | Approved architecture, plan/handoff, repo guidance, synchronized backlog | None | In progress |
| 1 | F01 / US001–US003 | `codex/f01-market-context` | Runnable store shell with persistent market, language, and display-currency context | Foundation | Waiting |
| 2 | F02 / US004–US005 | `codex/f02-rtl-accessible-storefront` | Responsive, keyboard-usable English/Arabic shell with correct RTL behavior | F01 | Waiting |
| 3 | F15 / US036–US037 | `codex/f15-api-security-contracts` | OpenAPI/error/security/privacy baseline protecting later endpoints | F01 | Waiting |
| 4 | F03 / US006–US007 | `codex/f03-vehicle-context` | Persistent synthetic model/year/engine selector and safe context changes | F01, F15 | Waiting |
| 5 | F13 / US032–US033 | `codex/f13-fixtures-operations` | Validated fixture import plus authorized deterministic reset | F03, F15 | Waiting |
| 6 | F04 / US008–US009 | `codex/f04-catalog-browsing` | Localized category/subcategory browse with stable pagination and empty states | F03, F13 | Waiting |
| 7 | F05 / US010–US011 | `codex/f05-fitment-relationships` | Explainable four-state fitment plus typed part relationships | F03, F04 | Waiting |
| 8 | F08 / US018–US019 | `codex/f08-product-variants` | Localized product detail and genuine/OE-equivalent/aftermarket comparison | F04, F05 | Waiting |
| 9 | F09 / US020–US022 | `codex/f09-price-stock-quantity` | Reproducible quotes, inventory states, quantity rules, and reconciliation | F08, F13 | Waiting |
| 10 | F06 / US012–US014 | `codex/f06-search-discovery` | Multilingual/reference search with filters, sorting, pagination, and safe diagnostics | F04, F05, F09 | Waiting |
| 11 | F10 / US023–US025 | `codex/f10-cart-reconciliation` | Persistent server-priced anonymous cart with explicit conflict recovery | F05, F09 | Waiting |
| 12 | F11 / US026–US029 | `codex/f11-cod-checkout` | Egypt/UK guest address, demo shipping/tax quote, policy review, idempotent COD order | F10, F15 | Waiting |
| 13 | F12 / US030–US031 | `codex/f12-confirmation-order-lookup` | Localized confirmation plus throttled, non-enumerable demonstration order lookup | F11 | Waiting |
| 14 | F14 / US034–US035 | `codex/f14-observability-faults` | Correlated events and authorized bounded failure scenarios across the commerce path | F09–F12, F13 | Waiting |
| 15 | F16 / US038–US039 | `codex/f16-functional-quality-gates` | CI unit/integration/contract suites and four-context end-to-end journeys | Functional features | Waiting |
| 16 | F07 / US015–US017 | `codex/f07-knowledge-retrieval` | Versioned bilingual corpus, filtered retrieval, citations, and golden dataset | F13, F15 | Waiting |
| 17 | F17 / US040–US041 | `codex/f17-nfr-ai-evaluation` | Accessibility/performance/security gates and reproducible RAG/agent evaluation | F07, F14, F16 | Waiting |

## Feature execution template

For every feature:

1. Refresh `main`, create the named feature branch, and update this document to mark the feature active.
2. Confirm story acceptance criteria and dependencies against the repository and Azure Boards.
3. Implement story-level phases that leave the branch runnable and tested after each phase.
4. Add or update unit, integration, contract, accessibility, and E2E coverage proportionate to the change.
5. Run the relevant focused tests during development and the full `mvnw verify` release check before push.
6. Update OpenAPI, fixtures, events, UI test IDs, translations, deployment configuration, and this handoff document when affected.
7. Push and open the feature PR. Record the PR, commit, tests, CI result, deployment preview, review comments, and remaining risks here.
8. Resolve or justify every actionable review comment. Wait for green checks and product-owner merge.
9. Do not create the next feature branch until the product owner confirms continuation from the newly updated `main`.

## New-task continuation checklist

A future task should:

1. Read `AGENTS.md` and this document.
2. Inspect the current branch, worktree, last commits, active PR, and Azure story states.
3. Resume the exact `Next action` recorded in **Current position**; do not restart completed work.
4. Preserve the branch/PR/confirmation gate.
5. Update this document before ending a phase or handing off.

## Decision log

| Date | Decision | Reason |
|---|---|---|
| 2026-08-03 | Repository documentation is authoritative; synchronize Azure Boards from it | The repository contains fuller acceptance and traceability data than the initial Azure import |
| 2026-08-03 | Java 26 with Spring Boot 4.1.x | Explicit product-owner choice; current stable Boot supports Java 26 |
| 2026-08-03 | Modular monolith with Thymeleaf/HTMX | Lower operational complexity and strong server/UI testability for a Java-first project |
| 2026-08-03 | Google Cloud Run plus Neon | Managed scale-to-zero hosting and a durable external relational database |
| 2026-08-03 | Unofficial “MG Parts Lab” design with original/generic assets | Avoid unsupported trademark, catalog, and official-store claims |
| 2026-08-03 | One branch/PR per Azure Feature plus one foundation PR | Clear review/merge boundaries and resumable story-level phases |
| 2026-08-03 | Protected APIs before an operator UI; defer the generative assistant | Keep the first release focused on deterministic commerce and evaluation foundations |
