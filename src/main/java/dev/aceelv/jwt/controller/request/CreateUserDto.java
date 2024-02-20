package dev.aceelv.jwt.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateUserDto(
        @Email
        @NotBlank
        @Size(max = 80)
        String email,
        @NotBlank
        @Size(max = 30)
        String username,
        @NotBlank
        String password,
        @NotNull
        Set<String> roles
) {
}
