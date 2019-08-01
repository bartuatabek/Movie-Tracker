package tr.com.obss.bartu.model.dto;

import tr.com.obss.bartu.model.Role;

import java.util.Objects;

public class RoleDto {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleDto(Role role) {
        this.name = role.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto roleDto = (RoleDto) o;
        return Objects.equals(getName(), roleDto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
