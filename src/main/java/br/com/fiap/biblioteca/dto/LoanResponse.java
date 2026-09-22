package br.com.fiap.biblioteca.dto;

import br.com.fiap.biblioteca.domain.Loan;

import java.time.OffsetDateTime;

public class LoanResponse {

    private Long id;

    private Long userId;
    private String userName;

    private Long bookId;
    private String bookTitle;

    private OffsetDateTime borrowedAt;
    private OffsetDateTime dueAt;
    private OffsetDateTime returnedAt;

    public LoanResponse(Loan loan) {
        this.id = loan.getId();

        this.userId = loan.getUser().getId();
        this.userName = loan.getUser().getName();

        this.bookId = loan.getBook().getId();
        this.bookTitle = loan.getBook().getTitle();

        this.borrowedAt = loan.getBorrowedAt();
        this.dueAt = loan.getDueAt();
        this.returnedAt = loan.getReturnedAt();
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

    public OffsetDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public OffsetDateTime getDueAt() {
        return dueAt;
    }

    public OffsetDateTime getReturnedAt() {
        return returnedAt;
    }
}