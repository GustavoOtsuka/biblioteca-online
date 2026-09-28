package br.com.fiap.biblioteca.controller;

import br.com.fiap.biblioteca.dto.LoanRequest;
import br.com.fiap.biblioteca.dto.LoanResponse;
import br.com.fiap.biblioteca.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loans")
@Tag(
        name = "Empréstimos",
        description = "Registro, consulta, devolução e acompanhamento de empréstimos de livros"
)
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(
            summary = "Registrar um novo empréstimo",
            description = "Associa um livro a um usuário, registra a data do empréstimo e calcula automaticamente a previsão de devolução."
    )
    @PostMapping
    public ResponseEntity<LoanResponse> create(
            @Valid @RequestBody LoanRequest request) {

        LoanResponse response = loanService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(
            summary = "Registrar a devolução de um livro",
            description = "Finaliza o empréstimo e torna o livro disponível novamente."
    )
    @PutMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnLoan(@PathVariable Long id) {

        LoanResponse response = loanService.returnLoan(id);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Consultar um empréstimo pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> findById(@PathVariable Long id) {

        LoanResponse response = loanService.findById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar empréstimos",
            description = "Retorna o histórico de empréstimos de forma paginada."
    )
    @GetMapping
    public ResponseEntity<Page<LoanResponse>> findAll(Pageable pageable) {

        Page<LoanResponse> response = loanService.findAll(pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Consultar empréstimos de um usuário",
            description = "Retorna de forma paginada o histórico de empréstimos associado ao usuário informado."
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<LoanResponse>> findByUserId(
            @PathVariable Long userId,
            Pageable pageable) {

        Page<LoanResponse> response = loanService.findByUserId(userId, pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar empréstimos ativos",
            description = "Retorna de forma paginada os empréstimos que ainda não foram devolvidos, incluindo a previsão de devolução."
    )
    @GetMapping("/active")
    public ResponseEntity<Page<LoanResponse>> findActiveLoans(Pageable pageable) {

        Page<LoanResponse> response = loanService.findActiveLoans(pageable);

        return ResponseEntity.ok(response);
    }
}