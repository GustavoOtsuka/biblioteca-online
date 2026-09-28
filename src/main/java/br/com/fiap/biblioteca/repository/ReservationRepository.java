package br.com.fiap.biblioteca.repository;

import br.com.fiap.biblioteca.domain.Reservation;
import br.com.fiap.biblioteca.domain.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByUserIdAndBookIdAndStatus(
            Long userId,
            Long bookId,
            ReservationStatus status
    );

    Page<Reservation> findByUserId(
            Long userId,
            Pageable pageable
    );


    boolean existsByBookId(Long bookId);

    boolean existsByUserId(Long userId);
}