package br.com.fiap.biblioteca.controller;

import br.com.fiap.biblioteca.dto.LoanResponse;
import br.com.fiap.biblioteca.dto.MostBorrowedBookResponse;
import br.com.fiap.biblioteca.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@Tag(
        name = "Relatórios",
        description = "Relatórios de utilização da biblioteca, ranking de livros e empréstimos ativos"
)
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(
            summary = "Listar os 20 livros mais emprestados",
            description = "Retorna o ranking dos 20 livros com maior quantidade de empréstimos registrados na biblioteca."
    )
    @GetMapping("/most-borrowed-books")
    public ResponseEntity<List<MostBorrowedBookResponse>> findMostBorrowedBooks() {

        List<MostBorrowedBookResponse> response =
                reportService.findMostBorrowedBooks();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar os empréstimos atualmente ativos",
            description = "Retorna de forma paginada os livros atualmente emprestados, incluindo a previsão de devolução."
    )
    @GetMapping("/active-loans")
    public ResponseEntity<Page<LoanResponse>> findActiveLoans(
            Pageable pageable) {

        Page<LoanResponse> response =
                reportService.findActiveLoans(pageable);

        return ResponseEntity.ok(response);
    }
}