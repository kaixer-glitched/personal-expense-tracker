import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Expense {
    private Long id;
    private double amount;
    private String productName;
}
