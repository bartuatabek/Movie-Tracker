package tr.com.obss.bartu.model.dto;

import tr.com.obss.bartu.model.Director;
import tr.com.obss.bartu.model.Movie;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class MovieDto {

    private Long id;

    private Long imdbID;

    private String name;

    private Set<DirectorDto> directors;

    private Date release_date;

    private Float imdbRating;

    private Integer runtime;

    private String genre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImdbID() {
        return imdbID;
    }

    public void setImdbID(Long imdbID) {
        this.imdbID = imdbID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DirectorDto> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<DirectorDto> directors) {
        this.directors = directors;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public Float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.imdbID = movie.getImdbID();
        this.name = movie.getName();

        for (Director director:movie.getDirectors()) {
            this.directors.add(new DirectorDto(director));
        }

        this.release_date = movie.getRelease_date();
        this.imdbRating = movie.getImdbRating();
        this.runtime = movie.getRuntime();
        this.genre = movie.getGenre();
    }

    public MovieDto(Long imdbID, String name, Set<DirectorDto> directors, Date release_date, Float imdbRating, Integer runtime, String genre) {
        this.imdbID = imdbID;
        this.name = name;
        this.directors = directors;
        this.release_date = release_date;
        this.imdbRating = imdbRating;
        this.runtime = runtime;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDto movieDto = (MovieDto) o;
        return Objects.equals(getId(), movieDto.getId()) &&
                Objects.equals(getImdbID(), movieDto.getImdbID()) &&
                Objects.equals(getName(), movieDto.getName()) &&
                Objects.equals(getDirectors(), movieDto.getDirectors()) &&
                Objects.equals(getRelease_date(), movieDto.getRelease_date()) &&
                Objects.equals(getImdbRating(), movieDto.getImdbRating()) &&
                Objects.equals(getRuntime(), movieDto.getRuntime()) &&
                Objects.equals(getGenre(), movieDto.getGenre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getImdbID(), getName(), getDirectors(), getRelease_date(), getImdbRating(), getRuntime(), getGenre());
    }
}
