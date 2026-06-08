package com.personalexpensetracker.personalexpensetracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query(value = "SELECT SUM(e.amount) FROM Expense e")
    BigDecimal sumAllExpenses();
}
