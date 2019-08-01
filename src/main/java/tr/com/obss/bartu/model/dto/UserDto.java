package tr.com.obss.bartu.model.dto;

import tr.com.obss.bartu.model.User;

import java.util.Objects;

public class UserDto {

    private Long id;

    private String username;

    private String password;

    private String email;

    private boolean enabled;

    private RoleDto role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.role = new RoleDto(user.getRole());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return isEnabled() == userDto.isEnabled() &&
                Objects.equals(getId(), userDto.getId()) &&
                Objects.equals(getUsername(), userDto.getUsername()) &&
                Objects.equals(getPassword(), userDto.getPassword()) &&
                Objects.equals(getEmail(), userDto.getEmail()) &&
                Objects.equals(getRole(), userDto.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getEmail(), isEnabled(), getRole());
    }
}
