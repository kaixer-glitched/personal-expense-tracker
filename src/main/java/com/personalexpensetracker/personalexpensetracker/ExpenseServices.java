package com.personalexpensetracker.personalexpensetracker;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ExpenseServices {

    // we'll use an ArrayList to get all expenses
    List<Expense> expenses = new ArrayList<>();

    // we will use a hashmap for faster lookups
    Map<Long, Expense> expensesById = new HashMap<>();

    // pre-gen ids
    Long firstId = 1L;

    public List<Expense> getAllExpenses() { return expenses; }

    public Optional<Expense> getExpenseById(Long id) {
        return Optional.ofNullable(expensesById.get(id));
    }

    // we'll return a BigDecimal object since we changed our model for precision
    public BigDecimal getTotalExpenses() {

        BigDecimal total = BigDecimal.ZERO;

        if (expenses.isEmpty()) return total;

        // BigDecimal is immutable
        // so if we just do total.add(x), it really doesnt change the value of total
        // so instead we will re-assign the value that we got from total.add() back to total (self)
        for (Expense expense : expenses) { total = total.add(expense.getAmount()); }

        return total;
    }

    // we can eventually work on concurrent hashmap for thread-safety
    // NOTE: concurrent or not, this one is just fine.
    public ExpenseResponseDTO addExpense(ExpenseRequestDTO expenseRequestDTO) {

        Expense expense = new Expense();
        expense.setExpenseDescription(expenseRequestDTO.getExpenseDescription());
        expense.setAmount(expenseRequestDTO.getAmount());

        expense.setId(firstId++);
        expenses.add(expense);
        expensesById.put(expense.getId(), expense);

        Expense savedExpense = expensesById.get(expense.getId());

        return new ExpenseResponseDTO(
                savedExpense.getAmount(),
                savedExpense.getExpenseDescription()
        );
    }
    // Returns an Optional
    // Either way, if it doesn't exist it will return an Optional generic
    public Optional<Expense> deleteExpenseById(Long id) {
        return Optional.ofNullable(expensesById.get(id));
    }

    public Optional<Expense> updateExpense(Long id, Expense updatedExpense) {
        Expense expense = expensesById.get(id);

        if (expense == null) {
            return Optional.empty();
        }

        expense.setAmount(updatedExpense.getAmount());
        expense.setExpenseDescription(updatedExpense.getExpenseDescription());

        return Optional.of(expense);
    }
}
