package pl.edu.agh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.response.IBookResponse;
import pl.edu.agh.model.users.User;
import pl.edu.agh.repository.loans.HistoricalLoanRepository;
import pl.edu.agh.repository.loans.LoanRepository;

import java.util.List;

@Service
public class UserStatsService {
    private final LoanRepository loanRepository;
    private final HistoricalLoanRepository historicalLoanRepository;


    @Autowired
    public UserStatsService(LoanRepository loanRepository, HistoricalLoanRepository historicalLoanRepository) {
        this.loanRepository = loanRepository;
        this.historicalLoanRepository = historicalLoanRepository;
    }

    public List<IBookResponse> findTheMostFrequentlyCurrentlyBorrowedBooks(User user) {
        return loanRepository.findTheMostFrequentlyBorrowedBooksByUser(user);
    }

    public List<IBookResponse> findTheMostFrequentlyHistoricalBorrowedBooks(User user) {
        return historicalLoanRepository.findTheMostFrequentlyBorrowedBooksByUser(user);
    }

    public Integer getAmountCurrentlyBorrowedBooks(User user) {
        return loanRepository.getAmountOfCurrentlyBorrowedBooksByUser(user);
    }

    public Integer getAmountHistoricallyBorrowedBooks(User user) {
        return historicalLoanRepository.getAmountHistoricallyBorrowedBooksByUser(user);
    }
}
