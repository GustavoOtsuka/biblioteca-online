package br.com.fiap.biblioteca.controller;

import br.com.fiap.biblioteca.dto.LoanRequest;
import br.com.fiap.biblioteca.dto.LoanResponse;
import br.com.fiap.biblioteca.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanResponse> create(
            @Valid @RequestBody LoanRequest request) {

        LoanResponse response = loanService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}