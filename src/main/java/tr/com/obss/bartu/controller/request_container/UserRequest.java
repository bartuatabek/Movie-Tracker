package tr.com.obss.bartu.controller.request_container;

import tr.com.obss.bartu.model.Role;

public class UserRequest {

    private String username;

    private String password;

    private String email;

    private Role role;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
