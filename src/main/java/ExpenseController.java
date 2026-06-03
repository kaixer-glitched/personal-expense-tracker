import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/expense")
@RestController
public class ExpenseController {
    private final ExpenseServices expenseServices;
    public  ExpenseController(ExpenseServices expenseServices) {
        this.expenseServices = expenseServices;
    }

    // we will return a ResponseEntity
    // response entity that holds a list of expenses
    // since we want to get all expenses, we must get the list of expenses
    // if we did ResponseEntity<Expense> that means it will hold a single expense, not all of it.
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return ResponseEntity.ok(expenseServices.getAllExpenses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        return expenseServices.getExpenseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense newExpense = expenseServices.addExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExpense);
    }

}
