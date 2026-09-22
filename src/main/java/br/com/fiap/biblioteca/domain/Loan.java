package br.com.fiap.biblioteca.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "loans")
public class Loan {

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
    private OffsetDateTime borrowedAt;

    @Column(nullable = false)
    private OffsetDateTime dueAt;

    private OffsetDateTime returnedAt;

    public Loan() {
    }

    public Loan(
            LibraryUser user,
            Book book,
            OffsetDateTime borrowedAt,
            OffsetDateTime dueAt) {

        this.user = user;
        this.book = book;
        this.borrowedAt = borrowedAt;
        this.dueAt = dueAt;
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

    public OffsetDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public void setBorrowedAt(OffsetDateTime borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    public OffsetDateTime getDueAt() {
        return dueAt;
    }

    public void setDueAt(OffsetDateTime dueAt) {
        this.dueAt = dueAt;
    }

    public OffsetDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(OffsetDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }
}