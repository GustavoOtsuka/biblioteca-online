package br.com.fiap.biblioteca.repository;

import br.com.fiap.biblioteca.domain.Reservation;
import br.com.fiap.biblioteca.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByUserIdAndBookIdAndStatus(
            Long userId,
            Long bookId,
            ReservationStatus status
    );
}