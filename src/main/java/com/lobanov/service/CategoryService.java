package com.lobanov.service;

import com.lobanov.dto.CategoryDto;
import com.lobanov.dto.ExpenseDto;
import com.lobanov.exeptions.CategoryNotFoundException;
import com.lobanov.exeptions.UserNotFoundException;
import com.lobanov.models.Category;
import com.lobanov.models.Expense;
import com.lobanov.models.User;
import com.lobanov.repositories.CategoryRepository;
import com.lobanov.repositories.ExpensesRepository;
import com.lobanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ExpensesRepository expensesRepository;

    private CategoryDto mapCategoryToDto(Category category) {
        return CategoryDto
                .builder()
                .id(category.getId())
                .limit(category.getLimit())
                .expensesList(category.getExpensesList())
                .name(category.getName())
                .userId(category.getUser().getId())
                .build();
    }

    private Category mapDtoToCategory(CategoryDto categoryDto) {
        return Category
                .builder()
                .expensesList(categoryDto.getExpensesList())
                .name(categoryDto.getName())
                .limit(categoryDto.getLimit())
                .build();
    }

    private ExpenseDto mapExpenseToDto(Expense expense) {
        return ExpenseDto
                .builder()
                .categoryId(expense.getCategory().getId())
                .expenseValue(expense.getExpense())
                .expensesDateTime(expense.getExpensesDateTime())
                .categoryName(expense.getCategory().getName())
                .build();
    }

    private Long calculateRemainderInCategory(CategoryDto categoryDto) {
        Long res = categoryDto.getExpensesList().stream().mapToLong(Expense::getExpense).sum();
        return categoryDto.getLimit() - res;
    }

    public CategoryDto addCategory(CategoryDto payload) {
        User userById = userRepository.getUserById(payload.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Category category = mapDtoToCategory(payload);
        category.setUser(userById);
        Category save = categoryRepository.save(category);
        return mapCategoryToDto(save);
    }

    public CategoryDto updateCategory(CategoryDto payload) {
        Category category = categoryRepository.findById(payload.getId()).orElseThrow(() -> new CategoryNotFoundException("Not found"));
        category.setName(payload.getName());
        category.setLimit(payload.getLimit());
        categoryRepository.save(category);
        CategoryDto categoryDto = mapCategoryToDto(category);
        categoryDto.setRemainder(calculateRemainderInCategory(categoryDto));
        return categoryDto;
    }

    public CategoryDto getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            return null;
        }
        CategoryDto categoryDto = mapCategoryToDto(category.orElseThrow(() -> new CategoryNotFoundException("Category not found")));
        categoryDto.setRemainder(calculateRemainderInCategory(categoryDto));
        return categoryDto;
    }

    public List<CategoryDto> getAllCategoriesByUserId(Long id) {
        return categoryRepository.findAllByUserId(id).stream()
                .map(this::mapCategoryToDto)
                .peek(c -> c.setRemainder(calculateRemainderInCategory(c)))
                .collect(Collectors.toList());
    }

    public ExpenseDto addExpensesToCategory(Long userId, Long expenseValue, Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        Expense expense = new Expense(null, new Date(), expenseValue, category);
        return mapExpenseToDto(expensesRepository.save(expense));
    }
}
