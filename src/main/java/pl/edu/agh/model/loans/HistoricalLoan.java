package pl.edu.agh.model.loans;

import jakarta.persistence.*;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.users.Member;

import java.util.Date;

@Entity
public class HistoricalLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int loanId;
    private Date startLoanDate;
    private Date endLoanDate;
    private Date returnLoanDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "userId")
    private Member member;

    public HistoricalLoan(Date startLoanDate, Date endLoanDate, Date returnLoanDate, Member member, Book book) {
        this.startLoanDate = startLoanDate;
        this.endLoanDate = endLoanDate;
        this.returnLoanDate = returnLoanDate;
        this.member = member;
        this.book = book;
    }

    public HistoricalLoan() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id", referencedColumnName = "bookId")
    private Book book;

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public Date getStartLoanDate() {
        return startLoanDate;
    }

    public void setStartLoanDate(Date startLoanDate) {
        this.startLoanDate = startLoanDate;
    }

    public Date getEndLoanDate() {
        return endLoanDate;
    }

    public void setEndLoanDate(Date endLoanDate) {
        this.endLoanDate = endLoanDate;
    }

    public Date getReturnLoanDate() {
        return returnLoanDate;
    }

    public void setReturnLoanDate(Date returnLoanDate) {
        this.returnLoanDate = returnLoanDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
