package br.com.fiap.biblioteca.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private LibraryUser user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private OffsetDateTime reservedAt;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public Reservation() {
    }

    public Reservation(
            LibraryUser user,
            Book book,
            OffsetDateTime reservedAt,
            ReservationStatus status) {
        this.user = user;
        this.book = book;
        this.reservedAt = reservedAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public LibraryUser getUser() {
        return user;
    }

    public void setUser(LibraryUser user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public OffsetDateTime getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(OffsetDateTime reservedAt) {
        this.reservedAt = reservedAt;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}