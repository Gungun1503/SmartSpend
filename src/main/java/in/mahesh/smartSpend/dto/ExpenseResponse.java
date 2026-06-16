package in.mahesh.smartSpend.dto;

import java.time.LocalDateTime;

public class ExpenseResponse {
    private Long id;
    private String category;
    private String expenseName;
    private String description;
    private Double amount;
    private LocalDateTime createdAt;

    public ExpenseResponse(Long id, String category, String expenseName, String description, Double amount, LocalDateTime createdAt) {
        this.id = id;
        this.category = category;
        this.expenseName = expenseName;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
