package com.lobanov;

import com.lobanov.enums.RolesEnum;
import com.lobanov.enums.UserStatus;
import com.lobanov.models.Role;
import com.lobanov.models.User;
import com.lobanov.repositories.RoleRepository;
import com.lobanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BudgetPlanerApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BudgetPlanerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        roleRepository.saveAll(List.of(
                new Role(null, RolesEnum.ROLE_ADMIN ),
                new Role(null, RolesEnum.ROLE_USER )
        ));

        userRepository.save(User
                .builder()
                .username("Dimas")
                .password("zxc123")
                .firstName("A")
                .secondName("B")
                .email("@ass")
                .phoneNumber("70077")
                .roles(roleRepository.findAll())
                .status(UserStatus.ACTIVE)
                .build()
        );
    }
}
