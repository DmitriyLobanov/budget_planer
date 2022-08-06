package com.lobanov.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expenses_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expensesDateTime;

    @Column(name = "expense")
    private Long expense;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;
}

