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
    @Column(name = "expense_id")
    private Long id;

    @Column(name = "expense_date_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expenseTimeStamp;

    @Column(name = "expense_value", nullable = false,  columnDefinition = "numeric(12, 2)")
    private Double expenseValue;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;
}

