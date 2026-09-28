package br.com.fiap.biblioteca.exception;

public class ResourceHasHistoryException extends RuntimeException {

    public ResourceHasHistoryException(String resourceType, Long id) {
        super(
                "Não é possível excluir "
                        + resourceType
                        + " com o ID "
                        + id
                        + " porque possui histórico de empréstimos ou reservas"
        );
    }
}