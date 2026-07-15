package br.ufes.ct_forum.controllers;

import br.ufes.ct_forum.config.SecurityConfig;
import br.ufes.ct_forum.dtos.TopicFeedDto;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.services.TopicService;
import br.ufes.ct_forum.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedController.class)
@Import(SecurityConfig.class)
class FeedControllerTest {

    private final MockMvc mockMvc;

    @MockitoBean
    private TopicService topicService;

    @MockitoBean
    private UserService userService;

    @Autowired
    FeedControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("Deve barrar usuário não autenticado no /feed e redirecionar para login")
    void accessFeed_Unauthenticated_ShouldRedirect() throws Exception {
        mockMvc.perform(get("/feed"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @DisplayName("Deve permitir acesso ao /feed para usuário autenticado e injetar a lista de tópicos no Model")
    void accessFeed_Authenticated_ShouldReturnFeedView() throws Exception {
        String userEmail = "aluno@ufes.br";
        Long expectedUserId = 1L;
        
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockUser.getId()).thenReturn(expectedUserId);
        Mockito.when(userService.findByEmail(userEmail)).thenReturn(mockUser);
        
        Page<TopicFeedDto> mockPage = new PageImpl<>(Collections.emptyList());
        Mockito.when(topicService.findAllForFeed(any(Pageable.class), eq(expectedUserId)))
               .thenReturn(mockPage);

        mockMvc.perform(get("/feed").with(user(userEmail).roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("feed"))
                .andExpect(model().attributeExists("topics"));

        Mockito.verify(userService).findByEmail(userEmail);
        Mockito.verify(topicService).findAllForFeed(any(Pageable.class), eq(expectedUserId));
        Mockito.verifyNoMoreInteractions(userService, topicService);
    }
}
