# Data Model and API Requirements

This document specifies a technology-neutral logical model. Storage and implementation technology are intentionally not prescribed.

## Core domain model

```mermaid
erDiagram
    MARKET ||--o{ MARKET_RULE : configures
    VEHICLE_MODEL ||--o{ VEHICLE_VARIANT : contains
    CATEGORY ||--o{ CATEGORY : has_child
    PRODUCT ||--o{ PRODUCT_VARIANT : offers
    PRODUCT }o--o{ CATEGORY : classified_as
    PRODUCT }o--o{ VEHICLE_VARIANT : fits
    PRODUCT }o--o{ PRODUCT : relates_to
    PRODUCT_VARIANT ||--|| STOCK_ITEM : stocked_as
    PRODUCT_VARIANT ||--o{ PRICE : priced_by_market
    CART ||--o{ CART_LINE : contains
    PRODUCT_VARIANT ||--o{ CART_LINE : selected_as
    ORDER ||--o{ ORDER_LINE : snapshots
    MARKET_RULE ||--o{ ORDER : quoted_with
    KNOWLEDGE_SOURCE ||--o{ KNOWLEDGE_VERSION : versions
    KNOWLEDGE_VERSION ||--o{ KNOWLEDGE_CHUNK : chunks
    EVAL_CASE }o--o{ KNOWLEDGE_CHUNK : expects
```

## Entity definitions

| Entity | Required fields/notes |
|---|---|
| Market | `code` (`EG`, `GB`), enabled locales/currencies, base currency, status. |
| Market rule | Type (`FX`, `TAX`, `SHIPPING`, `COD`), version, effective dates, structured payload, checksum. |
| Vehicle model | Stable ID, MG model name, market availability. |
| Vehicle variant | Model ID, year range, engine/trim codes and normalized attributes used for fitment. |
| Category | Stable ID, parent ID, sort order, localized name/description, status. |
| Product | Stable ID, localized content, reference numbers, specifications, category links, quantity rules, shipping attributes. |
| Product variant | SKU, product ID, manufacturer, part type, origin, status, images. |
| Fitment | Product ID, vehicle variant ID, constraints and explanation code. |
| Product relationship | Source/target product IDs and type: `ALTERNATIVE`, `SUPERSEDES`, `RELATED`. |
| Stock item | SKU, on-hand, available, status, version/concurrency token. |
| Price | SKU, market, base amount in minor units, currency, effective dates and version. |
| Cart | Opaque ID, market, locale, display currency, version, expiry. |
| Cart line | SKU, quantity, current quote snapshot, validation flags. |
| Order | Reference, status, contact/address snapshot, market/locale/currency, totals, rule versions, policy versions, idempotency key hash and timestamps. |
| Order line | Immutable product/SKU/name/reference/quantity/price/tax snapshot. |
| Knowledge source/version/chunk | Stable provenance and versioning fields defined by FR-080–FR-083. |
| Evaluation case | Question, locale, market, expected chunk IDs, answerability, prohibited claims and rubric. |

## Money and quote shape

Example fields, not a prescribed JSON naming convention:

```json
{
  "quoteVersion": "q-00042",
  "baseSubtotal": { "minor": 125000, "currency": "EGP" },
  "fxSnapshot": { "id": "fx-demo-2026-08-03", "rate": "1.000000" },
  "displaySubtotal": { "minor": 125000, "currency": "EGP" },
  "tax": { "minor": 0, "currency": "EGP", "ruleVersion": "tax-eg-demo-v1" },
  "shipping": { "minor": 0, "currency": "EGP", "ruleVersion": "ship-eg-demo-v1" },
  "grandTotal": { "minor": 125000, "currency": "EGP" },
  "isDemonstrationPolicy": true
}
```

Rates should use decimal strings or fixed-precision decimals. Monetary values use integer minor units.

## API capability map

| Method/path pattern | Purpose | Important behavior |
|---|---|---|
| `GET /api/v1/context` | Markets, locales and currencies | Returns defaults and active configuration versions. |
| `GET /api/v1/vehicles` | Vehicle selector data | Filterable by market, model and year. |
| `GET /api/v1/categories` | Category tree | Supports vehicle and locale context. |
| `GET /api/v1/products` | Browse/search | Query, vehicle, category, filters, sort, pagination and diagnostics flag. |
| `GET /api/v1/products/{id}` | Product details | Includes variants, fitment, relations, price and stock. |
| `POST /api/v1/fitment/evaluate` | Explicit fitment check | Returns status and explanation code. |
| `POST /api/v1/carts` | Start anonymous cart | Returns opaque cart token and version. |
| `GET /api/v1/carts/{id}` | Get/requote cart | Never trusts client totals. |
| `POST /api/v1/carts/{id}/lines` | Add line | Validates SKU, quantity, stock and market. |
| `PATCH /api/v1/carts/{id}/lines/{lineId}` | Change quantity | Requires expected cart version. |
| `DELETE /api/v1/carts/{id}/lines/{lineId}` | Remove line | Idempotent delete. |
| `POST /api/v1/quotes/shipping` | Shipping/tax quote | Destination and cart attributes produce rule-versioned quote. |
| `POST /api/v1/orders` | Place COD order | Requires cart version, policy versions and idempotency key. |
| `POST /api/v1/orders/lookup` | Customer lookup | Generic failure response and rate limit. |
| `GET /api/v1/knowledge/search` | Testable retrieval | Returns chunks, ranks, filters and provenance. |
| `POST /api/v1/assistant/query` | Optional demo assistant | Returns answer, citations, tool facts and abstention status. |
| `POST /api/test-support/reset` | Non-production reset | Authorized, named fixture, checksum response. |
| `POST /api/test-support/scenarios` | Activate test fault/scenario | Time-bound and auditable. |

## Contract conventions

- Public identifiers are opaque and stable; database keys are not exposed accidentally.
- State-changing requests support a correlation ID and appropriate idempotency/concurrency token.
- Error bodies contain `code`, `message`, `correlationId`, optional field errors and safe diagnostic metadata.
- Localized messages are presentation aids; automation asserts machine-readable codes.
- Pagination returns stable ordering, page/cursor metadata and total count when economically feasible.
- Date/time values use ISO 8601 UTC, while the UI renders market-appropriate formats.
- APIs do not accept calculated price, tax, shipping, availability or fitment as authoritative client input.

## Representative error codes

`VALIDATION_FAILED`, `MARKET_UNSUPPORTED`, `LOCALE_UNSUPPORTED`, `CURRENCY_UNSUPPORTED`, `VEHICLE_INCOMPLETE`, `FITMENT_INCOMPATIBLE`, `FITMENT_AMBIGUOUS`, `SKU_NOT_SELLABLE`, `STOCK_CHANGED`, `PRICE_CHANGED`, `QUANTITY_RULE_VIOLATION`, `CART_EMPTY`, `CART_VERSION_CONFLICT`, `QUOTE_EXPIRED`, `ADDRESS_INVALID`, `SHIPPING_UNAVAILABLE`, `POLICY_VERSION_STALE`, `ORDER_LOOKUP_FAILED`, `RATE_LIMITED`, `KNOWLEDGE_EVIDENCE_INSUFFICIENT`.

## Data lifecycle and privacy

- Anonymous cart expiry: configurable; default test fixture 24 hours.
- Demonstration orders: purgeable by environment reset; never use real customer data.
- Logs mask contact/address data and idempotency secrets.
- Knowledge chunks must not contain personal data or copied proprietary catalog content without permission.
- Reset endpoints and fault controls are disabled outside non-production environments.

