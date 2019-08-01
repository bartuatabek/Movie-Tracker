package tr.com.obss.bartu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.bartu.controller.request_container.ListRequest;
import tr.com.obss.bartu.controller.request_container.UserLoginRequest;
import tr.com.obss.bartu.controller.request_container.UserRequest;
import tr.com.obss.bartu.controller.request_container.UserUpdateRequest;
import tr.com.obss.bartu.controller.response.Response;
import tr.com.obss.bartu.model.dto.MovieDto;
import tr.com.obss.bartu.model.dto.UserDto;
import tr.com.obss.bartu.service.UserService;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/admin/add")
    public ResponseEntity<?> addUser(@RequestBody UserRequest request) {
        if (userService.addUser(request))
            return new ResponseEntity<>(request, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(new Response("User insertion failed."), HttpStatus.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/admin")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest request) {
        return userService.updateUser(request);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id))
            return new ResponseEntity<>(new Response("User successfully deleted with id: " + id), HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Could not found user with id: " + id), HttpStatus.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin/search")
    public ResponseEntity<?> searchUser(@RequestParam(required = false) Long id, @RequestParam(required = false) String username, @RequestParam(required = false) String email) {
        List<UserDto> userList = userService.search(id, username, email);

        if (userList != null)
            return new ResponseEntity<>(userList, HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Could not found any users with query"), HttpStatus.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<?> userDetail(@PathVariable Long id) {
        UserDto result = userService.userDetail(id);

        if (result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Could not found user with id: " + id), HttpStatus.NOT_FOUND);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/{id}/lists")
    public ResponseEntity<?> addMovieToList(@PathVariable Long id, @RequestBody ListRequest request) {
        if (userService.addMovieToList(id, request))
            return new ResponseEntity<>(new Response("Movie successfully added to " + request.getListname()), HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(new Response("Could not add movie to list"), HttpStatus.BAD_REQUEST);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}/lists")
    public ResponseEntity<?> getMoviesFromList(@PathVariable Long id, @RequestBody ListRequest request) {
        List<MovieDto> movieList = userService.getMoviesFromList(id, request);

        if (movieList != null)
            return new ResponseEntity<>(movieList, HttpStatus.OK);
        else
            return new ResponseEntity<>(new Response("Could not found any movies in the list"), HttpStatus.NOT_FOUND);
    }
}