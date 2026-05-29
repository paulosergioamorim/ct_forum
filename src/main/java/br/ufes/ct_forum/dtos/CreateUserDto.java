package br.ufes.ct_forum.dtos;

import br.ufes.ct_forum.models.UserRole;

public record CreateUserDto(String name, String email, String password, String passwordConfirm, UserRole role) {
}
