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


/**
 * Controlador REST responsável por expor os endpoints de gerenciamento de usuários.
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    /**
     * Construtor para injeção de dependência do serviço de usuários.
     *
     * @param usersService O serviço contendo a lógica de negócios de usuários.
     */
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Recupera uma lista paginada de todos os usuários registrados.
     *
     * @param page Objeto embutido pela requisição contendo parâmetros de paginação.
     * @return Resposta HTTP 200 contendo o modelo paginado de {@link UserDto}.
     */
    @GetMapping
    @Operation(summary = "Busca os usuários do sistema por paginação")
    @ApiResponse(description = "Sucesso", responseCode = "200")
    public ResponseEntity<PagedModel<UserDto>> findAll(@ParameterObject @PageableDefault(size = 50) Pageable page) {
        Page<User> users = usersService.findAll(page);
        PagedModel<UserDto> model = new PagedModel<>(users.map(UserDto::of));
        return ResponseEntity.ok(model);
    }

    /**
     * Recupera os dados de um usuário específico a partir de sua chave primária.
     *
     * @param id O identificador do usuário passado no caminho da URL.
     * @return Resposta HTTP 200 contendo o {@link UserDto}, ou 404 caso não exista.
     */
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

    /**
     * Cria uma nova conta de usuário no sistema.
     *
     * @param dto O corpo da requisição mapeado para {@link CreateUserDto}.
     * @return Resposta HTTP 201 (Created) com os dados públicos do usuário criado.
     */
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

    /**
     * Atualiza parcialmente os dados de um usuário existente.
     *
     * @param id  O identificador do usuário a ser alterado.
     * @param dto Os novos dados a serem aplicados.
     * @return Resposta HTTP 200 (OK) vazia em caso de sucesso.
     */
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

    /**
     * Remove fisicamente um usuário do sistema.
     *
     * @param id O identificador do usuário a ser deletado.
     * @return Resposta HTTP 200 (OK) vazia em caso de sucesso.
     */
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
