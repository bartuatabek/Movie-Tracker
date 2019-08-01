package tr.com.obss.bartu.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tr.com.obss.bartu.controller.response.Response;
import tr.com.obss.bartu.dao.MovieRepository;
import tr.com.obss.bartu.model.Movie;
import tr.com.obss.bartu.controller.request_container.MovieRequest;
import tr.com.obss.bartu.model.dto.MovieDto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public boolean addMovie(MovieRequest request) {
        if (StringUtils.isEmpty(request.getName()) || request.getDirector() == null || request.getRelease_date() == null ||
                request.getImdb_rating() == null || request.getRuntime() == null || StringUtils.isEmpty(request.getGenre()))
            return false;

        List<Movie> existingMovies = repository.findMoviesByName(request.getName());

        if (!existingMovies.isEmpty()) {
            return false;
        } else {
            Movie newMovie = new Movie(request.getName(), request.getDirector(), request.getRelease_date(),
                                       request.getImdb_rating(), request.getRuntime(), request.getGenre());
            repository.save(newMovie);
            return true;
        }
    }

    public ResponseEntity<?> updateMovie(Long id, MovieRequest request) {
        if (StringUtils.isEmpty(request.getName()) || request.getDirector() == null || request.getRelease_date() == null ||
            request.getImdb_rating() == null || request.getRuntime() == null || StringUtils.isEmpty(request.getGenre())) {
            return new ResponseEntity<>(new Response("Missing fields updating move."), HttpStatus.BAD_REQUEST);
        }

        Movie existingMovie =  repository.getOne(id);

        if (existingMovie != null) {
            existingMovie.setName(request.getName());
            existingMovie.addDirector(request.getDirector());
            existingMovie.setRelease_date(request.getRelease_date());
            existingMovie.setImdbRating(request.getImdb_rating());
            existingMovie.setRuntime(request.getRuntime());
            existingMovie.setGenre(request.getGenre());
            repository.save(existingMovie);
            return new ResponseEntity<>(request, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response("Movie not found."), HttpStatus.NOT_FOUND);
        }
    }

    public boolean deleteMovie(Long id) {
        Movie existingMovie =  repository.getOne(id);
        if (existingMovie != null) {
            repository.delete(existingMovie);
            return true;
        } else
            return false;
    }

    public List<MovieDto> search(Long id, Long imdb_id, String name, Float imdb_rating, String genre) {
        if (id == null && imdb_id == null && StringUtils.isEmpty(name) && imdb_rating == null && StringUtils.isEmpty(genre))
            return null;

        List<MovieDto> filteredList = new ArrayList<>();
        List<MovieDto> resultSet1 = new ArrayList<>();
        List<MovieDto> resultSet2 = new ArrayList<>();
        List<MovieDto> resultSet3 = new ArrayList<>();

        if (id != null) {
            filteredList.add(new MovieDto(repository.getOne(id)));
            return filteredList;
        }
        if (imdb_id != null) {
            filteredList.add(new MovieDto(repository.findMovieByImdbID(imdb_id)));
            return filteredList;
        }

        if (name != null) {
            for (Movie movie : repository.findMoviesByName(name)) {
                resultSet1.add(new MovieDto(movie));
            }
        }
        if (imdb_rating != null) {
            for (Movie movie : repository.findMoviesByImdbRating(imdb_rating)) {
                resultSet2.add(new MovieDto(movie));
            }
        }
        if (genre != null) {
            for (Movie movie : repository.findMoviesByGenre(genre)) {
                resultSet3.add(new MovieDto(movie));
            }
        }

        if (name != null && imdb_rating != null && !StringUtils.isEmpty(genre)) {
            resultSet1.retainAll(resultSet2);
            resultSet1.retainAll(resultSet3);
            return resultSet1;
        }
        if (name != null && imdb_rating != null) {
            resultSet1.retainAll(resultSet2);
            return resultSet1;
        }
        if (name != null && !StringUtils.isEmpty(genre)) {
            resultSet1.retainAll(resultSet3);
            return resultSet1;
        }
        if (imdb_rating != null && !StringUtils.isEmpty(genre)) {
            resultSet2.retainAll(resultSet3);
            return resultSet1;
        }

        return null;
    }

    public MovieDto movieDetail(Long id) {
        Movie existingMovie =  repository.getOne(id);
        if (existingMovie != null) {
            return new MovieDto(existingMovie);
        } else
            return null;
    }
}
