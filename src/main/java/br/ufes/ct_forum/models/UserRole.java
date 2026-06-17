package br.ufes.ct_forum.models;

/**
 * Define os níveis de acesso (papéis) disponíveis para os usuários do fórum.
 * <p>
 * Esta enumeração é utilizada para o controle de autorização dentro do sistema.
 * Dependendo do papel associado à conta, o usuário terá permissões diferentes
 * para realizar ações como criar tópicos, comentar ou moderar o conteúdo de terceiros.
 * </p>
 *
 */
public enum UserRole {
    /**
     * Administrador do sistema.
     * <p>
     * Possui privilégios elevados, i.e, tem permissões para
     * moderar o fórum, apagar qualquer publicação e contas 
     * de outros usuários.
     * </p>
     */
    ADMIN("admin"),

    /**
     * Usuário padrão do fórum.
     * <p>
     * Possui as permissões básicas de participação, restritas apenas a criar 
     * os próprios tópicos, responder a discussões e gerenciar o próprio conteúdo.
     * </p>
     */
    USER("user");

    /**
     * A representação em texto do papel.
     */
    private final String name;

    /**
     * Construtor interno da enumeração.
     *
     * @param name A string descritiva associada ao papel.
     */
    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
