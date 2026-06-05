import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ExpenseResponseDTO {
    private BigDecimal amount;
    private String expenseDescription;
}
