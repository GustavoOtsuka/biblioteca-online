package br.com.fiap.biblioteca.controller;

import br.com.fiap.biblioteca.dto.LoanRequest;
import br.com.fiap.biblioteca.dto.LoanResponse;
import br.com.fiap.biblioteca.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @PutMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnLoan(@PathVariable Long id) {
        LoanResponse response = loanService.returnLoan(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> findById(@PathVariable Long id) {
        LoanResponse response = loanService.findById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<Page<LoanResponse>> findAll(Pageable pageable) {
        Page<LoanResponse> response = loanService.findAll(pageable);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<LoanResponse>> findByUserId(
            @PathVariable Long userId,
            Pageable pageable) {

        Page<LoanResponse> response = loanService.findByUserId(userId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<Page<LoanResponse>> findActiveLoans(Pageable pageable) {
        Page<LoanResponse> response = loanService.findActiveLoans(pageable);
        return ResponseEntity.ok(response);
    }


}