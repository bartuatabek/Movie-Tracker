package tr.com.obss.bartu.model.dto;

import tr.com.obss.bartu.model.MovieList;

import java.util.Objects;

public class MovieListDto {

    private String listName;

    private Long userId;

    private Long movieId;

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public MovieListDto(MovieList list) {
        this.listName = list.getListName();
        this.userId = list.getUser().getId();
        this.movieId = list.getMovie().getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieListDto that = (MovieListDto) o;
        return Objects.equals(getListName(), that.getListName()) &&
                Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getMovieId(), that.getMovieId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getListName(), getUserId(), getMovieId());
    }
}
