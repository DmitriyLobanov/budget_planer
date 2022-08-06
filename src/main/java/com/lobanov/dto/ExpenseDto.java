package com.lobanov.dto;

import com.lobanov.models.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class ExpenseDto {

    private Date expensesDateTime;

    private Long expenseValue;

    private String categoryName;
}
