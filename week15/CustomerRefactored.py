from dataclasses import dataclass

@dataclass
class Customer:
    customer_id: str
    name: str
    orders: int
    customer_type: str
    discount: float = 0.0

def validateCustomerInput(customer: Customer) -> None:
    """
    Validate customer before processing.
    Raises ValueError on invalid data.
    FUnctional cohesion - single clear purpose.
    """
    if customer.orders < 0:
        raise ValueError(f"Order count cannot be negative: {customer.orders}")
    if customer.customer_type not in ("regular", "premium", "vip"):
        raise ValueError(f"Unknown customer type: {customer.customer_type}")
    if not customer.name or not customer.name.strip():
        raise ValueError("Customer name must not be empty.")

def calculateDiscount(customer_type: str, orders: int) -> float:
    """
    Return the applicable discount rate for this customer.
    Functional cohesion - computes and returns one value.
    """
    if customer_type == "vip":
        return 0.20 if orders >= 10 else 0.15
    elif customer_type == "premium":
        return 0.10 if orders >= 5 else 0.05
    else:
        return 0.0

def applyDiscountToCustomer(customer: Customer) -> Customer:
    """
    Compute and store the discount on the customer record.
    Returns the updated Customer.
    Functional cohesion - one purpose.
    """
    customer.discount = calculateDiscount(customer.customer_type, customer.orders)
    return customer

def generateCustomerSummary(customer: Customer) -> str:
    """
    Build and return a formattedsummary string.
    Functional cohesion - single output, no side effects.
    """
    return (
        f"Customer: {customer.name} (ID: {customer.customer_id})\n"
        f"  Type    : {customer.customer_type}\n"
        f"  Orders  : {customer.orders}\n"
        f"  Discount: {customer.discount * 100:.1f}%"
    )

def saveCustomerRecord(customer: Customer) -> None:
    """
    Persist the customer record.
    Functional cohesion - single responsibility.
    """
    print(f"[DB] Saving customer {customer.customer_id}: {customer.name}")

def processCustomer(customer: Customer) -> str:
    """
    Orchestrate the full customer-processing pipeline.
    Each step is delegated to a cohesive sub-routine.
    """
    validateCustomerInput(customer)
    applyDiscountToCustomer(customer)
    saveCustomerRecord(customer)
    return generateCustomerSummary(customer)

if __name__ == "__main__":
    c = Customer(customer_id="C001", name="Ada Obi", orders=12, customer_type="vip")
    print(processCustomer(c))
