package br.com.fiap.biblioteca.repository;

import br.com.fiap.biblioteca.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByBookIdAndReturnedAtIsNull(Long bookId);

    boolean existsByUserIdAndReturnedAtIsNull(Long userId);

    Page<Loan> findByUserId(Long userId, Pageable pageable);

    Page<Loan> findByReturnedAtIsNull(Pageable pageable);


    @Query("""
        SELECT l.book.id,
               l.book.title,
               l.book.author,
               COUNT(l.id)
        FROM Loan l
        GROUP BY l.book.id, l.book.title, l.book.author
        ORDER BY COUNT(l.id) DESC
        """)
    List<Object[]> findMostBorrowedBooks(Pageable pageable);




    boolean existsByBookId(Long bookId);

    boolean existsByUserId(Long userId);




}