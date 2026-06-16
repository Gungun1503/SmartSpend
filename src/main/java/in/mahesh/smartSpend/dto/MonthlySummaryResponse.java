package in.mahesh.smartSpend.dto;

public class MonthlySummaryResponse {
    private String category;
    private Double budget;
    private Double spent;

    public MonthlySummaryResponse() {}

    public MonthlySummaryResponse(String category, Double budget, Double spent) {
        this.category = category;
        this.budget = budget;
        this.spent = spent;
    }

    // Getters & Setters

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getSpent() {
        return spent;
    }

    public void setSpent(Double spent) {
        this.spent = spent;
    }
}