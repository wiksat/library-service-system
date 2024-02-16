package pl.edu.agh.session;

import pl.edu.agh.enums.UserRoleEnum;
import pl.edu.agh.model.users.User;

public class UserSession {
    private static UserSession instance;
    private User user;
    private UserRoleEnum role;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return this.user;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
    // jesli jest potrzeba to mozna dodac jakies metody juz na userze, ale trzeba pamietac ze user moze byc null
}
