# MG Parts Learning Store

This repository contains the requirements, delivery plan, and implementation of an unofficial educational MG spare-parts demonstration serving synthetic Egypt and United Kingdom scenarios.

## Recommendation

Use Skoda-Parts.com as a **behavioral benchmark**, not as a close replica. A replica would reproduce legacy design choices and narrow the learning value. The proposed MG Parts MVP preserves the useful commerce patterns—vehicle-led browsing, part-number search, compatibility data, product alternatives, cart, and checkout—while adding multilingual, multi-market, deterministic-data, API, observability, and RAG-ready capabilities that are much more valuable for Agentic AI testing.

## MVP boundaries

- Markets: Egypt and United Kingdom.
- Languages: Arabic and English, including right-to-left Arabic layouts.
- Display currencies: EGP, GBP, USD, and EUR.
- Payment: cash on delivery only.
- Customer journey: anonymous browse, search, product review, cart, checkout, order confirmation, and order lookup.
- Catalog: a deliberately small, representative MG fixture catalog rather than a production-scale catalog.
- Customer accounts, VIN decoding, online payment, returns processing, reviews, promotions, and live carrier/tax/FX integrations are deferred.
- The project uses original demonstration content, no official MG logo, and no real customer/order data.
- The planned runtime is Java 26 with Spring Boot 4.1.x, deployed to Google Cloud Run with Neon PostgreSQL.

## Documents

1. [Benchmark analysis](docs/01-benchmark-analysis.md)
2. [Product vision and scope](docs/02-product-vision-and-scope.md)
3. [Functional requirements](docs/03-functional-requirements.md)
4. [Journeys and business rules](docs/04-journeys-and-business-rules.md)
5. [Data model and API requirements](docs/05-data-model-and-apis.md)
6. [Non-functional and testability requirements](docs/06-non-functional-and-testability.md)
7. [Agentic AI and RAG test strategy](docs/07-agentic-ai-and-rag.md)
8. [Azure Boards backlog](docs/08-azure-boards-backlog.md)
9. [Requirements traceability matrix](docs/09-traceability-matrix.md)
10. [Azure DevOps implementation status](docs/10-azure-devops-status.md)
11. [Implementation plan and progress](docs/11-implementation-plan.md)

## Azure DevOps

- Organization: `https://dev.azure.com/Islamabdalazez/`
- Project: `MGParts`
- Area path: `MGParts`
- Process: Agile (converted from Basic on 2026-08-03)
- Local import backup: [azure-devops/backlog-import.csv](azure-devops/backlog-import.csv)

Azure work items `2` through `66` contain the complete MVP set: 7 Epics, 17 Features and 41 User Stories. All Feature-to-Epic and Story-to-Feature parent relations are materialized in Azure. Acceptance Criteria, Story Points, priorities, requirements, area/iteration paths and tags are synchronized from the repository; see the implementation-status document for the live verification record and ID map.

The work-item titles carry stable BA references (`E01`, `F01`, `US001`, and so on). These references are intentionally separate from Azure work-item IDs and remain stable if items are moved or recreated.

## Decision log

| Decision | Outcome | Rationale |
|---|---|---|
| Benchmark domain | Use `www.skoda-parts.com` | The supplied `www.skodaparts.com` domain was unavailable/parked; the active store is the hyphenated domain. |
| Product approach | Benchmark and improve | Produces richer and safer testing surfaces than a branded clone. |
| Accounts | Deferred | Anonymous commerce provides the full MVP funnel with less scope and privacy risk. |
| FX rates | Versioned test configuration | Deterministic prices are essential for repeatable automated and agent evaluations. |
| Shipping and tax | Configurable dummy policies | Real policies are unknown; invented values must never be represented as production rules. |
| AI readiness | Built into the MVP | Stable APIs, fixtures, provenance, reset hooks, and knowledge versions enable repeatable agent and RAG tests. |
| Product identity | Unofficial `MG Parts Lab` educational demonstration | Avoids any claim of affiliation and uses original or properly licensed content without an official MG logo. |
| Runtime | Java 26 with Spring Boot 4.1.x | Matches the approved Java-first direction and a stable framework version that supports Java 26. |
| Hosting | Google Cloud Run with Neon PostgreSQL | Provides managed scale-to-zero application hosting and durable external relational storage. |
| Delivery | One branch and PR per Azure Feature, plus one foundation PR | Keeps review boundaries clear and enables story-level phases within larger features. |
