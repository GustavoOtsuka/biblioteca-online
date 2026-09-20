package br.com.fiap.biblioteca.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Livro não encontrado com o ID " + id);
    }
}