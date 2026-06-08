package com.personalexpensetracker.personalexpensetracker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponseDTO {
    private Long id;
    private BigDecimal amount;
    private String expenseDescription;
}
