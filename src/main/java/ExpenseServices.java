import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseServices {

    // TODO: Clean up redundancy. Remove the 'expenses' ArrayList and rely only on 'expensesById' Map.
    // TODO: To make this thread-safe for Spring Boot, change HashMap to ConcurrentHashMap.

    // we'll use an ArrayList to get all expenses
    List<Expense> expenses = new ArrayList<>();

    // we will use a hashmap for faster lookups
    Map<Long, Expense> expensesById = new HashMap<>();

    // pre-gen ids
    Long firstId = 1L;

    // we'll simply return a list that contains all expenses
    public List<Expense> getAllExpenses() { return expenses; }

    public Optional<Expense> getExpenseById(Long id) {
        return Optional.ofNullable(expensesById.get(id));
    }

    // to be checked if working
    public double getTotalExpenses() {
        double total = 0;

        if (expenses.isEmpty()) return total;

        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        return total;
    }

    // TODO: REFINING ADD LOGIC:
    // 1. Assign an ID automatically using the idCounter (e.g., expense.setId(idCounter++)).
    // 2. Remove 'expenses.add(expense)' once the ArrayList is deleted.
    // 3. Ensure it only saves to the map: expensesById.put(expense.getId(), expense).

    // we can eventually work on concurrent hashmap for thread-safety
    public Expense addExpense(Expense expense) {

        expense.setId(firstId++);
        expenses.add(expense);

        expensesById.put(expense.getId(), expense);

        return expense;
    }
}
