package br.com.fiap.biblioteca.service;

import br.com.fiap.biblioteca.domain.Book;
import br.com.fiap.biblioteca.domain.LibraryUser;
import br.com.fiap.biblioteca.domain.Reservation;
import br.com.fiap.biblioteca.domain.ReservationStatus;
import br.com.fiap.biblioteca.dto.ReservationRequest;
import br.com.fiap.biblioteca.dto.ReservationResponse;
import br.com.fiap.biblioteca.exception.BookAlreadyAvailableException;
import br.com.fiap.biblioteca.exception.BookNotFoundException;
import br.com.fiap.biblioteca.exception.DuplicateReservationException;
import br.com.fiap.biblioteca.exception.LibraryUserNotFoundException;
import br.com.fiap.biblioteca.repository.BookRepository;
import br.com.fiap.biblioteca.repository.LibraryUserRepository;
import br.com.fiap.biblioteca.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import br.com.fiap.biblioteca.exception.ReservationNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.biblioteca.exception.ReservationNotActiveException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final LibraryUserRepository libraryUserRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            BookRepository bookRepository,
            LibraryUserRepository libraryUserRepository) {

        this.reservationRepository = reservationRepository;
        this.bookRepository = bookRepository;
        this.libraryUserRepository = libraryUserRepository;
    }

    @Transactional
    public ReservationResponse create(ReservationRequest request) {

        LibraryUser user = libraryUserRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new LibraryUserNotFoundException(request.getUserId()));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() ->
                        new BookNotFoundException(request.getBookId()));

        if (book.isAvailable()) {
            throw new BookAlreadyAvailableException(book.getId());
        }

        boolean alreadyReserved =
                reservationRepository.existsByUserIdAndBookIdAndStatus(
                        user.getId(),
                        book.getId(),
                        ReservationStatus.ACTIVE
                );

        if (alreadyReserved) {
            throw new DuplicateReservationException(
                    user.getId(),
                    book.getId()
            );
        }

        Reservation reservation = new Reservation(
                user,
                book,
                OffsetDateTime.now(ZoneOffset.UTC),
                ReservationStatus.ACTIVE
        );

        Reservation savedReservation =
                reservationRepository.save(reservation);

        return new ReservationResponse(savedReservation);
    }

    @Transactional(readOnly = true)
    public ReservationResponse findById(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        return new ReservationResponse(reservation);
    }


    @Transactional(readOnly = true)
    public Page<ReservationResponse> findAll(Pageable pageable) {

        return reservationRepository.findAll(pageable)
                .map(ReservationResponse::new);
    }


    @Transactional
    public ReservationResponse cancel(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new ReservationNotActiveException(id);
        }

        reservation.setStatus(ReservationStatus.CANCELLED);

        Reservation savedReservation =
                reservationRepository.save(reservation);

        return new ReservationResponse(savedReservation);
    }

}