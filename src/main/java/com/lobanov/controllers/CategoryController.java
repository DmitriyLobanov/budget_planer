package com.lobanov.controllers;

import com.lobanov.dto.CategoryDto;
import com.lobanov.dto.ExpenseDto;
import com.lobanov.security.jwt.JwtUser;
import com.lobanov.service.CategoryService;
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
        ExpenseDto expenseDto = categoryService.addExpensesToCategory(user.getId(), payload.getExpenseValue(), payload.getCategoryName());
        return ResponseEntity.ok(expenseDto);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto payload) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        payload.setUserId(user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(payload));
    }

    @PutMapping("/me/{id}")
    public ResponseEntity<CategoryDto> changeCategoryParameters(@RequestBody CategoryDto payload, @PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (categoryDto == null) {
            categoryDto = payload;
            categoryDto.setUserId(user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(categoryDto));
        }
        categoryDto.setLimit(payload.getLimit());
        categoryDto.setName(payload.getName());

        return ResponseEntity.ok(categoryService.updateCategory(categoryDto));
    }


}
