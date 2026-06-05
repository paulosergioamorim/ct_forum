package br.ufes.ct_forum.controllers;

import br.ufes.ct_forum.dtos.CreateUserDto;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.services.UsersService;
import org.springdoc.core.annotations.ParameterObject;
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
    public ResponseEntity<PagedModel<User>> findAll(@ParameterObject @PageableDefault(size = 50) Pageable page) {
        return ResponseEntity.ok(new PagedModel<>(usersService.findAll(page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable long id) {
        return ResponseEntity.ok(usersService.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody CreateUserDto dto) {
        User user = usersService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody CreateUserDto dto) {
        usersService.updateById(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        usersService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
