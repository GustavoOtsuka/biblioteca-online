package br.com.fiap.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BookRequest {

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 200, message = "O título deve possuir no máximo 200 caracteres")
    private String title;

    @NotBlank(message = "O autor é obrigatório")
    @Size(max = 150, message = "O autor deve possuir no máximo 150 caracteres")
    private String author;

    @NotBlank(message = "O ISBN é obrigatório")
    @Pattern(
            regexp = "^(?:\\d{10}|\\d{13})$",
            message = "O ISBN deve possuir 10 ou 13 dígitos"
    )
    private String isbn;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}