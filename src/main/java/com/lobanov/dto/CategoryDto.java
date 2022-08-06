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

    private Long remainder;

    private List<Expense> expensesList;

    private Long limit;

    private String name;

    private Long userId;
}
