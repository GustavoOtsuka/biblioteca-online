package br.com.fiap.biblioteca.controller;

import br.com.fiap.biblioteca.dto.LibraryUserRequest;
import br.com.fiap.biblioteca.dto.LibraryUserResponse;
import br.com.fiap.biblioteca.service.LibraryUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/users")
public class LibraryUserController {

    private final LibraryUserService libraryUserService;

    public LibraryUserController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @PostMapping
    public ResponseEntity<LibraryUserResponse> create(
            @Valid @RequestBody LibraryUserRequest request) {

        LibraryUserResponse response = libraryUserService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryUserResponse> findById(@PathVariable Long id) {

        LibraryUserResponse response = libraryUserService.findById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<LibraryUserResponse>> findAll(Pageable pageable) {

        Page<LibraryUserResponse> response = libraryUserService.findAll(pageable);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibraryUserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody LibraryUserRequest request) {

        LibraryUserResponse response = libraryUserService.update(id, request);

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        libraryUserService.delete(id);

        return ResponseEntity.noContent().build();
    }

}