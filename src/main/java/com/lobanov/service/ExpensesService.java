package com.lobanov.service;

import com.lobanov.models.Expense;
import com.lobanov.repositories.ExpensesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpensesService {

    private final ExpensesRepository expensesRepository;

    public List<Expense> getAllExpensesByCategoryId(Long id) {
       return expensesRepository.getExpensesByCategoryId(id);
    }
}
