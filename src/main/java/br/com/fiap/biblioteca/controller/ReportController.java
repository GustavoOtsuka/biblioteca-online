package br.com.fiap.biblioteca.controller;

import br.com.fiap.biblioteca.dto.MostBorrowedBookResponse;
import br.com.fiap.biblioteca.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import br.com.fiap.biblioteca.dto.LoanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/most-borrowed-books")
    public ResponseEntity<List<MostBorrowedBookResponse>> findMostBorrowedBooks() {

        List<MostBorrowedBookResponse> response =
                reportService.findMostBorrowedBooks();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active-loans")
    public ResponseEntity<Page<LoanResponse>> findActiveLoans(
            Pageable pageable) {

        Page<LoanResponse> response =
                reportService.findActiveLoans(pageable);

        return ResponseEntity.ok(response);
    }
}