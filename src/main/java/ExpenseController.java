import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    // wtf that worked?? Oh, wait it was the right one by chat, hooray!
    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> addExpense(@Valid @RequestBody ExpenseRequestDTO expenseRequestDTO) {
        ExpenseResponseDTO newExpense = expenseServices.addExpense(expenseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExpense);
    }

    // added /total so that spring don't get confused which one to call between all GETs
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalExpenses() {
        return ResponseEntity.ok(expenseServices.getTotalExpenses());
    }
}
