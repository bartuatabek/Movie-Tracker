package tr.com.obss.bartu.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long imdbID;

    @NotNull
    private String name;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_director",
            joinColumns = { @JoinColumn(name = "fk_movie")},
            inverseJoinColumns = {@JoinColumn(name = "fk_director")})
    private Set<Director> directors = new HashSet<>();

    @NotNull
    private Date release_date;

    @NotNull
    private Float imdbRating;

    @NotNull
    private Integer runtime;

    @NotNull
    @ElementCollection
    private List<String> genre = new ArrayList<>();

    @NotNull
    private String posterUrl;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie", fetch = FetchType.LAZY)
    private Set<MovieList> movieLists = new HashSet<>();

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

    public Set<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    public void addDirector(Director director) {
        this.directors.add(director);
        director.getMovies().add(this);
    }

    public void removeDirector(Director director) {
        this.directors.remove(director);
        director.getMovies().remove(this);
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

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public Set<MovieList> getMovieLists() {
        return movieLists;
    }

    public void setMovieLists(Set<MovieList> movieLists) {
        this.movieLists = movieLists;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }


    public Movie(Long imdbID, @NotNull String name, @NotNull Date release_date, @NotNull Float imdbRating, @NotNull Integer runtime, @NotNull List<String> genre, @NonNull String posterUrl) {
        this.imdbID = imdbID;
        this.name = name;
        this.release_date = release_date;
        this.imdbRating = imdbRating;
        this.runtime = runtime;
        this.genre = genre;
        this.posterUrl = posterUrl;

    }

    public Movie(@NotNull String name, @NotNull Date release_date, @NotNull Float imdbRating, @NotNull Integer runtime, @NotNull List<String> genre) {
        this.name = name;
        this.release_date = release_date;
        this.imdbRating = imdbRating;
        this.runtime = runtime;
        this.genre = genre;
    }

    public Movie(@NotNull String name, Director director, @NotNull Date release_date, @NotNull Float imdbRating, @NotNull Integer runtime, @NotNull List<String> genre, @NonNull String posterUrl) {
        this.name = name;
        addDirector(director);
        this.release_date = release_date;
        this.imdbRating = imdbRating;
        this.runtime = runtime;
        this.genre = genre;
        this.posterUrl = posterUrl;
    }

    public Movie() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(getId(), movie.getId()) &&
                Objects.equals(getImdbID(), movie.getImdbID()) &&
                Objects.equals(getName(), movie.getName()) &&
                Objects.equals(getDirectors(), movie.getDirectors()) &&
                Objects.equals(getRelease_date(), movie.getRelease_date()) &&
                Objects.equals(getImdbRating(), movie.getImdbRating()) &&
                Objects.equals(getRuntime(), movie.getRuntime()) &&
                Objects.equals(getGenre(), movie.getGenre()) &&
                Objects.equals(getMovieLists(), movie.getMovieLists()) &&
                Objects.equals(getPosterUrl(), movie.getPosterUrl());
    }

}
