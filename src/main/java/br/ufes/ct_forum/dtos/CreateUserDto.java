package br.ufes.ct_forum.dtos;

import br.ufes.ct_forum.models.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * DTO utilizado para encapsular os dados de registro ou atualização de um usuário.
 *
 */
@Schema(description = "Dto para criação de usuário")
public final class CreateUserDto {
    @Schema(description = "Nome do usuário", example = "John Doe")
    private final String name;
    @Schema(description = "Email do usuário", example = "johndoe@gmail.com")
    private final String email;
    @Schema(description = "Senha do usuário", example = "john.doe1$")
    private final String password;
    @Schema(description = "Confirmação de senha do usuário", example = "john.doe1$")
    private final String passwordConfirm;
    @Schema(description = "Role do usuário", example = "USER")
    private final UserRole role = UserRole.USER;

    /**
     * @param name            O nome de exibição do usuário.
     * @param email           O endereço de e-mail utilizado para login.
     * @param password        A senha em texto plano (a ser criptografada pelo Service).
     * @param passwordConfirm A confirmação da senha para validação de erro de digitação.
     */
    public CreateUserDto(@Schema(description = "Nome do usuário", example = "John Doe") String name,
                         @Schema(description = "Email do usuário", example = "johndoe@gmail.com") String email,
                         @Schema(description = "Senha do usuário", example = "john.doe1$") String password,
                         @Schema(description = "Confirmação de senha do usuário", example = "john.doe1$") String passwordConfirm
                         ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    @Schema(description = "Nome do usuário", example = "John Doe")
    public String name() {
        return name;
    }

    @Schema(description = "Email do usuário", example = "johndoe@gmail.com")
    public String email() {
        return email;
    }

    @Schema(description = "Senha do usuário", example = "john.doe1$")
    public String password() {
        return password;
    }

    @Schema(description = "Confirmação de senha do usuário", example = "john.doe1$")
    public String passwordConfirm() {
        return passwordConfirm;
    }

    @Schema(description = "Role do usuário", example = "ADMIN")
    public UserRole role() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CreateUserDto) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.password, that.password) &&
                Objects.equals(this.passwordConfirm, that.passwordConfirm) &&
                Objects.equals(this.role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, passwordConfirm, role);
    }

    @Override
    public String toString() {
        return "CreateUserDto[" +
                "name=" + name + ", " +
                "email=" + email + ", " +
                "password=" + password + ", " +
                "passwordConfirm=" + passwordConfirm + ", " +
                "role=" + role + ']';
    }

}
