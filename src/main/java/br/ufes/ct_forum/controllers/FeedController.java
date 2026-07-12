package br.ufes.ct_forum.controllers;

import br.ufes.ct_forum.dtos.CreateTopicDto;
import br.ufes.ct_forum.dtos.TopicDetailDto;
import br.ufes.ct_forum.dtos.TopicFeedDto;
import br.ufes.ct_forum.models.Topic;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.services.TopicService;
import br.ufes.ct_forum.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class FeedController {
    private final TopicService topicService;
    private final UserService userService;

    public FeedController(TopicService topicService, UserService userService) {
        this.topicService = topicService;
        this.userService = userService;
    }

    @GetMapping("/feed")
    public String feed(@PageableDefault Pageable pageable, Model model) {
        Page<TopicFeedDto> topics = topicService.findAllForFeed(pageable);
        model.addAttribute("topics", topics);

        return "feed";
    }
    
    @GetMapping("/topic/{id}")
    public String topicDetail(@PathVariable long id, Model model) {
        TopicDetailDto topic = topicService.findDetailById(id);
        model.addAttribute("topic", topic);

        return "topic";
    }


    @GetMapping("/create-topic")
    public String createTopicForm() {
        return "create-topic";
    }

    @PostMapping("/create-topic")
    public String createTopicSubmit(@ModelAttribute("form") CreateTopicDto formDto, Principal principal, Model model) {
        try {
            String userEmail = principal.getName();
            User author = userService.findByEmail(userEmail);
            CreateTopicDto completeDto = new CreateTopicDto(
                    formDto.title(),
                    formDto.content(),
                    author.getId(),
                    formDto.tags()
            );

            topicService.save(completeDto);
            return "redirect:/feed";

        } catch (Exception e) {
            model.addAttribute("error", "Ocorreu um erro ao tentar publicar o tópico. Verifique os dados e tente novamente.");
            return "create-topic";
        }
    }
}
