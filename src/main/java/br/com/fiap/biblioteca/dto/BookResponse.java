package br.com.fiap.biblioteca.dto;

import br.com.fiap.biblioteca.domain.Book;

public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private boolean available;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
        this.available = book.isAvailable();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return available;
    }
}