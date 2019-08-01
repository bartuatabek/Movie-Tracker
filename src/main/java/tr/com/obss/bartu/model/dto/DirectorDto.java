package tr.com.obss.bartu.model.dto;

import tr.com.obss.bartu.model.Director;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class DirectorDto {

    private Long id;

    private String name;

    private Date birthDate;

    private String birthPlace;

    private Set<MovieDto> movies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Set<MovieDto> getMovies() { return movies; }

    public void setMovies(Set<MovieDto> movies) { this.movies = movies; }

    public DirectorDto() { }

    public DirectorDto(Director director) {
        this.id = director.getId();
        this.name = director.getName();
        this.birthDate = director.getBirthDate();
        this.birthPlace = director.getBirthPlace();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectorDto that = (DirectorDto) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getBirthDate(), that.getBirthDate()) &&
                Objects.equals(getBirthPlace(), that.getBirthPlace());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getBirthDate(), getBirthPlace());
    }
}
