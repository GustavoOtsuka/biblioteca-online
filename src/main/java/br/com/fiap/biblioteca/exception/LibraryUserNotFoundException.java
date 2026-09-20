package br.com.fiap.biblioteca.exception;

public class LibraryUserNotFoundException extends RuntimeException {

    public LibraryUserNotFoundException(Long id) {
        super("Usuário não encontrado com o ID " + id);
    }
}