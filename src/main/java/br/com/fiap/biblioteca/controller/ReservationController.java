package br.com.fiap.biblioteca.controller;

import br.com.fiap.biblioteca.dto.ReservationRequest;
import br.com.fiap.biblioteca.dto.ReservationResponse;
import br.com.fiap.biblioteca.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@Tag(
        name = "Reservas",
        description = "Registro, consulta, acompanhamento e cancelamento de reservas de livros"
)
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(
            summary = "Registrar uma nova reserva",
            description = "Registra a reserva de um livro indisponível para o usuário informado."
    )
    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @Valid @RequestBody ReservationRequest request) {

        ReservationResponse response = reservationService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "Consultar uma reserva pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> findById(
            @PathVariable Long id) {

        ReservationResponse response = reservationService.findById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar reservas",
            description = "Retorna as reservas registradas de forma paginada."
    )
    @GetMapping
    public ResponseEntity<Page<ReservationResponse>> findAll(
            Pageable pageable) {

        Page<ReservationResponse> response =
                reservationService.findAll(pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Cancelar uma reserva",
            description = "Cancela uma reserva ativa, preservando seu registro no histórico."
    )
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancel(
            @PathVariable Long id) {

        ReservationResponse response = reservationService.cancel(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Consultar reservas de um usuário",
            description = "Retorna de forma paginada o histórico de reservas associado ao usuário informado."
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ReservationResponse>> findByUserId(
            @PathVariable Long userId,
            Pageable pageable) {

        Page<ReservationResponse> response =
                reservationService.findByUserId(userId, pageable);

        return ResponseEntity.ok(response);
    }
}