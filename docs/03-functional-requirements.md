# Functional Requirements

Requirement IDs are stable and are referenced by user stories and the traceability matrix.

## Market and localization

| ID | Requirement |
|---|---|
| FR-001 | The user can select Egypt or United Kingdom as the active market. |
| FR-002 | The user can select Arabic or English without losing the current page, vehicle, search, cart or checkout-safe data. |
| FR-003 | Arabic renders with `lang="ar"`, right-to-left direction and mirrored directional controls; English renders left-to-right. |
| FR-004 | The user can display prices in EGP, GBP, USD or EUR. Egypt defaults to EGP; the UK defaults to GBP. |
| FR-005 | Changing display currency does not change the market, shipping destination, base price, stock or cart quantities. |
| FR-006 | The API returns ISO market, locale and currency codes and never infers them only from free text. |
| FR-007 | A shared translation key cannot silently fall back in production-like environments; missing translations emit a diagnostic event. |

## Vehicle and catalog

| ID | Requirement |
|---|---|
| FR-010 | A user can select MG model, model year and optional engine/variant from seeded values. |
| FR-011 | The selected vehicle persists in the anonymous session and is displayed on results and product pages. |
| FR-012 | A user can clear or change the vehicle at any time and is warned when this makes a cart item incompatible. |
| FR-013 | Catalog navigation supports category and subcategory levels with stable, locale-independent identifiers. |
| FR-014 | Category results show product name, primary part reference, price/range, availability and fitment indicator. |
| FR-015 | Catalog pages support pagination with a defined default page size and stable ordering tie-breaker. |
| FR-016 | An unavailable or retired category returns a usable empty/not-found state rather than an unhandled error. |

## Search and discovery

| ID | Requirement |
|---|---|
| FR-020 | Search accepts product name, OEM/reference number, manufacturer, category term and supported Arabic synonym. |
| FR-021 | Reference-number search ignores case and normalizes spaces and hyphens while retaining the original query for display. |
| FR-022 | Search can be scoped to the selected vehicle or run across all vehicles. |
| FR-023 | Results can be filtered by category, availability, fitment, price range and part type (genuine/OE-equivalent/aftermarket). |
| FR-024 | Results can be sorted by relevance, price ascending, price descending and name. |
| FR-025 | Empty queries, no-result queries and invalid filter combinations return intentional states with recovery actions. |
| FR-026 | In non-production diagnostics, a result exposes a machine-readable match reason without leaking internal secrets. |

## Product, fitment, stock and price

| ID | Requirement |
|---|---|
| FR-030 | A product page shows localized name/description, references, category, images, specifications and applicable vehicles. |
| FR-031 | A logical product can have one or more sellable variants with manufacturer, origin/type, SKU, price and stock. |
| FR-032 | Fitment status is one of `COMPATIBLE`, `INCOMPATIBLE`, `AMBIGUOUS` or `VEHICLE_NOT_SELECTED`, with an explanation code. |
| FR-033 | Alternative, superseded and related products are represented as typed relationships. |
| FR-034 | Availability is one of `IN_STOCK`, `LOW_STOCK`, `OUT_OF_STOCK`, `BACKORDER` or `DISCONTINUED`. |
| FR-035 | An unavailable variant cannot be newly added to the cart. |
| FR-036 | Product quantity rules support minimum quantity, maximum quantity and increment; the UI explains non-default rules. |
| FR-037 | The price quote identifies base amount/currency, conversion-rate snapshot, display amount/currency, tax, shipping if known and rounding method. |
| FR-038 | Rounding uses currency minor units and is performed once on the documented calculation boundary. |
| FR-039 | Price or stock changes between view and checkout trigger an explicit reconciliation response. |

## Cart

| ID | Requirement |
|---|---|
| FR-040 | An anonymous user can add a valid variant and quantity to the cart. |
| FR-041 | Re-adding the same variant consolidates quantity unless doing so violates stock or quantity rules. |
| FR-042 | The user can change quantity, remove an item and clear the cart. |
| FR-043 | The cart persists across pages, language changes and display-currency changes within its configured lifetime. |
| FR-044 | Cart totals are recalculated server-side and returned with a quote version; client-provided totals are never trusted. |
| FR-045 | The cart identifies incompatible, unavailable, repriced and quantity-adjusted lines before checkout. |
| FR-046 | An empty cart cannot proceed to checkout. |

## Checkout and order

| ID | Requirement |
|---|---|
| FR-050 | Checkout is available without an account and supports cash on delivery only. |
| FR-051 | Checkout collects name, email, phone, shipping address, market-specific postcode/governorate fields and optional note. |
| FR-052 | Egypt and UK address schemas and validation messages are locale-aware and accept representative real formats without calling a live address service. |
| FR-053 | Shipping is quoted from configured market, destination zone, weight and oversize rules. |
| FR-054 | Tax is quoted from a versioned market policy. The seed policy may be zero but must be labeled as demonstration configuration. |
| FR-055 | Checkout shows item subtotal, tax, shipping and grand total in the selected display currency before submission. |
| FR-056 | Order submission requires explicit acceptance of terms and privacy notice versions. |
| FR-057 | Order creation is idempotent for a supplied idempotency key and cart version. |
| FR-058 | Successful submission creates an immutable order reference and confirmation page; no real payment is captured. |
| FR-059 | The order stores the pricing, FX, tax, shipping, locale, market and legal-policy versions used at submission. |
| FR-060 | An order can be looked up with order reference plus a non-public verification value; responses must not disclose whether another customer’s order exists. |

## Operator and test support

| ID | Requirement |
|---|---|
| FR-070 | An authorized operator can manage or import fixture products, variants, fitment, stock, prices, market rules and knowledge documents. |
| FR-071 | Non-production environments expose an authorized reset operation that loads a named fixture version and returns its checksum. |
| FR-072 | Non-production environments expose controllable scenarios for stock change, stale price quote, delayed response and selected fault codes. |
| FR-073 | Every API response contains a correlation ID; state-changing operations create structured audit/domain events. |
| FR-074 | Public APIs are documented by OpenAPI and use stable machine-readable error codes with localized display messages. |
| FR-075 | Seed generation never uses real customer personal data. |

## Knowledge and RAG readiness

| ID | Requirement |
|---|---|
| FR-080 | Knowledge sources support English and Arabic documents for product guidance, fitment, shipping, tax, COD, returns and order help. |
| FR-081 | Each document records source ID, version, locale, market scope, effective dates, status and checksum. |
| FR-082 | Each retrievable chunk has a stable chunk ID, heading path, source link and provenance metadata. |
| FR-083 | Published documents are immutable; corrections create a new version and retire the previous version. |
| FR-084 | The repository includes a golden evaluation set containing answerable, unanswerable, conflicting, multilingual and stale-version questions. |
| FR-085 | Any demonstration assistant returns cited chunk IDs and abstains when evidence is insufficient or outside the active market/version. |

## Out-of-scope requirements

The following are explicitly not MVP commitments: account management, real VIN decoding, live third-party integrations, payment authorization, refund, return workflow, promotions, reviews, email marketing and production customer support.

