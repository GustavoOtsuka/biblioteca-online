package br.com.fiap.biblioteca.service;

import br.com.fiap.biblioteca.domain.Book;
import br.com.fiap.biblioteca.dto.BookRequest;
import br.com.fiap.biblioteca.dto.BookResponse;
import br.com.fiap.biblioteca.repository.BookRepository;
import org.springframework.stereotype.Service;
import br.com.fiap.biblioteca.exception.DuplicateIsbnException;
import br.com.fiap.biblioteca.exception.BookNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse create(BookRequest request) {

        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateIsbnException(request.getIsbn());
        }

        Book book = new Book(
                request.getTitle(),
                request.getAuthor(),
                request.getIsbn()
        );

        Book savedBook = bookRepository.save(book);

        return new BookResponse(savedBook);
    }

    public BookResponse findById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        return new BookResponse(book);
    }

    public Page<BookResponse> findAll(Pageable pageable) {

        return bookRepository
                .findAll(pageable)
                .map(BookResponse::new);
    }

    public BookResponse update(Long id, BookRequest request) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        if (bookRepository.existsByIsbnAndIdNot(request.getIsbn(), id)) {
            throw new DuplicateIsbnException(request.getIsbn());
        }

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());

        Book updatedBook = bookRepository.save(book);

        return new BookResponse(updatedBook);
    }

    public void delete(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookRepository.delete(book);
    }

    public Page<BookResponse> search(
            String title,
            String author,
            String isbn,
            Boolean available,
            Pageable pageable) {

        Specification<Book> specification = Specification.unrestricted();

        if (title != null && !title.isBlank()) {
            String normalizedTitle = title.trim().toLowerCase();

            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get("title")),
                                    "%" + normalizedTitle + "%"
                            )
            );
        }

        if (author != null && !author.isBlank()) {
            String normalizedAuthor = author.trim().toLowerCase();

            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get("author")),
                                    "%" + normalizedAuthor + "%"
                            )
            );
        }

        if (isbn != null && !isbn.isBlank()) {
            String normalizedIsbn = isbn.trim();

            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(
                                    root.get("isbn"),
                                    normalizedIsbn
                            )
            );
        }

        if (available != null) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(
                                    root.get("available"),
                                    available
                            )
            );
        }

        return bookRepository.findAll(specification, pageable)
                .map(BookResponse::new);
    }




}