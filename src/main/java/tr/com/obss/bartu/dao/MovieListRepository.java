package tr.com.obss.bartu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.obss.bartu.model.MovieList;

import java.util.List;

public interface MovieListRepository extends JpaRepository<MovieList, Long> {

    List<MovieList> findMovieListsByListNameAndUserId(String listName, Long userId);
}
