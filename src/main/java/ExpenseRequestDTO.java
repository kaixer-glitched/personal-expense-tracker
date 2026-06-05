import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenseRequestDTO {
    @DecimalMin(value = ".1", message = "Amount cannot be lower than .1")
    private BigDecimal amount;

    @NotBlank(message = "Expense description cannot be empty.")
    private String expenseDescription;
}
