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

import java.util.ArrayList;
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
                // .remainder(calculateRemainderInCategory(category.getId()))
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
                .expenseValue(expense.getExpense())
                .expensesDateTime(expense.getExpensesDateTime())
                .categoryName(expense.getCategory().getName())
                .build();
    }

    private Long calculateRemainderInCategory(CategoryDto categoryDto) {
        Long res = categoryDto.getExpensesList().stream().mapToLong(Expense::getExpense).sum();
        return categoryDto.getLimit() - res;
    }

    public CategoryDto addCategory(CategoryDto categoryDto) {
        User userById = userRepository.getUserById(categoryDto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Category category = mapDtoToCategory(categoryDto);
        category.setUser(userById);
        Category save = categoryRepository.save(category);
        return mapCategoryToDto(save);
    }

    public CategoryDto updateCategory(CategoryDto categoryDto) {
        List<Expense> expenseList = new ArrayList<>(categoryDto.getExpensesList());
        categoryDto.getExpensesList().clear();
        Category category = mapDtoToCategory(categoryDto);
        category.setExpensesList(expenseList);
        categoryRepository.save(category);
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
        //collect.forEach(c -> c.setRemainder(calculateRemainderInCategory(c)));
        return categoryRepository.findAllByUserId(id).stream()
                .map(this::mapCategoryToDto)
                .peek(c -> c.setRemainder(calculateRemainderInCategory(c)))
                .collect(Collectors.toList());
    }

    public ExpenseDto addExpensesToCategory(Long userId, Long expenseValue, String categoryName) {
        User user = userRepository.getUserById(userId).orElseThrow(() -> new UserNotFoundException("Cannot find user"));
        Category category = categoryRepository.findCategoryByNameAndUser(categoryName, user).orElseThrow(() -> new CategoryNotFoundException("Cannot find category"));
        Expense expense = new Expense(null, new Date(), expenseValue, category);
        //expensesRepository.save(expense);
        return mapExpenseToDto(expensesRepository.save(expense));
    }
}
