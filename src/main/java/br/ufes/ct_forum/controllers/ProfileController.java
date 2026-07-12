package br.ufes.ct_forum.controllers;

import br.ufes.ct_forum.dtos.TopicFeedDto;
import br.ufes.ct_forum.dtos.UserDto;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.services.TopicService;
import br.ufes.ct_forum.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;


@Controller
public class ProfileController {
    private final UserService userService;
    private final TopicService topicService;

    public ProfileController(UserService userService, TopicService topicService) {
        this.userService = userService;
        this.topicService = topicService;
    }

    @GetMapping("/profile")
    public String ownProfile(@PageableDefault Pageable pageable, Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        return renderProfile(user, "/profile", pageable, model);
    }

    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable long id, @PageableDefault Pageable pageable, Model model) {
        User user = userService.findById(id);
        return renderProfile(user, "/profile/" + id, pageable, model);
    }

    private String renderProfile(User user, String baseUrl, Pageable pageable, Model model) {
        Page<TopicFeedDto> topics = topicService.findByAuthorForFeed(user.getId(), pageable);

        model.addAttribute("user", UserDto.of(user));
        model.addAttribute("topics", topics);
        model.addAttribute("baseUrl", baseUrl);

        return "profile";
    }
}
