package br.com.fiap.biblioteca.dto;

import jakarta.validation.constraints.NotNull;

public class LoanRequest {

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long userId;

    @NotNull(message = "O ID do livro é obrigatório")
    private Long bookId;

    public LoanRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}