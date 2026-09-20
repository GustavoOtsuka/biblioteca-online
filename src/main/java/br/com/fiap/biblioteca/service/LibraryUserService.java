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


@Service
public class LibraryUserService {

    private final LibraryUserRepository libraryUserRepository;

    public LibraryUserService(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
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

    public void delete(Long id) {

        LibraryUser libraryUser = libraryUserRepository.findById(id)
                .orElseThrow(() -> new LibraryUserNotFoundException(id));

        libraryUserRepository.delete(libraryUser);
    }

}