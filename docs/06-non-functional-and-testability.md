# Non-Functional and Testability Requirements

## Quality attributes

| ID | Requirement / target |
|---|---|
| NFR-001 | Critical customer pages should meet p95 server response under 1.5 seconds for reads and 2.5 seconds for cart/order writes in the reference test environment. |
| NFR-002 | Search should return the first page under 2 seconds for the fixture catalog at the agreed concurrent test load. |
| NFR-003 | Availability objective for a deployed learning environment is 99% during planned learning sessions; planned resets are excluded. |
| NFR-004 | UI supports current desktop and mobile versions of Chromium, Firefox and WebKit-based browsers used by the test strategy. |
| NFR-005 | Critical journeys meet WCAG 2.2 AA, including keyboard use, visible focus, names/roles/values, contrast, error association and 200% zoom. |
| NFR-006 | Arabic content and controls are visually correct at 320, 768, 1024 and 1440 CSS-pixel reference widths. |
| NFR-007 | API and UI must protect against OWASP Top 10 classes relevant to the design; dependency and secret scanning run in CI. |
| NFR-008 | Order submission, stock decrement and idempotency behavior remain correct under concurrent requests. |
| NFR-009 | Logs and traces are structured, time-correlated and searchable by correlation ID without containing unmasked personal data. |
| NFR-010 | Configuration, fixture and knowledge versions are exposed in a safe health/build endpoint. |
| NFR-011 | Default fixture load is deterministic and produces a published checksum. |
| NFR-012 | Backup/restore is not a production commitment, but fixture source and schema migrations are version controlled. |

## Testability contract

### Stable UI automation

- Interactive elements have semantic roles and accessible names.
- Critical controls also have documented stable `data-testid` values independent of translated text.
- Test IDs identify business intent, for example `vehicle-model`, `search-query`, `product-card-{id}`, `cart-line-{id}`, `checkout-submit`.
- IDs do not encode array position, CSS class or translated label.
- Loading, empty, error, stale-data and success states are explicit in the DOM.

### Deterministic data

- Named fixtures: `mvp-happy-v1`, `mvp-edge-v1`, `rag-golden-v1` and `empty-catalog-v1`.
- The clock can be fixed or injected in automated environments.
- FX, tax, shipping, stock and search index versions are fixture-controlled.
- Reset returns fixture name, checksum, data counts and completion timestamp.
- Tests do not depend on execution order or another test’s cart/order.

### Observability

Required events include:

`context.changed`, `vehicle.selected`, `search.executed`, `fitment.evaluated`, `product.viewed`, `cart.line_added`, `cart.requoted`, `cart.reconciliation_required`, `shipping.quoted`, `order.created`, `order.idempotent_replay`, `order.lookup_attempted`, `knowledge.retrieved`, `assistant.abstained` and `fixture.reset`.

Each event contains event ID, schema version, timestamp, correlation/causation IDs, safe entity IDs, market/locale and fixture version. Personal data is omitted or masked.

### Fault and state controls

Non-production-only controls can activate bounded scenarios such as:

- price changes after product view;
- stock changes during checkout;
- ambiguous fitment;
- shipping unavailable for an oversize product;
- API latency or one controlled 5xx response;
- stale or conflicting knowledge version;
- missing translation in the negative fixture;
- duplicate order submission.

Every control is authenticated, time-limited, auditable and cleared by reset.

## Security and privacy acceptance

- Operator and test-support endpoints require authorization and are not discoverable as unauthenticated functionality.
- Input validation and output encoding cover Arabic/English Unicode, bidirectional text, long references and injection payloads.
- Cart and order identifiers are non-sequential and cannot be used for enumeration.
- Order lookup has generic failures, throttling and security events.
- Client totals, roles, diagnostic flags and test-scenario headers are not trusted.
- Secrets never appear in repository files, UI, API payload examples, logs or traces.
- Consent/policy versions are stored with orders; the demo must not claim regulatory compliance beyond implemented evidence.

## Accessibility-focused acceptance examples

- Language change updates document language and direction without unexpected focus loss.
- Visual order and keyboard focus order remain logical in RTL and LTR layouts.
- Currency symbols and numbers have unambiguous accessible text.
- Validation errors are summarized and linked to fields; color is not the sole cue.
- Product cards, filters, quantity controls, dialogs and toast messages expose status correctly to assistive technologies.

## Performance and resilience test notes

- Load profiles should separate browse/search traffic from order writes.
- Search index unavailability should produce a controlled degraded state; direct catalog browsing remains available if its dependency is healthy.
- Retrying safe reads is allowed; order writes rely on idempotency rather than blind retries.
- A stock conflict returns a recoverable business error rather than a generic server error.
- Cache keys include market, locale, currency and vehicle/filter context where relevant.

## Definition of Done for an MVP user story

1. Acceptance criteria pass at API and/or UI level as appropriate.
2. English and Arabic behavior is implemented or explicitly not user-facing.
3. Egypt and UK differences are covered where the story is market-sensitive.
4. Accessibility semantics and stable test IDs are present.
5. Observability and machine-readable errors are implemented.
6. Unit/contract/integration tests pass, with at least one negative case.
7. OpenAPI, fixtures and knowledge documentation are updated when affected.
8. No high/critical security finding or critical accessibility defect remains.

