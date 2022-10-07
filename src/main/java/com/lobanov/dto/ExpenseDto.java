package com.lobanov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class ExpenseDto {

    private Long categoryId;

    private Date expenseTimeStamp;

    private Double expenseValue;

    private String categoryName;
}
