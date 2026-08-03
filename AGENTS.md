# MG Parts Repository Guidance

## Sources of truth

Read these before changing the application:

1. `docs/11-implementation-plan.md` for delivery order, current progress, and handoff state.
2. `docs/03-functional-requirements.md`, `docs/04-journeys-and-business-rules.md`, and `docs/06-non-functional-and-testability.md` for behavior and quality constraints.
3. `docs/08-azure-boards-backlog.md` for feature and user-story scope.
4. `docs/09-traceability-matrix.md` for requirement coverage and risks.

The repository documentation is authoritative when it differs from Azure Boards. Synchronize Azure Boards from the repository, not the reverse, unless the product owner explicitly changes that decision.

## Product safety boundary

- This is an unofficial educational demonstration. It does not take real orders, real payments, or real customer data.
- Use synthetic fixtures only. Never commit personal data, secrets, real customer records, proprietary catalogs, or copied commerce content.
- Do not use an official MG logo. Use original interface content and original or properly licensed generic imagery, with attribution/provenance recorded in the repository.
- Display a clear unofficial-demo notice and label fictional prices, FX, tax, shipping, fitment, and inventory as demonstration data.
- The only payment experience is simulated cash on delivery. Final submission remains an explicit approval boundary for agent tests.

## Technical baseline

- Java 26
- Spring Boot 4.1.x modular monolith
- Maven Wrapper
- Thymeleaf, HTMX where it materially improves interaction, and minimal custom JavaScript
- PostgreSQL with Flyway migrations
- OpenAPI contracts and stable machine-readable error codes
- JUnit, AssertJ, Testcontainers, ArchUnit, and Playwright as appropriate
- Docker deployment to Google Cloud Run with Neon PostgreSQL

Prefer package-by-business-capability modules with explicit boundaries. Keep money in integer minor units, rates as fixed-precision decimals, identifiers stable and locale-independent, and time injectable in deterministic tests.

## Delivery workflow

- Refresh `main` from `origin/main` before starting a feature.
- Use one branch and one pull request per Azure Feature: `codex/f01-...` through `codex/f17-...`.
- `codex/project-foundation` is the one approved pre-feature planning branch.
- Break a large feature into story-level implementation phases on the same feature branch.
- Do not start the next feature until the current pull request is merged and the product owner explicitly confirms continuation.
- Preserve user changes and unrelated worktree changes.
- Every behavior change requires proportionate automated tests, including at least one negative case.
- Run the complete relevant local suite before pushing. Once the application scaffold exists, the default release check is `./mvnw verify` (or `mvnw.cmd verify` on Windows).
- Push only the intentional branch changes, open a pull request, respond to review comments, and wait for GitHub checks and product-owner approval.

## Progress and handoff

Update `docs/11-implementation-plan.md` whenever a phase starts, completes, becomes blocked, or changes scope. Record the branch, Azure references, PR, test command/result, deployment state, decisions, and exact next action so a new task can continue without reconstructing history.
