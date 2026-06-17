package br.ufes.ct_forum.models;

import jakarta.persistence.*;

/**
 * Entidade que representa um usuário do fórum.
 * <p>
 * Gerencia os dados de autenticação e o perfil de acesso (role) no sistema.
 * A tabela é configurada com um índice único na coluna de e-mail para garantir
 * que não existam contas duplicadas.
 * </p>
 */
@Entity
@Table(name = "users", indexes = @Index(name = "user_email_index", columnList = "email", unique = true))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(nullable = false)
    private String name;
    
    /**
     * O endereço de e-mail do usuário.
     */
    @Column(nullable = false)
    private String email;

    /**
     * O hash da senha do usuário.
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * O nível de permissão (papel) do usuário no fórum (ex: ADMIN, USER).
     * <p>
     * O mapeamento utiliza {@code EnumType.STRING} para salvar o nome literal da enum
     * no banco de dados, protegendo a integridade dos dados caso a ordem dos elementos
     * no arquivo Java seja alterada no futuro.
     * </p>
     */
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    /**
     * Construtor padrão sem argumentos, exigido pela especificação da JPA.
     */ 
    public User() {
    }

    /**
     * Cria um novo usuário com os dados essenciais para o registro.
     *
     * @param name         O nome de exibição do usuário.
     * @param email        O e-mail válido para login.
     * @param passwordHash A senha já processada por uma função de hash.
     * @param role         O nível de acesso inicial concedido ao usuário.
     */
    public User(String name, String email, String passwordHash, UserRole role) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // --- Getters e Setters (omitidos do javadoc por serem autoexplicativos) ---
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
