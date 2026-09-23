package br.com.fiap.biblioteca.exception;

public class ReservationNotActiveException extends RuntimeException {

    public ReservationNotActiveException(Long id) {
        super("A reserva com o ID " + id + " não está ativa");
    }
}