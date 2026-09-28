package br.com.fiap.biblioteca.service;

import br.com.fiap.biblioteca.domain.LibraryUser;
import br.com.fiap.biblioteca.dto.LibraryUserRequest;
import br.com.fiap.biblioteca.dto.LibraryUserResponse;
import br.com.fiap.biblioteca.exception.DuplicateEmailException;
import br.com.fiap.biblioteca.repository.LibraryUserRepository;
import org.springframework.stereotype.Service;

import br.com.fiap.biblioteca.exception.LibraryUserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.biblioteca.exception.ResourceHasHistoryException;
import br.com.fiap.biblioteca.repository.LoanRepository;
import br.com.fiap.biblioteca.repository.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;


import br.com.fiap.biblioteca.exception.ResourceHasHistoryException;
import br.com.fiap.biblioteca.repository.LoanRepository;
import br.com.fiap.biblioteca.repository.ReservationRepository;


@Service
public class LibraryUserService {

    private final LibraryUserRepository libraryUserRepository;

    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;




    public LibraryUserService(
            LibraryUserRepository libraryUserRepository,
            LoanRepository loanRepository,
            ReservationRepository reservationRepository) {

        this.libraryUserRepository = libraryUserRepository;
        this.loanRepository = loanRepository;
        this.reservationRepository = reservationRepository;
    }

    public LibraryUserResponse create(LibraryUserRequest request) {

        if (libraryUserRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        LibraryUser libraryUser = new LibraryUser(
                request.getName(),
                request.getEmail()
        );

        LibraryUser savedUser = libraryUserRepository.save(libraryUser);

        return new LibraryUserResponse(savedUser);
    }

    public LibraryUserResponse findById(Long id) {

        LibraryUser libraryUser = libraryUserRepository.findById(id)
                .orElseThrow(() -> new LibraryUserNotFoundException(id));

        return new LibraryUserResponse(libraryUser);
    }

    public Page<LibraryUserResponse> findAll(Pageable pageable) {

        return libraryUserRepository
                .findAll(pageable)
                .map(LibraryUserResponse::new);
    }


    public LibraryUserResponse update(Long id, LibraryUserRequest request) {

        LibraryUser libraryUser = libraryUserRepository.findById(id)
                .orElseThrow(() -> new LibraryUserNotFoundException(id));

        if (libraryUserRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new DuplicateEmailException(request.getEmail());
        }

        libraryUser.setName(request.getName());
        libraryUser.setEmail(request.getEmail());

        LibraryUser updatedUser = libraryUserRepository.save(libraryUser);

        return new LibraryUserResponse(updatedUser);
    }

    @Transactional
    public void delete(Long id) {

        LibraryUser user = libraryUserRepository.findById(id)
                .orElseThrow(() -> new LibraryUserNotFoundException(id));

        boolean hasLoans = loanRepository.existsByUserId(id);
        boolean hasReservations = reservationRepository.existsByUserId(id);

        if (hasLoans || hasReservations) {
            throw new ResourceHasHistoryException("o usuário", id);
        }

        libraryUserRepository.delete(user);
    }

}