package com.lobanov;

import com.lobanov.enums.RoleEnum;
import com.lobanov.enums.UserStatus;
import com.lobanov.models.Role;
import com.lobanov.models.User;
import com.lobanov.repositories.RoleRepository;
import com.lobanov.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BudgetPlanerApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BudgetPlanerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*roleRepository.saveAll(List.of(
                new Role(null, RoleEnum.ROLE_ADMIN ),
                new Role(null, RoleEnum.ROLE_USER )
        ));

        userRepository.save(User
                .builder()
                .username("Dimas")
                .password(passwordEncoder.encode("zxc123"))
                .firstName("A")
                .secondName("B")
                .email("@ass")
                .phoneNumber("70077")
                .roles(roleRepository.findAll())
                .status(UserStatus.ACTIVE)
                .build()
        );*/
    }
}
