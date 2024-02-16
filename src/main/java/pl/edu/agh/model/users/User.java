package pl.edu.agh.model.users;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int userId;
    protected String firstName;

    public void setEmail(String email) {
        this.email = email;
    }

    protected String lastName;
    @Column(unique = true)
    protected String email;
    protected Date joinDate;
    protected Date expirationDate;

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    protected boolean isActive;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    protected String password;
    protected AccountType accountType;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, AccountType accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.joinDate = new Date();
        this.expirationDate = null;
        this.isActive = true;
        this.password = password;
        this.accountType = accountType;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getPassword() {
        return password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "User{" +
                "accountType=" + accountType +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", joinDate=" + joinDate +
                ", expirationDate=" + expirationDate +
                ", isActive=" + isActive +
                ", password='" + password + '\'' +
                '}';
    }
}
