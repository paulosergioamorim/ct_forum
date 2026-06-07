package br.ufes.ct_forum.controllers;

import br.ufes.ct_forum.dtos.CreateUserDto;
import br.ufes.ct_forum.dtos.ErrorDto;
import br.ufes.ct_forum.dtos.UserDto;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    @Operation(summary = "Busca os usuários do sistema por paginação")
    @ApiResponse(description = "Sucesso", responseCode = "200")
    public ResponseEntity<PagedModel<UserDto>> findAll(@ParameterObject @PageableDefault(size = 50) Pageable page) {
        Page<User> users = usersService.findAll(page);
        PagedModel<UserDto> model = new PagedModel<>(users.map(UserDto::of));
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário pelo ID")
    @ApiResponses({
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Não encontrado", responseCode = "404")
    })
    public ResponseEntity<UserDto> findById(@PathVariable long id) {
        UserDto dto = UserDto.of(usersService.findById(id));
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Cria um usuário")
    @ApiResponses({
            @ApiResponse(description = "Sucesso", responseCode = "201", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(description = "Email já em uso", responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(description = "Senhas não coincidem", responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<UserDto> save(@RequestBody CreateUserDto dto) {
        User user = usersService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDto.of(user));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza parcialmente um usuário pelo ID")
    @ApiResponses({
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Não encontrado", responseCode = "404"),
            @ApiResponse(description = "Email já em uso", responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(description = "Senhas não coincidem", responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody CreateUserDto dto) {
        usersService.updateById(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um usuário pelo ID")
    @ApiResponses({
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Não encontrado", responseCode = "404")
    })
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        usersService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
