package br.com.fiap.biblioteca.controller;

import br.com.fiap.biblioteca.dto.BookRequest;
import br.com.fiap.biblioteca.dto.BookResponse;
import br.com.fiap.biblioteca.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@Tag(
        name = "Livros",
        description = "Cadastro, consulta, atualização, exclusão e pesquisa de livros"
)
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Cadastrar um novo livro")
    @PostMapping
    public ResponseEntity<BookResponse> create(
            @Valid @RequestBody BookRequest request) {

        BookResponse response = bookService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "Consultar um livro pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable Long id) {

        BookResponse response = bookService.findById(id);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Pesquisar livros",
            description = "Retorna livros de forma paginada e permite filtrar por título, autor, ISBN e disponibilidade."
    )
    @GetMapping
    public ResponseEntity<Page<BookResponse>> findAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Boolean available,
            Pageable pageable) {

        Page<BookResponse> response = bookService.search(
                title,
                author,
                isbn,
                available,
                pageable
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar os dados de um livro")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request) {

        BookResponse response = bookService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Excluir um livro",
            description = "Remove o livro quando ele não possui histórico de empréstimos ou reservas."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        bookService.delete(id);

        return ResponseEntity.noContent().build();
    }
}