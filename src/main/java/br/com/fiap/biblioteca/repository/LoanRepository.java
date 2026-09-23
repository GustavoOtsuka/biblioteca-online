package br.com.fiap.biblioteca.repository;

import br.com.fiap.biblioteca.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByBookIdAndReturnedAtIsNull(Long bookId);

    boolean existsByUserIdAndReturnedAtIsNull(Long userId);

    Page<Loan> findByUserId(Long userId, Pageable pageable);

    Page<Loan> findByReturnedAtIsNull(Pageable pageable);

}