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


}