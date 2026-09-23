package br.com.fiap.biblioteca.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(Long id) {
        super("Reserva não encontrada com o ID " + id);
    }
}