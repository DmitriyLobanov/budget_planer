package com.lobanov.models;

import com.lobanov.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="role_id")
    private Long id;

    @Column(name = "user_role", columnDefinition = "varchar(10) default 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    private RoleEnum roles;

}
