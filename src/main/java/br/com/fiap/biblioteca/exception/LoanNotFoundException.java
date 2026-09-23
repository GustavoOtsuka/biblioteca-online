package br.com.fiap.biblioteca.exception;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(Long id) {
        super("Empréstimo não encontrado com o ID " + id);
    }
}