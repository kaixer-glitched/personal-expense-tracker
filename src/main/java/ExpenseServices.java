import org.springframework.stereotype.Service;

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

    public double getTotalExpenses() {

        double total = 0;

        if (expenses.isEmpty()) return total;

        for (Expense expense : expenses) { total += expense.getAmount(); }

        return total;
    }

    // we can eventually work on concurrent hashmap for thread-safety
    // NOTE: concurrent or not, this one is just fine.
    public Expense addExpense(Expense expense) {

        expense.setId(firstId++);
        expenses.add(expense);

        expensesById.put(expense.getId(), expense);

        return expense;
    }
}
