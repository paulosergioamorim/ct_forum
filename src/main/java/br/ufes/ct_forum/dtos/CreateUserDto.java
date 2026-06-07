package br.ufes.ct_forum.dtos;

import br.ufes.ct_forum.models.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dto para criação de usuário")
public record CreateUserDto(@Schema(description = "Nome do usuário", example = "John Doe") String name,
                            @Schema(description = "Email do usuário", example = "johndoe@gmail.com") String email,
                            @Schema(description = "Senha do usuário", example = "john.doe1$") String password,
                            @Schema(description = "Confirmação de senha do usuário", example = "john.doe1$") String passwordConfirm,
                            @Schema(description = "Role do usuário", example = "ADMIN") UserRole role) {
}
