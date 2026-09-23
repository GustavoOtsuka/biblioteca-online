package br.com.fiap.biblioteca.controller;

import br.com.fiap.biblioteca.dto.ReservationRequest;
import br.com.fiap.biblioteca.dto.ReservationResponse;
import br.com.fiap.biblioteca.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(
            @Valid @RequestBody ReservationRequest request) {

        ReservationResponse response = reservationService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> findById(
            @PathVariable Long id) {

        ReservationResponse response = reservationService.findById(id);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<Page<ReservationResponse>> findAll(
            Pageable pageable) {

        Page<ReservationResponse> response =
                reservationService.findAll(pageable);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancel(
            @PathVariable Long id) {

        ReservationResponse response = reservationService.cancel(id);

        return ResponseEntity.ok(response);
    }
}