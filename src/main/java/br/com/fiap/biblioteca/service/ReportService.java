package br.com.fiap.biblioteca.service;

import br.com.fiap.biblioteca.dto.MostBorrowedBookResponse;
import br.com.fiap.biblioteca.repository.LoanRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import br.com.fiap.biblioteca.dto.LoanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ReportService {

    private final LoanRepository loanRepository;

    public ReportService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Transactional(readOnly = true)
    public List<MostBorrowedBookResponse> findMostBorrowedBooks() {

        List<Object[]> results =
                loanRepository.findMostBorrowedBooks(
                        PageRequest.of(0, 20)
                );

        return results.stream()
                .map(result -> new MostBorrowedBookResponse(
                        (Long) result[0],
                        (String) result[1],
                        (String) result[2],
                        (Long) result[3]
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<LoanResponse> findActiveLoans(Pageable pageable) {

        return loanRepository.findByReturnedAtIsNull(pageable)
                .map(LoanResponse::new);
    }
}