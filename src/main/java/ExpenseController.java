import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
