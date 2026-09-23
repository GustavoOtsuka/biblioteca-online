package br.com.fiap.biblioteca.exception;

public class BookAlreadyAvailableException extends RuntimeException {

    public BookAlreadyAvailableException(Long bookId) {
        super("O livro com o ID " + bookId + " já está disponível para empréstimo");
    }
}