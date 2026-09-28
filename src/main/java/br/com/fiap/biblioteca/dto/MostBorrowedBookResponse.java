package br.com.fiap.biblioteca.dto;

public class MostBorrowedBookResponse {

    private Long bookId;
    private String title;
    private String author;
    private Long totalLoans;

    public MostBorrowedBookResponse(
            Long bookId,
            String title,
            String author,
            Long totalLoans) {

        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.totalLoans = totalLoans;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Long getTotalLoans() {
        return totalLoans;
    }
}