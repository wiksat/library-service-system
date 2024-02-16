package pl.edu.agh.model.users;

import jakarta.persistence.*;
import pl.edu.agh.model.loans.HistoricalLoan;
import pl.edu.agh.model.loans.Loan;

import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Member extends User {

    private Boolean newsLetter;
    private Boolean emailNotification;
    private Boolean banned;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "member")
    private List<Loan> loans;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
    private List<HistoricalLoan> historicalLoans;

    public Member(String firstName, String lastName, String email, Boolean newsLetter, Boolean emailNotification, String password) {
        super(firstName, lastName, email, password, AccountType.MEMBER);
        this.newsLetter = newsLetter;
        this.emailNotification = emailNotification;
        this.banned = false;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Member() {
    }

    public void setNewsLetter(Boolean newsLetter) {
        this.newsLetter = newsLetter;
    }

    public Boolean getNewsLetter() {
        return newsLetter;
    }

    public Boolean getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(Boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public void ban() {
        this.banned = true;
    }

    public void unban() {
        this.banned = false;
    }

    @Override
    public String toString() {
        return "Member{" +
                "userId=" + userId +
                " accountType=" + accountType +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", joinDate=" + joinDate +
                ", expirationDate=" + expirationDate +
                ", isActive=" + isActive +
                ", password='" + password + '\'' +
                "newsLetter=" + newsLetter +
                ", emailNotification=" + emailNotification +
                ", banned=" + banned +
                '}';
    }
}
