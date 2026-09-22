package br.com.fiap.biblioteca.service;

import br.com.fiap.biblioteca.domain.Book;
import br.com.fiap.biblioteca.domain.LibraryUser;
import br.com.fiap.biblioteca.domain.Loan;
import br.com.fiap.biblioteca.dto.LoanRequest;
import br.com.fiap.biblioteca.dto.LoanResponse;
import br.com.fiap.biblioteca.exception.BookNotFoundException;
import br.com.fiap.biblioteca.exception.BookUnavailableException;
import br.com.fiap.biblioteca.exception.LibraryUserNotFoundException;
import br.com.fiap.biblioteca.repository.BookRepository;
import br.com.fiap.biblioteca.repository.LibraryUserRepository;
import br.com.fiap.biblioteca.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class LoanService {

    private static final int LOAN_DAYS = 14;

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LibraryUserRepository libraryUserRepository;

    public LoanService(
            LoanRepository loanRepository,
            BookRepository bookRepository,
            LibraryUserRepository libraryUserRepository) {

        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.libraryUserRepository = libraryUserRepository;
    }

    @Transactional
    public LoanResponse create(LoanRequest request) {

        LibraryUser user = libraryUserRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new LibraryUserNotFoundException(request.getUserId()));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() ->
                        new BookNotFoundException(request.getBookId()));

        if (!book.isAvailable()
                || loanRepository.existsByBookIdAndReturnedAtIsNull(book.getId())) {

            throw new BookUnavailableException(book.getId());
        }

        OffsetDateTime borrowedAt = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime dueAt = borrowedAt.plusDays(LOAN_DAYS);

        Loan loan = new Loan(
                user,
                book,
                borrowedAt,
                dueAt
        );

        book.setAvailable(false);

        Loan savedLoan = loanRepository.save(loan);
        bookRepository.save(book);

        return new LoanResponse(savedLoan);
    }
}