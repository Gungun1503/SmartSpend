package in.mahesh.smartSpend.repository;

import in.mahesh.smartSpend.entity.Budget;
import in.mahesh.smartSpend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long> {
    List<Budget> findByUserAndMonthAndYear(User user, Integer month, Integer year);
    Optional<Budget> findByUserAndCategoryAndMonthAndYear(User user, String category, Integer month, Integer year);
}
