package br.ufes.ct_forum.controllers;

import br.ufes.ct_forum.dtos.CreateUserDto;
import br.ufes.ct_forum.exception.EmailAlreadyExists;
import br.ufes.ct_forum.exception.PasswordsDoesNotMatch;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.services.UsersService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<PagedModel<User>> findAll(Pageable page) {
        return ResponseEntity.ok(new PagedModel<>(usersService.findAll(page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(long id) {
        Optional<User> user = usersService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody CreateUserDto dto) throws URISyntaxException, EmailAlreadyExists, PasswordsDoesNotMatch {
        User user = usersService.save(dto);
        return ResponseEntity.created(new URI("/v1/users/" + user.getId())).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(long id, @RequestBody CreateUserDto dto) throws EmailAlreadyExists, PasswordsDoesNotMatch {
        usersService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(long id) {
        usersService.delete(id);
        return ResponseEntity.ok().build();
    }
}
