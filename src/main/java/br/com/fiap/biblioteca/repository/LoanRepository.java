package br.com.fiap.biblioteca.repository;

import br.com.fiap.biblioteca.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByBookIdAndReturnedAtIsNull(Long bookId);

    boolean existsByUserIdAndReturnedAtIsNull(Long userId);
}