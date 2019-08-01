package tr.com.obss.bartu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.bartu.controller.response.Response;
import tr.com.obss.bartu.controller.request_container.MovieRequest;
import tr.com.obss.bartu.model.dto.MovieDto;
import tr.com.obss.bartu.service.MovieService;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/admin/add")
    public ResponseEntity<?> addMovie(@RequestBody MovieRequest request) {
        if (movieService.addMovie(request))
            return new ResponseEntity<>(request, HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Movie insertion failed."), HttpStatus.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/admin/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody MovieRequest request) {
        return movieService.updateMovie(id, request);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        if (movieService.deleteMovie(id))
            return new ResponseEntity<>(new Response("Movie successfully deleted with id: " + id), HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Could not found movie with id:" + id), HttpStatus.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/search")
    public ResponseEntity<?> searchMovie(@RequestParam(required = false) Long id, @RequestParam(required = false) Long imdb_id,
                                         @RequestParam(required = false) String name, @RequestParam(required = false) Float imdb_rating,
                                         @RequestParam(required = false) String genre) {
        List<MovieDto> movieList = movieService.search(id, imdb_id, name, imdb_rating, genre);

        if (movieList != null)
            return new ResponseEntity<>(movieList, HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Could not found any movies with query"), HttpStatus.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<?> movieDetail(@PathVariable Long id) {
        MovieDto result = movieService.movieDetail(id);

        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Could not found movie with id: " + id), HttpStatus.BAD_REQUEST);
    }
}
