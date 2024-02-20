package dev.aceelv.jwt;

import dev.aceelv.jwt.model.Role;
import dev.aceelv.jwt.model.RoleEnum;
import dev.aceelv.jwt.model.UserEntity;
import dev.aceelv.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class SpringSecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtApplication.class, args);
    }

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Bean
    CommandLineRunner init() {
        return args -> {
            UserEntity userEntity = UserEntity.builder()
                    .email("mar@correo.com")
                    .username("mar")
                    .password(passwordEncoder.encode("1234"))
                    .roles(Set.of(Role.builder().name(RoleEnum.valueOf(RoleEnum.ADMIN.name())).build()))
                    .build();
            UserEntity userEntity2 = UserEntity.builder()
                    .email("sol@correo.com")
                    .username("sol")
                    .password(passwordEncoder.encode("1234"))
                    .roles(Set.of(Role.builder().name(RoleEnum.valueOf(RoleEnum.USER.name())).build()))
                    .build();
            UserEntity userEntity3 = UserEntity.builder()
                    .email("luna@correo.com")
                    .username("luna")
                    .password(passwordEncoder.encode("1234"))
                    .roles(Set.of(Role.builder().name(RoleEnum.valueOf(RoleEnum.INVITED.name())).build()))
                    .build();


            userRepository.save(userEntity);
            userRepository.save(userEntity2);
            userRepository.save(userEntity3);
        };
    }
}
