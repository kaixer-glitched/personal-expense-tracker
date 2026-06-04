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
    public Expense addExpense(Expense expense) {

        expense.setId(firstId++);
        expenses.add(expense);

        expensesById.put(expense.getId(), expense);

        return expense;
    }
}
