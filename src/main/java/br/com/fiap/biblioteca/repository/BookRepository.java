package br.com.fiap.biblioteca.repository;

import br.com.fiap.biblioteca.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);
}