import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Expense {
    private Long id;

    @DecimalMin(value = ".1", message = "Amount cannot be lower than .1")
    private BigDecimal amount;

    @NotBlank(message = "Product name cannot be empty.")
    private String productName;
}
