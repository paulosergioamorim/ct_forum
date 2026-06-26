package br.ufes.ct_forum.controllers;

import br.ufes.ct_forum.models.Topic;
import br.ufes.ct_forum.services.TopicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feed")
public class FeedController {

    private final TopicService topicService;

    public FeedController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public String feed(@PageableDefault Pageable pageable, Model model) {
        Page<Topic> topics = topicService.findAll(pageable);
        model.addAttribute("topics", topics);

        return "feed";
    }
}
