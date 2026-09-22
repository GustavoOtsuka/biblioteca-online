package br.com.fiap.biblioteca.exception;

public class BookUnavailableException extends RuntimeException {

    public BookUnavailableException(Long bookId) {
        super("O livro com o ID " + bookId + " não está disponível para empréstimo");
    }
}