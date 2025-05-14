package com.example.Booking_Resort.config;

import com.example.Booking_Resort.constant.PredefinedRole;
import com.example.Booking_Resort.models.Roles;
import com.example.Booking_Resort.models.User;
import com.example.Booking_Resort.repository.RoleRepository;
import com.example.Booking_Resort.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig
{
    PasswordEncoder passwordEncoder;

    @NonFinal
    private final String ADMIN_USER_NAME = "admin";
    @NonFinal
    private final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository)
    {
        return args -> {
            if(userRepository.findByAccount(ADMIN_USER_NAME).isEmpty())
            {
                Roles adminRole = roleRepository.save(Roles.builder()
                                .name(PredefinedRole.ADMIN_ROLE)
                                .description("Admin Role")
                                .build());

                var roles = new HashSet<Roles>();
                roles.add(adminRole);

                User user = User.builder()
                        .account(ADMIN_USER_NAME)
                        .passworduser(passwordEncoder.encode(ADMIN_PASSWORD))
                        .role_user(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin created with account admin: {}", user.getAccount());
            }
        };
    }
}
