package br.com.fiap.biblioteca.controller;

import br.com.fiap.biblioteca.dto.LibraryUserRequest;
import br.com.fiap.biblioteca.dto.LibraryUserResponse;
import br.com.fiap.biblioteca.service.LibraryUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(
        name = "Usuários",
        description = "Cadastro, consulta, atualização e exclusão de usuários da biblioteca"
)
public class LibraryUserController {

    private final LibraryUserService libraryUserService;

    public LibraryUserController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @Operation(summary = "Cadastrar um novo usuário")
    @PostMapping
    public ResponseEntity<LibraryUserResponse> create(
            @Valid @RequestBody LibraryUserRequest request) {

        LibraryUserResponse response = libraryUserService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "Consultar um usuário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<LibraryUserResponse> findById(@PathVariable Long id) {

        LibraryUserResponse response = libraryUserService.findById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar usuários",
            description = "Retorna os usuários cadastrados de forma paginada."
    )
    @GetMapping
    public ResponseEntity<Page<LibraryUserResponse>> findAll(Pageable pageable) {

        Page<LibraryUserResponse> response = libraryUserService.findAll(pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar os dados de um usuário")
    @PutMapping("/{id}")
    public ResponseEntity<LibraryUserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody LibraryUserRequest request) {

        LibraryUserResponse response = libraryUserService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Excluir um usuário",
            description = "Remove o usuário quando ele não possui histórico de empréstimos ou reservas."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        libraryUserService.delete(id);

        return ResponseEntity.noContent().build();
    }
}