package dev.aceelv.jwt.controller;

import dev.aceelv.jwt.controller.request.CreateUserDto;
import dev.aceelv.jwt.model.Role;
import dev.aceelv.jwt.model.RoleEnum;
import dev.aceelv.jwt.model.UserEntity;
import dev.aceelv.jwt.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class PrincipalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/public")
    public String helloPublic() {
        return "Hola mundo";
    }

    @GetMapping("/secured")
    public String helloSecured() {
        return "Hola mundo";
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDto createUserDto) {

        Set<Role> roles = createUserDto.roles().stream()
                .map(role -> Role.builder()
                            .name(RoleEnum.valueOf(role.toUpperCase()))
                            .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDto.username())
                .password(passwordEncoder.encode(createUserDto.password()))
                .email(createUserDto.email())
                .roles(roles)
                .build();

        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);

        return ResponseEntity.ok("Usuario eliminado");
    }
}
