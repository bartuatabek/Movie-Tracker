package tr.com.obss.bartu.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "directors")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Date birthDate;

    private String birthPlace;

    @ManyToMany(mappedBy = "directors", fetch = FetchType.LAZY)
    private Set<Movie> movies = new HashSet<>();

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

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public Director(Long id, @NotNull String name, @NotNull Date birthDate, @NotNull String birthPlace) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
    }

    public Director(@NotNull String name, @NotNull Date birthDate, @NotNull String birthPlace) {
        this.name = name;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
    }

    public Director() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return Objects.equals(getId(), director.getId()) &&
                Objects.equals(getName(), director.getName()) &&
                Objects.equals(getBirthDate(), director.getBirthDate()) &&
                Objects.equals(getBirthPlace(), director.getBirthPlace()) &&
                Objects.equals(getMovies(), director.getMovies());
    }

}
