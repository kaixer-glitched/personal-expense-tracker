package com.personalexpensetracker.personalexpensetracker;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ExpenseServices {
    // TODO: Convert this project into JPA

    private final ExpenseRepository expenseRepository;

    public ExpenseServices(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // we'll use an ArrayList to get all expenses
    List<Expense> expenses = new ArrayList<>();

    // we will use a hashmap for faster lookups
    Map<Long, Expense> expensesById = new HashMap<>();


    // findAll() returns a List
    public List<Expense> getAllExpenses() { return expenseRepository.findAll(); }

    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
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
        expense.setAmount(expenseRequestDTO.getAmount());
        expense.setExpenseDescription(expenseRequestDTO.getExpenseDescription());

        Expense savedExpense = expenseRepository.save(expense);

        return toResponseDTO(savedExpense);
    }

    private ExpenseResponseDTO toResponseDTO(Expense expense) {
        ExpenseResponseDTO dto = new ExpenseResponseDTO();
        dto.setId(expense.getId());
        dto.setAmount(expense.getAmount());
        dto.setExpenseDescription(expense.getExpenseDescription());

        return dto;
    }
    // Returns an Optional
    // Either way, if it doesn't exist it will return an Optional generic
    public Optional<Expense> deleteExpenseById(Long id) {
        Optional<Expense> deleteExpense = getExpenseById(id);

        deleteExpense.ifPresent(expense -> expenses.remove(expense));

        return deleteExpense;
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
