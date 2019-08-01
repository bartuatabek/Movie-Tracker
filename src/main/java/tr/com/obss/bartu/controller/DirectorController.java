package tr.com.obss.bartu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.bartu.controller.response.Response;
import tr.com.obss.bartu.controller.request_container.DirectorRequest;
import tr.com.obss.bartu.model.dto.DirectorDto;
import tr.com.obss.bartu.model.dto.MovieDto;
import tr.com.obss.bartu.service.DirectorService;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/admin/add")
    public ResponseEntity<?> addDirector(@RequestBody DirectorRequest request) {
        if (directorService.addDirector(request))
            return new ResponseEntity<>(request, HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Director insertion failed"), HttpStatus.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/admin/{id}")
    public ResponseEntity<?> updateDirector(@PathVariable Long id, @RequestBody DirectorRequest request) {
        return directorService.updateDirector(id, request);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteDirector(@PathVariable Long id) {
        if (directorService.deleteDirector(id))
            return new ResponseEntity<>(new Response("Director successfully deleted with id: " + id), HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Could not found director with id: " + id), HttpStatus.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/search")
    public ResponseEntity<?> searchDirector(@RequestParam String name) {
        List<DirectorDto> directorList = directorService.search(name);

        if (directorList != null)
            return new ResponseEntity<>(directorList, HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Could not found any directors with query"), HttpStatus.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<?> directorDetail(@PathVariable Long id) {
        DirectorDto result = directorService.directorDetail(id);

        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Could not found director with id: " + id), HttpStatus.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/{id}/movies")
    public ResponseEntity<?> directorMovies(@PathVariable Long id) {
        List<MovieDto> result = directorService.directorMovies(id);

        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Could not found director movies with id: " + id), HttpStatus.BAD_REQUEST);
    }
}

