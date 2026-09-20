package br.com.fiap.biblioteca.dto;

import br.com.fiap.biblioteca.domain.LibraryUser;

public class LibraryUserResponse {

    private Long id;
    private String name;
    private String email;

    public LibraryUserResponse(LibraryUser libraryUser) {
        this.id = libraryUser.getId();
        this.name = libraryUser.getName();
        this.email = libraryUser.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}