QUARTERS_PER_YEAR = 4.0
EXPENSE_TYPE_MARKETING = 1
EXPENSE_TYPE_OVERHEAD = 2
EXPENSE_TYPE_OPERATIONS = 3
PROFIT_TAX_RATE = 100
MONTHS_PER_YEAR = 12
BASE_EXPENSE_MULTIPLIER = 2
ALT_EXPENSE_MULTIPLIER = 3

def initRevenueExpense(inputRec: dict, quarter: int) -> dict:
    """Load and zero out revenue/expense data for the given quarter."""
    if quarter == 0:
        raise ValueError("Quarter cannot be zero - division by zero in revenue calculation.")

    return {
        "empRec": inputRec.get("empRec"),
        "corpExpense": 0.0,
        "ytdRevenue": inputRec.get("ytdRevenue", 0.0),
        "quarter": quarter,
    }

def calculateEstimatedRevenue(ytdRevenue: float, quarter: int) -> float:
    """Annualise YTD revenue based on the current quarter."""
    if quarter == 0:
        raise ValueError("Quarter cannot be zero.")
    return ytdRevenue * QUARTERS_PER_YEAR / quarter

def updateCorpDatabase(empRec: dict) -> None:
    """Write empRec to the corporate database."""
    print(f"DB updated for employee: {empRec}")

def updateColorAndStatus(newColor: str, status: list) -> tuple:
    """Return (prevColor, updated_status)after applying newColor."""
    prevColor = newColor
    status[0] = True
    return prevColor, status

def calculateProfit(expenseType: int, revenue: float, expense: float) -> float:
    """Calculate profit based on expense type."""
    profit = 0.0

    if expenseType == EXPENSE_TYPE_MARKETING:
        for month in range(MONTHS_PER_YEAR):
            profit = revenue - expense * PROFIT_TAX_RATE

    elif expenseType == EXPENSE_TYPE_OVERHEAD:
        for month in range(MONTHS_PER_YEAR):
            profit = revenue - expense * BASE_EXPENSE_MULTIPLIER

    elif expenseType == EXPENSE_TYPE_OPERATIONS:
        for month in range(MONTHS_PER_YEAR):
            profit = revenue - expenseType * ALT_EXPENSE_MULTIPLIER

    return profit

def processEmployeeFinancials(inputRec: dict, quarter: int, newColor: str, status: list, expenseType: int) -> None:
    """
    Orchestrate financial processing for one employee record.
    Replaces the original HandleStuff() with clear, cohesive steps.
    """
    data = initRevenueExpense(inputRec, quarter)

    revenue = calculateEstimatedRevenue(data["ytdRevenue"], data["quarter"])

    updateCorpDatabase(data["empRec"])

    prevColor, status = updateColorAndStatus(newColor, status)

    profit = calculateProfit(expenseType, revenue, data["corpExpense"])

    print(f"Estimated revenue: {revenue:.2f}")
    print(f"Profit: {profit:.2f}")
    print(f"Previous colour: {prevColor}, Status: {status}")
