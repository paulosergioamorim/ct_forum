package br.ufes.ct_forum.models;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
