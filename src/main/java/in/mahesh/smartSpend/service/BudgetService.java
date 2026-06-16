package in.mahesh.smartSpend.service;

import in.mahesh.smartSpend.dto.BudgetRequest;
import in.mahesh.smartSpend.dto.BudgetResponse;
import in.mahesh.smartSpend.dto.MonthlySummaryResponse;
import in.mahesh.smartSpend.entity.Budget;
import in.mahesh.smartSpend.entity.Expense;
import in.mahesh.smartSpend.entity.User;
import in.mahesh.smartSpend.repository.BudgetRepository;
import in.mahesh.smartSpend.repository.ExpenseRepository;
import in.mahesh.smartSpend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    public BudgetResponse createOrUpdateBudget(BudgetRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Budget budget = budgetRepository
                .findByUserAndCategoryAndMonthAndYear(user, request.getCategory(), request.getMonth(), request.getYear())
                .orElse(new Budget());

        budget.setUser(user);
        budget.setAmount(request.getAmount());
        budget.setCategory(request.getCategory());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());

        budgetRepository.save(budget);

        return new BudgetResponse(
                budget.getCategory(),
                budget.getAmount(),
                budget.getMonth(),
                budget.getYear()
        );
    }

    public List<BudgetResponse> getBudgetsForMonth(String email, Integer month, Integer year) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Budget> budgets = budgetRepository.findByUserAndMonthAndYear(user, month, year);

        return budgets.stream()
                .map(b -> new BudgetResponse(
                        b.getCategory(),
                        b.getAmount(),
                        b.getMonth(),
                        b.getYear()
                )).toList();
    }

    public BudgetResponse getBudgetByCategory(String email, String category, Integer month, Integer year) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Budget b = budgetRepository.findByUserAndCategoryAndMonthAndYear(user, category, month, year)
                .orElseThrow(() -> new RuntimeException("No budget found for category"));

        return new BudgetResponse(
                b.getCategory(),
                b.getAmount(),
                b.getMonth(),
                b.getYear()
        );
    }

    public List<MonthlySummaryResponse> getMonthlySummary(String email, Integer month, Integer year) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch all expenses for the month
        List<Expense> expenses = expenseRepository.findByUserAndMonthAndYear(user, month, year);

        // Group expenses by category and calculate total per category
        Map<String, Double> totalSpentPerCategory = new HashMap<>();
        double totalSpent = 0;

        for (Expense e : expenses) {
            String category = e.getCategory();
            double amount = e.getAmount();

            totalSpentPerCategory.put(category,
                    totalSpentPerCategory.getOrDefault(category, 0.0) + amount);

            totalSpent += amount;
        }

        // Fetch budget entries for the same month
        List<Budget> budgets = budgetRepository.findByUserAndMonthAndYear(user, month, year);
        Map<String, Double> budgetMap = new HashMap<>();

        for (Budget b : budgets) {
            budgetMap.put(b.getCategory(), b.getAmount());
        }

        // Build response
        List<MonthlySummaryResponse> responseList = new ArrayList<>();

        // Add category-wise summaries
        for (String category : totalSpentPerCategory.keySet()) {
            double spent = totalSpentPerCategory.get(category);
            double budget = budgetMap.getOrDefault(category, 0.0);
            responseList.add(new MonthlySummaryResponse(category, budget, spent));
        }

        // Add overall summary
        double overallBudget = budgetMap.getOrDefault("Overall", 0.0);
        responseList.add(new MonthlySummaryResponse("Overall", overallBudget, totalSpent));

        return responseList;
    }
}
