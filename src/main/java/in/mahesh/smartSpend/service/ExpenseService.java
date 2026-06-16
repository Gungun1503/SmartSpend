package in.mahesh.smartSpend.service;

import in.mahesh.smartSpend.dto.ExpenseRequest;
import in.mahesh.smartSpend.dto.ExpenseResponse;
import in.mahesh.smartSpend.entity.Expense;
import in.mahesh.smartSpend.entity.User;
import in.mahesh.smartSpend.repository.ExpenseRepository;
import in.mahesh.smartSpend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;

    public ExpenseResponse saveExpense(ExpenseRequest expenseRequest,String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));
        Expense expense = new Expense(
                expenseRequest.getCategory(),
                expenseRequest.getExpenseName(),
                expenseRequest.getDescription(),
                expenseRequest.getAmount(),
                user
        );
        Expense saved = expenseRepository.save(expense);
        return new ExpenseResponse(
                saved.getId(),
                saved.getCategory(),
                saved.getExpenseName(),
                saved.getDescription(),
                saved.getAmount(),
                saved.getCreatedAt()
        );
    }

    public List<ExpenseResponse> getAllExpense(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User Not found"));
        List<Expense> expenses = expenseRepository.findByUser(user);
        List<ExpenseResponse> result = new ArrayList<>();

        for(Expense e : expenses){
            result.add(new ExpenseResponse(
                    e.getId(),
                    e.getCategory(),
                    e.getExpenseName(),
                    e.getDescription(),
                    e.getAmount(),
                    e.getCreatedAt()
            ));
        }
        return result;
    }

    public ExpenseResponse updateExpense(Long expenseId, ExpenseRequest request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to update this expense");
        }

        expense.setCategory(request.getCategory());
        expense.setExpenseName(request.getExpenseName());
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());

        Expense updated = expenseRepository.save(expense);

        return new ExpenseResponse(
                updated.getId(),
                updated.getCategory(),
                updated.getExpenseName(),
                updated.getDescription(),
                updated.getAmount(),
                updated.getCreatedAt()
        );
    }

    // ✅ DELETE Expense
    public void deleteExpense(Long expenseId, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to delete this expense");
        }

        expenseRepository.delete(expense);
    }
}
