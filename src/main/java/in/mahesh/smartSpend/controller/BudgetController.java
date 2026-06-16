package in.mahesh.smartSpend.controller;

import in.mahesh.smartSpend.dto.BudgetRequest;
import in.mahesh.smartSpend.dto.BudgetResponse;
import in.mahesh.smartSpend.dto.MonthlyBudgetVsSpentDTO;
import in.mahesh.smartSpend.dto.MonthlySummaryResponse;
import in.mahesh.smartSpend.response.ApiResponse;
import in.mahesh.smartSpend.security.JwtUtil;
import in.mahesh.smartSpend.service.BudgetService;
import in.mahesh.smartSpend.service.BudgetSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BudgetSummaryService budgetSummaryService;

    @PostMapping
    public ResponseEntity<ApiResponse<BudgetResponse>> createBudget(
            @RequestBody BudgetRequest request,
            @RequestHeader("Authorization") String token) {

        String email = jwtUtil.getEmailFromToken(token.substring(7));
        BudgetResponse res = budgetService.createOrUpdateBudget(request, email);

        return ResponseEntity.ok(new ApiResponse<>(true, "Budget saved successfully", res));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BudgetResponse>>> getBudgets(
            @RequestHeader("Authorization") String token,
            @RequestParam Integer month,
            @RequestParam Integer year) {

        String email = jwtUtil.getEmailFromToken(token.substring(7));
        List<BudgetResponse> budgets = budgetService.getBudgetsForMonth(email, month, year);
        return ResponseEntity.ok(new ApiResponse<>(true, "Budgets fetched", budgets));
    }

    @GetMapping("/category")
    public ResponseEntity<ApiResponse<BudgetResponse>> getBudgetByCategory(
            @RequestHeader("Authorization") String token,
            @RequestParam String category,
            @RequestParam Integer month,
            @RequestParam Integer year) {

        String email = jwtUtil.getEmailFromToken(token.substring(7));
        BudgetResponse budget = budgetService.getBudgetByCategory(email, category, month, year);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category budget fetched", budget));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<List<MonthlySummaryResponse>>> getMonthlySummary(
            @RequestHeader("Authorization") String token,
            @RequestParam Integer month,
            @RequestParam Integer year) {

        String email = jwtUtil.getEmailFromToken(token.substring(7));
        List<MonthlySummaryResponse> summary = budgetService.getMonthlySummary(email, month, year);

        return ResponseEntity.ok(new ApiResponse<>(true, "Monthly summary generated", summary));
    }
    @GetMapping("/yearly-vs-spent")
    public ResponseEntity<List<MonthlyBudgetVsSpentDTO>> getYearlyBudgetVsSpent(
            @RequestHeader("Authorization") String token) {

        String pureToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        List<MonthlyBudgetVsSpentDTO> data = budgetSummaryService.getYearlyBudgetVsSpent(pureToken);
        return ResponseEntity.ok(data);
    }

}
