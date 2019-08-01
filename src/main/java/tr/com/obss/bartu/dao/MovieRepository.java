package tr.com.obss.bartu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.obss.bartu.model.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findMovieByImdbID(Long imdbID);
    List<Movie> findMoviesByName(String name);
    List<Movie> findMoviesByImdbRating(Float imdbRating);
    List<Movie> findMoviesByGenre(String genre);
}
