package br.com.fiap.biblioteca.dto;

import br.com.fiap.biblioteca.domain.Reservation;
import br.com.fiap.biblioteca.domain.ReservationStatus;

import java.time.OffsetDateTime;

public class ReservationResponse {

    private Long id;

    private Long userId;
    private String userName;

    private Long bookId;
    private String bookTitle;

    private OffsetDateTime reservedAt;

    private ReservationStatus status;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();

        this.userId = reservation.getUser().getId();
        this.userName = reservation.getUser().getName();

        this.bookId = reservation.getBook().getId();
        this.bookTitle = reservation.getBook().getTitle();

        this.reservedAt = reservation.getReservedAt();
        this.status = reservation.getStatus();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public OffsetDateTime getReservedAt() {
        return reservedAt;
    }

    public ReservationStatus getStatus() {
        return status;
    }
}