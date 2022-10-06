package com.lobanov.controllers;

import com.lobanov.dto.CategoryDto;
import com.lobanov.dto.ExpenseDto;
import com.lobanov.dto.response.UserDtoResponse;
import com.lobanov.security.jwt.JwtUser;
import com.lobanov.service.CategoryService;
import com.lobanov.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<List<CategoryDto>> getAllCategoriesByUserId() {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("USER ID = {}", user.getId());
        return ResponseEntity.ok(categoryService.getAllCategoriesByUserId(user.getId()));
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping("/me")
    public ResponseEntity<ExpenseDto> addExpensesToCategory(@RequestBody ExpenseDto payload) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("USER ID = {}", user.getId());
        ExpenseDto expenseDto = categoryService.addExpensesToCategory(user.getId(), payload.getExpenseValue(), payload.getCategoryId());
        return ResponseEntity.ok(expenseDto);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto payload) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        payload.setUserId(user.getId());
        CategoryDto categoryDto = categoryService.addCategory(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
    }

    @PutMapping("/me/{id}")
    public ResponseEntity<CategoryDto> changeCategoryParameters(@RequestBody CategoryDto payload, @PathVariable Long id) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if (categoryDto == null) {
            UserDtoResponse userDto = userService.getUserById(user.getId());
            categoryDto = new CategoryDto(null, null, null, payload.getLimit(), payload.getName(), userDto.getId());
            categoryService.addCategory(categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
        }
        categoryDto.setName(payload.getName());
        categoryDto.setLimit(payload.getLimit());
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto));
    }

    @DeleteMapping("/me/{id}")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }
}
