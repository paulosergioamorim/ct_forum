package br.ufes.ct_forum.dtos;

import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.models.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jspecify.annotations.NonNull;

@Schema(description = "Usuário do sistema")
public record UserDto(@Schema(description = "Id único do usuário", example = "10") long id,
                      @Schema(description = "Nome do usuário", example = "John Doe") String name,
                      @Schema(description = "Email do usuário", example = "johndoe@gmail.com") String email,
                      @Schema(description = "Role do usuário", example = "USER") UserRole role) {
    public static UserDto of(@NonNull User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
