package br.ufes.ct_forum.services;

import br.ufes.ct_forum.dtos.CreateCommentDto;
import br.ufes.ct_forum.exceptions.NotFoundException;
import br.ufes.ct_forum.models.Comment;
import br.ufes.ct_forum.models.Topic;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.repositories.CommentRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável por concentrar a lógica de negócio relacionada aos Comentários.
 */
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final TopicService topicService;

    /**
     * Construtor da classe com Injeção de Dependências.
     *
     * @param commentRepository Repositório para operações de I/O na tabela de comentários.
     * @param userService       Serviço de domínio de usuários para recuperação de dados de autoria.
     * @param topicService      Serviço de domínio de tópicos para associação hierárquica.
     */
    public CommentService(CommentRepository commentRepository, UserService userService, TopicService topicService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.topicService = topicService;
    }

    /**
     * Busca um comentário específico pela sua chave primária (ID).
     *
     * @param id O identificador único do comentário.
     * @return A entidade {@link Comment} correspondente.
     * @throws NotFoundException Se nenhum comentário possuir o ID fornecido.
     */
    public Comment findById(long id) {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comentário com id " + id + " não encontrado"));
    }

    /**
     * Busca todas as respostas diretas de um comentário específico, de forma paginada.
     *
     * @param parentId O ID do comentário pai.
     * @param page     Configurações de limite e deslocamento da página.
     * @return Um objeto {@link Page} contendo as respostas encontradas.
     */
    public Page<Comment> findReplies(long parentId, Pageable page) {
        return commentRepository.findByParentId(parentId, page);
    }

    /**
     * Busca todos os comentários feitos por um usuário específico, de forma paginada.
     *
     * @param authorId O ID do usuário autor dos comentários.
     * @param page     Configurações da página.
     * @return Um objeto {@link Page} contendo os comentários encontrados.
     */
    public Page<Comment> findByAuthor(long authorId, Pageable page) {
        return commentRepository.findByAuthorId(authorId, page);
    }

    /**
     * Cria e registra um novo comentário raiz vinculado diretamente a um tópico.
     *
     * @param topicId O identificador do tópico onde o comentário está sendo feito.
     * @param dto     O Data Transfer Object contendo o texto e a autoria da mensagem.
     * @return A entidade {@link Comment} persistida e associada ao tópico.
     * @throws NotFoundException Se o usuário ou o tópico não existirem no banco de dados.
     */
    public Comment createRootComment(long topicId, @NonNull CreateCommentDto dto) {
        User author = userService.findById(dto.authorId());
        Topic topic = topicService.findById(topicId);
        Comment comment = new Comment(dto.content(), author, topic);

        return commentRepository.save(comment);
    }

    /**
     * Cria e registra uma resposta vinculada a um comentário preexistente utilizando o identificador
     * presente no DTO.
     *
     * @param dto O Data Transfer Object contendo o texto, a autoria e a referência do comentário pai.
     * @return A entidade {@link Comment} persistida e associada à árvore de comentários.
     * @throws IllegalArgumentException Se a propriedade parentId do DTO for nula.
     * @throws NotFoundException        Se o autor ou o comentário pai não existirem.
     */
    public Comment createReply(@NonNull CreateCommentDto dto) {
        if (dto.parentId() == null) {
            throw new IllegalArgumentException("O identificador do comentário pai é obrigatório para criar uma resposta.");
        }

        User author = userService.findById(dto.authorId());
        Comment parent = findById(dto.parentId());
        Comment comment = new Comment(dto.content(), author, parent);

        return commentRepository.save(comment);
    }

    /**
     * Atualiza o texto de um comentário existente de forma transacional.
     *
     * @param id  O ID do comentário a ser atualizado.
     * @param dto O DTO contendo o novo texto.
     * @throws NotFoundException Se o comentário a ser atualizado não existir.
     */
    @Transactional
    public void updateById(long id, @NonNull CreateCommentDto dto) {
        Comment comment = findById(id);

        if (dto.content() != null) {
            comment.setContent(dto.content());
        }
    }

    /**
     * Remove fisicamente um comentário do banco de dados.
     *
     * @param id O identificador do comentário a ser removido.
     * @throws NotFoundException Se o ID fornecido não existir previamente.
     */
    public void deleteById(long id) {
        if (!commentRepository.existsById(id)) {
            throw new NotFoundException("Comentário com id " + id + " não encontrado");
        }

        commentRepository.deleteById(id);
    }
}
