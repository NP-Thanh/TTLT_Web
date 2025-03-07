package vn.edu.hcmuaf.fit.web.model;

import java.io.Serializable;

public class UserAdmin implements Serializable {
    private User user;
    private Role role;

    public UserAdmin() {
    }

    public UserAdmin(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
