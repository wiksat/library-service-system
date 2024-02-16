package pl.edu.agh.model.books;

import jakarta.persistence.*;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.loans.Loan;

import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;

    @Enumerated(EnumType.STRING)
    private CoverType coverType;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "book")
    private Loan loan;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    private List<HistoricalLoan> historicalLoans;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="title_id", referencedColumnName = "titleId")
    private Title title;

    public Book(CoverType coverType, Title title) {
        this.coverType = coverType;
        this.title = title;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public CoverType getCoverType() {
        return coverType;
    }

    public void setCoverType(CoverType coverType) {
        this.coverType = coverType;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public List<HistoricalLoan> getHistoricalLoans() {
        return historicalLoans;
    }

    public void setHistoricalLoans(List<HistoricalLoan> historicalLoans) {
        this.historicalLoans = historicalLoans;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Book() {

    }
}
