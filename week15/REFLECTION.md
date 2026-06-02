# REFLECTION.md
## SEN306 – Project: Refactor processCustomer

---

### 1. How did you achieve functional cohesion? Which routines did you extract?

The original `processCustomer` routine suffered from **coincidental/logical cohesion**:
it validated input, computed discounts, updated a value, and saved to the database —
all in one block with no clear structure.

I split it into four focused routines:

| Routine | Cohesion | Reason |
|---|---|---|
| `validateCustomerInput` | Functional | Does exactly one thing: check correctness of input |
| `calculateDiscount` | Functional* | Returns one computed value based on type and orders |
| `applyDiscountToCustomer` | Functional | Mutates the single field `discount` on the record |
| `generateCustomerSummary` | Functional | Builds and returns a formatted string — no side effects |
| `saveCustomerRecord` | Functional | Single persistence action, no other responsibility |

\* `calculateDiscount` technically has **logical cohesion** internally (a large if/elif
 selecting by `customer_type`). This is a design smell. The correct fix is a
 **Strategy pattern**: an interface `DiscountStrategy` with one implementation per
 customer type, each with a `compute(orders)` method. That raises it to fully
 functional cohesion.

---

### 2. What parameter passing issues did you encounter?

The original code modified the parameter `d` (representing the customer's discount)
directly but never returned it, relying on **side-effect visibility** through object
mutation. This is the Python/Java behaviour:

- Python passes object references **by value** — you can *mutate* the object
  (e.g. `customer.discount = 0.15`) and the caller sees the change,  
  but you cannot *rebind* the caller's variable.
- This created a subtle bug: if a primitive (e.g. `d: float`) had been used instead
  of an object field, the caller's `d` would **not** have been updated at all.

The fix was to wrap all loose parameters into a `Customer` dataclass, so mutations
to its fields are safely visible, and the intent (mutating the record) is explicit.

---

### 3. How would `d` update behave differently under pass-by-value-result?

Under **pass-by-value-result** (Ada-style copy-in / copy-out):

1. At the call site, the *current value* of `d` is **copied into** the formal parameter.
2. The routine works on its **local copy**.
3. At routine exit, the local copy is **written back** to `d`.

This differs from Python/Java object mutation in a critical way:

- In Python, mutations to `customer.discount` are **immediately visible** to any
  other code that holds a reference to the same `Customer` object (live aliasing).
- Under value-result, no write-back occurs until the **routine exits**. If another
  part of the program reads `d` *during* the call, it still sees the old value.
- If the same variable were passed **twice** (e.g. `update(d, d)`), value-result
  would create **two independent copies**, whereas reference semantics would make
  both parameters alias the same storage — producing different final values
  depending on order of copy-out.

**Practical implication:** value-result avoids some aliasing bugs but can still
produce surprising results when the same variable appears in multiple positions of
the parameter list. Modern languages (Python, Java, C#) avoid it entirely.

---
