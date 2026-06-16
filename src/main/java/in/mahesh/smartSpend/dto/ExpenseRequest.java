package in.mahesh.smartSpend.dto;

public class ExpenseRequest {
    private String category;
    private String expenseName;
    private String description;
    private Double amount;

    public ExpenseRequest() {

    }

    public ExpenseRequest(String category, String expenseName, String description, Double amount) {
        this.category = category;
        this.expenseName = expenseName;
        this.description = description;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
