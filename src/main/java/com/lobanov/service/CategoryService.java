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
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
                .expenseValue(expense.getExpenseValue())
                .expenseTimeStamp(expense.getExpenseTimeStamp())
                .categoryName(expense.getCategory().getName())
                .build();
    }

    private Double calculateRemainderInCategory(CategoryDto categoryDto) {
        Double res = categoryDto.getExpensesList().stream().mapToDouble(Expense::getExpenseValue).sum();
        return categoryDto.getLimit() - res;
    }

    public CategoryDto addCategory(CategoryDto payload) {
        User userById = userRepository.getUserById(payload.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Category category = mapDtoToCategory(payload);
        category.setUser(userById);
        Category save = categoryRepository.save(category);
        CategoryDto categoryDto = mapCategoryToDto(save);
        categoryDto.setRemainder(payload.getRemainder());
        return categoryDto;
    }

    public CategoryDto updateCategory(CategoryDto payload) {
        Category category = categoryRepository.findById(payload.getId()).orElseThrow(() -> new CategoryNotFoundException("Not found"));
        category.setName(payload.getName());
        category.setLimit(payload.getLimit());
        categoryRepository.save(category);
        CategoryDto categoryDto = mapCategoryToDto(category);
        categoryDto.setRemainder(calculateRemainderInCategory(categoryDto));
        categoryDto.setId(category.getId());
        return categoryDto;
    }

    public CategoryDto getCategoryById(Long id, Long userId) {

        Optional<Category> optionalCategory = categoryRepository.findCategoryById(id);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException("Category with id: " + id  + " not found");
        }
        Category category = optionalCategory.get();
        if (!category.getUser().getId().equals(userId)) {
            throw new CategoryNotFoundException("Category with id: " + id  + " not found");
        }
        CategoryDto categoryDto = mapCategoryToDto(category);
        categoryDto.setRemainder(calculateRemainderInCategory(categoryDto));
        return categoryDto;
    }

    public List<CategoryDto> getAllCategoriesByUserId(Long id) {
        return categoryRepository.findAllByUserId(id).stream()
                .map(this::mapCategoryToDto)
                .peek(c -> c.setRemainder(calculateRemainderInCategory(c)))
                .collect(Collectors.toList());
    }

    public ExpenseDto addExpensesToCategory(Long userId, Double expenseValue, Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findCategoryById(categoryId);
        if(optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException("Category with id = " + categoryId + " not found");
        }
        Category category = optionalCategory.get();
        if (!category.getUser().getId().equals(userId)) {
            throw new CategoryNotFoundException("Category doesn`t exist");
        }
        Expense expense = new Expense(null, new Date(), expenseValue, category);
        return mapExpenseToDto(expensesRepository.save(expense));
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
