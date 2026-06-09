package com.personalexpensetracker.personalexpensetracker;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    // @Transactional(readOnly = true) means that we are informing spring that this is read-only
    // that saves a lot of memory and runs more efficiently by reducing load.
    // we referenced BigDecimal totalExpenses by the value that sumAllExpenses() returns (which is a BigDecimal).
    // null checking is important cause SUM() returns null if there is nothing to sum
    // JPA directs that to a Java null coming from SUM() query in repo
    // instead of giving null to whoever calls this method
    // we return BigDecimal.ZERO as a sensible value than null.
    @Transactional(readOnly = true)
    public BigDecimal getTotalExpenses() {
        BigDecimal totalExpenses = expenseRepository.sumAllExpenses();
        return totalExpenses != null ? totalExpenses : BigDecimal.ZERO;
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
        Optional<Expense> expense = expenseRepository.findById(id);

        if (expense.isPresent()) {
            expenseRepository.deleteById(id);
        }

        return expense;
    }

    @Transactional
    public Optional<Expense> updateExpense(Long id, Expense updatedExpense) {
        Optional<Expense> expense = expenseRepository.findById(id);

        if (expense.isPresent()) {
            expense.get().setAmount(updatedExpense.getAmount());
            expense.get().setExpenseDescription(updatedExpense.getExpenseDescription());
        }

        return expense;
    }
}
