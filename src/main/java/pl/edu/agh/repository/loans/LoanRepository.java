package pl.edu.agh.repository.loans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.model.response.IBookResponse;
import org.springframework.stereotype.Repository;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.books.CoverType;
import pl.edu.agh.model.extra.LoanDetails;
import pl.edu.agh.model.loans.Loan;
import pl.edu.agh.model.users.User;

import java.util.Date;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    @Query("SELECT l.member.email, l.book.title.title FROM Loan l WHERE l.endLoanDate = :endDate AND l.member.emailNotification = true")
    List<String> findEmailsAndBooksForEmailNotification(@Param("endDate") Date endDate);

    @Query("SELECT l.member.email FROM Loan l WHERE l.member.newsLetter = true")
    List<String> findEmailsForNewsLetter();

    @Query("SELECT l.book.title.title as title, count(*) as amount FROM Loan l GROUP BY l.book.title ORDER BY count(*) DESC")
    List<IBookResponse> findTheMostFrequentlyBorrowedBooks();

    @Query("SELECT l.book.title.title as title, count(*) as amount FROM Loan l WHERE l.member=:user GROUP BY l.book.title ORDER BY count(*) DESC")
    List<IBookResponse> findTheMostFrequentlyBorrowedBooksByUser(@Param("user") User user);

    @Query("SELECT COUNT(l) FROM Loan l")
    Integer getAmountOfCurrentlyBorrowedBooks();

    @Query("SELECT COUNT(l) FROM Loan l where l.member=:user")
    Integer getAmountOfCurrentlyBorrowedBooksByUser(@Param ("user") User user);
    @Query("SELECT new pl.edu.agh.model.extra.LoanDetails(l.book.title, l.startLoanDate, l.endLoanDate, l.loanId) FROM Loan l WHERE l.member.userId = :userId")
    List<LoanDetails> findAllLoansByUserId(@Param("userId") long userId);

    @Modifying
    @Query("DELETE FROM Loan l WHERE l.loanId = :loanId")
    void deleteByLoanId(@Param("loanId") int loanId);
}
