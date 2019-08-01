package tr.com.obss.bartu.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "movieLists")
public class MovieList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String listName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieList(String listName, User user, Movie movie) {
        this.listName = listName;
        this.user = user;
        this.movie = movie;
    }

    public MovieList() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieList movieList = (MovieList) o;
        return Objects.equals(getId(), movieList.getId()) &&
                Objects.equals(getListName(), movieList.getListName()) &&
                Objects.equals(getUser(), movieList.getUser()) &&
                Objects.equals(getMovie(), movieList.getMovie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getListName(), user, getMovie());
    }
}
