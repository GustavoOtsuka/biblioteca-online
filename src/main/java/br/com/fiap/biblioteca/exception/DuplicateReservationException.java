package br.com.fiap.biblioteca.exception;

public class DuplicateReservationException extends RuntimeException {

    public DuplicateReservationException(Long userId, Long bookId) {
        super(
                "O usuário com o ID " + userId
                        + " já possui uma reserva ativa para o livro com o ID "
                        + bookId
        );
    }
}