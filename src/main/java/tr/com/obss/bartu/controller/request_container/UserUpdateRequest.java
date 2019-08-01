package tr.com.obss.bartu.controller.request_container;

import tr.com.obss.bartu.model.Role;

public class UserUpdateRequest {

    private Long id;

    private String username;

    private String password;

    private String confirm_password;

    private String email;

    private boolean enabled;

    private Role role;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Role getRole() {
            return role;
        }

}
