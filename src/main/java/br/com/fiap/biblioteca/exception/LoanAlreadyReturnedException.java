package br.com.fiap.biblioteca.exception;

public class LoanAlreadyReturnedException extends RuntimeException {

    public LoanAlreadyReturnedException(Long loanId) {
        super("O empréstimo com o ID " + loanId + " já foi devolvido");
    }
}