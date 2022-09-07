package com.lobanov.repositories;

import com.lobanov.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expense, Long> {

    List<Expense> getExpensesByCategoryId(Long id);

}
