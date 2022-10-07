package com.lobanov.dto;

import com.lobanov.models.Expense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
//dto - чтобы возвращать всю полезную инфу req and resp изолированно с моделью
public class CategoryDto {

    private Long id;

    private Double remainder;

    private Double limit;

    private String name;

    private Long userId;

    private List<Expense> expensesList;
}
