package pl.edu.agh.model.users;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;


@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Admin extends User {

    private int room;
    private String phoneNumber;

    public Admin(String firstName, String lastName, String email, String phoneNumber, int room, String password) {
        super(firstName, lastName, email, password, AccountType.ADMIN);
        this.phoneNumber = phoneNumber;
        this.room = room;
    }

    public Admin() {
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
