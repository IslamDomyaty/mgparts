# Skoda Parts Benchmark Analysis

## Purpose and ethical boundary

This is a business and functional analysis of publicly accessible pages only. No login was used, no order was submitted, no access control was bypassed, and no personal data was collected. One product was added to an anonymous cart to inspect the public cart and checkout forms; the order was not placed.

Analysis date: 2026-08-03.

## Domain finding

The requested `https://www.skodaparts.com/` endpoint did not serve the referenced shop. The active and indexed shop is [Skoda-Parts.com](https://www.skoda-parts.com/online-store.html). All functional findings below refer to that active hyphenated domain.

## Pages reviewed

| Surface | Evidence URL | What was observed |
|---|---|---|
| Storefront | [Online store](https://www.skoda-parts.com/online-store.html) | Model list, service categories, spare-parts categories, global search, cart summary, trust messages, login/sign-up, contact and legal links. |
| Model catalog | [Octavia 4](https://www.skoda-parts.com/catalog/octavia-4.html) | A selected-vehicle context and category taxonomy divided into service-interval and spare-parts groups. |
| Category/results | [Octavia 4 brake system](https://www.skoda-parts.com/catalog/octavia-4/spare-parts/brake-system-11.html) | Subcategories, result counts, pagination, inventory state, part number, compatible models, price ranges, VAT labels and product grouping. |
| Product | [Front disc brake](https://www.skoda-parts.com/spare-part/5q0615301f-front-disc-brake-312x25mm-zimmermann-6354.html) | OEM references, description, origin, fitment guidance, stock, inclusive/exclusive tax prices, quantity, alternatives, related items and product-question form. |
| Search | [Site search](https://www.skoda-parts.com/online-store.html) | Keyword search can be scoped by model; results include price range, pagination, price filter, sorting and recently viewed products. |
| Cart | [Shopping cart](https://www.skoda-parts.com/shopping-cart.html) | Quantity editing, item removal, inclusive/exclusive tax totals, availability and checkout handoff. |
| Checkout | [Order form](https://www.skoda-parts.com/order.html) | Guest contact and billing fields, private/company choice, optional shipping address, note, cart summary, delivery-country dependency, discount code and consent links. |
| Delivery | [Shipping and delivery](https://www.skoda-parts.com/delivery.html) | Country-specific carrier, transit time, weight bands, volumetric weight and oversize rules. |
| Legal | [Terms and Conditions](https://www.skoda-parts.com/terms-conditions.html) and [Privacy Policy](https://www.skoda-parts.com/privacy-policy.html) | Legal and privacy content linked from checkout and the footer. |

## Current-state capability map

| Capability | Benchmark behavior | MVP disposition |
|---|---|---|
| Vehicle-led browsing | Customer chooses a model, and the selected model persists across catalog pages. | Keep and simplify to MG model, year and optional engine. |
| Category hierarchy | Model → service/spare-parts group → category → subcategory → products. | Keep a three-level taxonomy with stable IDs. |
| Part-number search | Global search supports text and part identifiers, optionally scoped by model. | Keep; normalize spaces, case and hyphens. |
| Result refinement | Pagination, price band and default sorting are visible. | Keep filters and defined sort options; use deterministic cursor/page semantics. |
| Fitment | Product references compatible models; enhanced chassis compatibility requires an account/garage. | Keep explicit fixture-based fitment. Defer VIN decoding and accounts. |
| Product families | A logical part can contain multiple brand/origin/price variants. | Keep as product plus sellable variants. |
| Pricing | Prices are shown including and excluding VAT in euros. | Replace with market base price and four display currencies; show configured tax separately. |
| Stock | Exact/approximate stock and dispatch messaging are shown. | Keep finite stock states and deterministic seeded quantities. |
| Quantity rules | Brake discs defaulted to two because they should be replaced in pairs. | Keep product-level minimum and increment rules. |
| Alternatives | OE, genuine and aftermarket alternatives appear together. | Keep part alternatives and supersession relationships. |
| Cross-sell | “You will also need” links related items. | Keep a small related-products rule set. |
| Anonymous cart | Session cart supports add, edit, remove and totals. | Keep. |
| Guest checkout | Contact, billing, country, note, delivery and consent fields are shown. | Keep, reduced to data needed for cash delivery. |
| Shipping | Country and weight-band rules drive delivery options and price. | Keep configurable dummy shipping zones; no live carrier integration. |
| Customer account/garage | Registration, login and virtual garage support compatibility. | Defer from MVP. |
| Newsletter/questions | Newsletter and product-question forms collect personal data. | Defer to minimize privacy scope. |

## Strengths worth retaining

- The catalog is organized around the customer’s vehicle, reducing incompatible product discovery.
- Part numbers, compatible models, origin, stock and alternatives provide unusually rich search and knowledge data.
- Product families make comparison between genuine, OE and aftermarket variants explicit.
- Cart and checkout expose clear quantities and tax-inclusive/exclusive totals.
- Shipping rules model realistic weight, dimension, destination and transit-time constraints.

## Gaps and improvement opportunities

| Gap | Business/testing impact | MG Parts response |
|---|---|---|
| Legacy visual and interaction patterns | Accessibility, mobile and locator stability may be weak. | Semantic HTML, stable test IDs and WCAG 2.2 AA targets. |
| Mixed-language remnants and malformed external link | Creates inconsistent experience and unreliable automation. | Translation completeness checks and URL validation in CI. |
| Egypt is absent from checkout and delivery countries | Benchmark cannot serve the required market. | First-class Egypt and UK market configuration. |
| Account-gated chassis compatibility | Adds identity scope before core fitment value. | Fixture-based model/year/engine fitment without an account. |
| Tax/shipping logic is tightly coupled to current geography | Hard to adapt and test. | Versioned market rules with an auditable quote response. |
| Currency choice is narrow | Does not meet requested four-currency display. | Base-currency prices plus versioned conversion-rate snapshots. |
| Search returns broad matches with limited explainability | Hard for agents to judge why a result matched. | Return match reasons and normalized tokens in non-production diagnostics. |
| No explicit AI knowledge provenance | RAG answers cannot be evaluated for grounding. | Versioned documents, chunk IDs, locale/market metadata and citations. |

## Recommendation

A close clone is not recommended. The stronger learning platform is a behaviorally comparable application with a cleaner domain model and intentional test affordances. This creates test problems across RTL layout, market/currency logic, fitment, inventory races, search relevance, multi-step ordering, API/UI consistency, RAG retrieval, citation faithfulness and agent recovery—without copying the benchmark’s branding, content or legacy limitations.

