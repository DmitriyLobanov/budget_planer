package com.lobanov.repositories;

import com.lobanov.enums.RolesEnum;
import com.lobanov.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoles(RolesEnum rolesEnum);
}
