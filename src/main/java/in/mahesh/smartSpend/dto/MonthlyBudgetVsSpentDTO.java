package in.mahesh.smartSpend.dto;

public class MonthlyBudgetVsSpentDTO {
    private String month;
    private Double budget;
    private Double spent;

    public MonthlyBudgetVsSpentDTO() {
    }

    public MonthlyBudgetVsSpentDTO(String month, Double budget, Double spent) {
        this.month = month;
        this.budget = budget;
        this.spent = spent;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
