package tr.com.obss.bartu.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.bartu.controller.request_container.ListRequest;
import tr.com.obss.bartu.controller.request_container.UserLoginRequest;
import tr.com.obss.bartu.controller.request_container.UserRequest;
import tr.com.obss.bartu.controller.request_container.UserUpdateRequest;
import tr.com.obss.bartu.controller.response.Response;
import tr.com.obss.bartu.dao.MovieListRepository;
import tr.com.obss.bartu.dao.MovieRepository;
import tr.com.obss.bartu.dao.UserRepository;
import tr.com.obss.bartu.model.Movie;
import tr.com.obss.bartu.model.MovieList;
import tr.com.obss.bartu.model.User;
import tr.com.obss.bartu.model.dto.MovieDto;
import tr.com.obss.bartu.model.dto.UserDto;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MovieListRepository movieListRepository;

    @Autowired
    private MovieRepository movieRepository;

    public boolean addUser(UserRequest request) {
        if (StringUtils.isEmpty(request.getUsername()) || StringUtils.isEmpty(request.getPassword()))
            return false;

        User existingUser = repository.findUsersByUsernameContainingIgnoreCase(request.getUsername());

        if (existingUser != null) {
            return false;
        } else {
            final String encodedPassword = passwordEncoder.encode(request.getPassword());
            User newUser = new User(request.getUsername(),encodedPassword, request.getEmail(),request.getRole());
            repository.save(newUser);
            return true;
        }
    }

    public boolean loginUser(UserLoginRequest request) {
        if (StringUtils.isEmpty(request.getUsername()) || StringUtils.isEmpty(request.getPassword()))
            return false;

        User existingUser = repository.findUsersByUsernameContainingIgnoreCase(request.getUsername());

        return existingUser != null && passwordEncoder.matches(request.getPassword(), existingUser.getPassword()) && existingUser.isEnabled();
    }

    public ResponseEntity<?> updateUser(UserUpdateRequest request) {
        if (StringUtils.isEmpty(request.getUsername()) || StringUtils.isEmpty(request.getPassword()) || StringUtils.isEmpty(request.getEmail()))
            return new ResponseEntity<>(new Response("Missing fields updating user with id: " + request.getId()), HttpStatus.BAD_REQUEST);
        else if (!request.getPassword().equals(request.getConfirm_password()))
            return new ResponseEntity<>(new Response("Password mismatch."), HttpStatus.BAD_REQUEST);

        User existingUser =  repository.getOne(request.getId());
        final String encodedPassword = passwordEncoder.encode(request.getPassword());

        if (existingUser != null) {
            User existingUsername = repository.findUsersByUsernameContainingIgnoreCase(request.getUsername());

            if (existingUsername != null && !existingUsername.getId().equals(request.getId())) {
                return new ResponseEntity<>(new Response("Username unavailable."), HttpStatus.CONFLICT);
            } else {
                existingUser.setUsername(request.getUsername());
            }

            existingUser.setPassword(encodedPassword);
            existingUser.setEmail(request.getEmail());
            existingUser.setEnabled(request.isEnabled());
            existingUser.setRole(request.getRole());
            repository.save(existingUser);
            return new ResponseEntity<>(request, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response("User not found."), HttpStatus.NOT_FOUND);
        }
    }

    public boolean deleteUser(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public List<UserDto> search(Long id, String username, String email) {
        if (id == null && StringUtils.isEmpty(username) && StringUtils.isEmpty(email))
            return null;

        List<UserDto> filteredList = new ArrayList<>();

        if (id != null) {
            try {
                filteredList.add(new UserDto(repository.getOne(id)));
                return filteredList;
            } catch (EntityNotFoundException e) {
                return null;
            }
        }
        if (!StringUtils.isEmpty(username)) {
            try {
                filteredList.add(new UserDto(repository.findUsersByUsernameContainingIgnoreCase(username)));
                return filteredList;
            } catch (NullPointerException e) {
                return null;
            }
        }
        if (!StringUtils.isEmpty(email)) {
            for (User user:repository.findUsersByEmail(email)) {
                filteredList.add(new UserDto(user));
            }
            if (filteredList.isEmpty())
                return null;
            else
                return filteredList;
        }
        return null;
    }

    public UserDto userDetail(Long id) {
        try {
            User existingUser = repository.getOne(id);
            return new UserDto((existingUser));
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    public boolean addMovieToList(Long id, ListRequest request) {
        if (StringUtils.isEmpty(request.getListname()) || request.getMovieId() == null)
            return false;

        User existingUser = repository.getOne(id);
        Movie existingMovie = movieRepository.getOne(request.getMovieId());

        if (existingUser != null) {
            return false;
        } else {
            MovieList list = new MovieList(request.getListname(), existingUser, existingMovie);
            movieListRepository.save(list);
            return true;
        }
    }

    public List<MovieDto> getMoviesFromList(Long id, ListRequest request) {
        List<MovieDto> movies = null;

        if (id != null && !StringUtils.isEmpty(request.getListname())) {
            List<MovieList> movieList = movieListRepository.findMovieListsByListNameAndUserId(request.getListname(), id);

            for (MovieList item: movieList) {
                movies.add(new MovieDto(item.getMovie()));
            }
        }
        return movies;
    }
}
