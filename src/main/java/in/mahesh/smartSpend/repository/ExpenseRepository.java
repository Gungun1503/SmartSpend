package in.mahesh.smartSpend.repository;

import in.mahesh.smartSpend.entity.Expense;
import in.mahesh.smartSpend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByUserAndCategory(User user, String category);
    List<Expense> findByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);
    @Query("SELECT e FROM Expense e WHERE e.user = :user AND FUNCTION('MONTH', e.createdAt) = :month AND FUNCTION('YEAR', e.createdAt) = :year")
    List<Expense> findByUserAndMonthAndYear(@Param("user") User user, @Param("month") Integer month, @Param("year") Integer year);

}
