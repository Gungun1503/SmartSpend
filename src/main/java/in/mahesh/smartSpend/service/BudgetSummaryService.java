package in.mahesh.smartSpend.service;

import in.mahesh.smartSpend.dto.MonthlyBudgetVsSpentDTO;
import in.mahesh.smartSpend.entity.Budget;
import in.mahesh.smartSpend.entity.Expense;
import in.mahesh.smartSpend.entity.User;
import in.mahesh.smartSpend.repository.BudgetRepository;
import in.mahesh.smartSpend.repository.ExpenseRepository;
import in.mahesh.smartSpend.repository.UserRepository;
import in.mahesh.smartSpend.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetSummaryService {
    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public BudgetSummaryService(BudgetRepository budgetRepository, ExpenseRepository expenseRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.budgetRepository = budgetRepository;
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<MonthlyBudgetVsSpentDTO> getYearlyBudgetVsSpent(String token) {
        String email = jwtUtil.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<MonthlyBudgetVsSpentDTO> result = new ArrayList<>();
        int year = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue(); // Get current month number

        for (int month = 1; month <= currentMonth; month++) { // ✅ Loop only till current month
            List<Budget> budgets = budgetRepository.findByUserAndMonthAndYear(user, month, year);
            double totalBudget = budgets.stream().mapToDouble(Budget::getAmount).sum();

            LocalDateTime startOfMonth = LocalDate.of(year, month, 1).atStartOfDay();
            LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

            List<Expense> expenses = expenseRepository.findByUserAndCreatedAtBetween(user, startOfMonth, endOfMonth);
            double totalSpent = expenses.stream().mapToDouble(Expense::getAmount).sum();

            result.add(new MonthlyBudgetVsSpentDTO(Month.of(month).name(), totalBudget, totalSpent));
        }

        return result;
    }
}
