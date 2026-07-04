package br.ufes.ct_forum.controllers;

import br.ufes.ct_forum.dtos.CreateUserDto;
import br.ufes.ct_forum.exceptions.EmailAlreadyExists;
import br.ufes.ct_forum.exceptions.PasswordsDoNotMatch;
import br.ufes.ct_forum.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerForm(@ModelAttribute("form") CreateUserDto dto, Model model) {
        try {
            userService.save(dto);
            return "redirect:/login";
        } catch (PasswordsDoNotMatch e) {
            model.addAttribute("error", "As senhas informadas não coincidem.");
            return "register";
        } catch (EmailAlreadyExists e) {
            model.addAttribute("error", "O e-mail informado já está em uso.");
            return "register";
        }
    }
}
