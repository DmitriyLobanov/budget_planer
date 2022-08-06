package com.lobanov.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name  = "expenses_limit")
    private Long limit;

    @Column(name = "name")
    private String name;

    @Column(name = "expenses")
    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Expense> expensesList;

    @ManyToOne
    private User user;
}
