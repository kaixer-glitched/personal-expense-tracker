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

    // we'll simply return a list that contains all expenses
    public List<Expense> getAllExpenses() {
        return expenses;
    }

    public Optional<Expense> getExpenseById(Long id) {
        return Optional.ofNullable(expensesById.get(id));
    }


}
