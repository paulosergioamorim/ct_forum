package br.ufes.ct_forum.dtos;

import br.ufes.ct_forum.models.UserRole;

public class UserRegisterDto {
    private final String name;
    private final String email;
    private final String password;
    private final String confirmPassword;
    private final UserRole role = UserRole.USER;

    public UserRegisterDto(String name, String email, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRole getRole() {
        return role;
    }
}
