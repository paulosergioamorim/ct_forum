package br.ufes.ct_forum.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufes.ct_forum.dtos.CreateRatingDto;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.services.RatingService;
import br.ufes.ct_forum.services.UserService;

@Controller
public class RatingController {
    private final RatingService ratingService;
    private final UserService userService;

    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @PostMapping("/post/{postId}/rate")
    public String rate(@PathVariable long postId,
                        @RequestParam boolean isPositive,
                        @RequestHeader(value = "Referer", required = false) String referer,
                        Principal principal) {
        User user = userService.findByEmail(principal.getName());
        CreateRatingDto dto = new CreateRatingDto(postId, user.getId(), isPositive);

        // Usando excessão como controle de fluxo. Proibido em 128 países...
        // Consertar depois
        try {
            ratingService.save(dto);
        } catch (IllegalStateException e) {
            ratingService.update(dto); // já existia voto -> troca like<->dislike
        }

        return "redirect:" + (referer != null ? referer : "/feed");
    }

    @PostMapping("/post/{postId}/rate/remove")
    public String removeRating(@PathVariable long postId,
                                @RequestHeader(value = "Referer", required = false) String referer,
                                Principal principal) {
        User user = userService.findByEmail(principal.getName());
        ratingService.deleteRating(postId, user.getId());
        return "redirect:" + (referer != null ? referer : "/feed");
    }
}
