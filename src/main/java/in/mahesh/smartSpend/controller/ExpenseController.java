package in.mahesh.smartSpend.controller;

import in.mahesh.smartSpend.dto.ExpenseRequest;
import in.mahesh.smartSpend.dto.ExpenseResponse;
import in.mahesh.smartSpend.response.ApiResponse;
import in.mahesh.smartSpend.security.JwtUtil;
import in.mahesh.smartSpend.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseResponse>> saveExpense(
            @RequestBody ExpenseRequest req,
            @RequestHeader("Authorization") String token) {

        System.out.println(token.substring(7));
        String email = jwtUtil.getEmailFromToken(token.substring(7));
        ExpenseResponse saved = expenseService.saveExpense(req, email);

        ApiResponse<ExpenseResponse> response =
                new ApiResponse<>(true, "Expense saved successfully", saved);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> getAllExpenses(
            @RequestHeader("Authorization") String token) {

        String email = jwtUtil.getEmailFromToken(token.substring(7));
        List<ExpenseResponse> expenses = expenseService.getAllExpense(email);

        ApiResponse<List<ExpenseResponse>> response =
                new ApiResponse<>(true, "Fetched all expenses", expenses);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponse>> updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseRequest req,
            @RequestHeader("Authorization") String token) {

        String email = jwtUtil.getEmailFromToken(token.substring(7));
        ExpenseResponse updated = expenseService.updateExpense(id, req, email);

        ApiResponse<ExpenseResponse> response =
                new ApiResponse<>(true, "Expense updated successfully", updated);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseResponse>> deleteExpense(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        String email = jwtUtil.getEmailFromToken(token.substring(7));
        expenseService.deleteExpense(id, email);

        ApiResponse<ExpenseResponse> response =
                new ApiResponse<>(true, "Expense deleted successfully", null);

        return ResponseEntity.ok(response);
    }
}
