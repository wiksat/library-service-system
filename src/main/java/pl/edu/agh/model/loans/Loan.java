package pl.edu.agh.model.loans;

import jakarta.persistence.*;
import pl.edu.agh.model.books.Book;
import pl.edu.agh.model.users.Member;

import java.util.Date;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int loanId;

    @Temporal(TemporalType.DATE)
    private Date startLoanDate;
    @Temporal(TemporalType.DATE)
    private Date endLoanDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private Member member;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    private Book book;


    public Loan(Date startLoanDate, Date endLoanDate, Member member, Book book) {
        this.startLoanDate = startLoanDate;
        this.endLoanDate = endLoanDate;
        this.member = member;
        this.book = book;
    }

    public Loan() {
    }

    public void setBook(Book book) {
        this.book = book;
    }

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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }
}
