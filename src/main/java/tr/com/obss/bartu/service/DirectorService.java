package tr.com.obss.bartu.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tr.com.obss.bartu.controller.response.Response;
import tr.com.obss.bartu.dao.DirectorRepository;
import tr.com.obss.bartu.model.Director;
import tr.com.obss.bartu.model.Movie;
import tr.com.obss.bartu.controller.request_container.DirectorRequest;
import tr.com.obss.bartu.model.dto.DirectorDto;
import tr.com.obss.bartu.model.dto.MovieDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class DirectorService {

    @Autowired
    private DirectorRepository repository;

    public boolean addDirector(DirectorRequest request) {
        if (StringUtils.isEmpty(request.getName()) || request.getBirthdate() == null ||
                request.getBirth_place().isEmpty())
            return false;

        Director existingDirector = repository.findDirectorByNameAndBirthDateAndBirthPlace(
                request.getName(), request.getBirthdate(),
                request.getBirth_place());

        if (existingDirector != null) {
            return false;
        } else {
            Director newDirector = new Director(request.getName(), request.getBirthdate(),
                    request.getBirth_place());
            repository.save(newDirector);
            return true;
        }
    }

    public ResponseEntity<?> updateDirector(Long id, DirectorRequest request) {
        if (StringUtils.isEmpty(request.getName()) || request.getBirthdate() == null ||
                StringUtils.isEmpty(request.getBirth_place())) {
            return new ResponseEntity<>(new Response("Missing fields updating director."), HttpStatus.BAD_REQUEST);
        }

        Optional<Director> existingDirector = repository.findById(id);

        if (existingDirector.isPresent()) {
            existingDirector.get().setName(request.getName());
            existingDirector.get().setBirthDate(request.getBirthdate());
            existingDirector.get().setBirthPlace(request.getBirth_place());
            existingDirector.get().setMovies(request.getMovies());
            repository.save(existingDirector.get());
            return new ResponseEntity<>(request, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response("Director not found."), HttpStatus.NOT_FOUND);
        }
    }

    public boolean deleteDirector(Long id) {
        Director existingDirector =  repository.getOne(id);
        if (existingDirector != null) {
            repository.delete(existingDirector);
            return true;
        } else
            return false;
    }

    public List<DirectorDto> search(String name) {
        if (StringUtils.isEmpty(name))
            return null;

        List<DirectorDto> filteredList = new ArrayList<>();

        for (Director director:repository.findDirectorByName(name)) {
            filteredList.add(new DirectorDto(director));
        }

        if (filteredList.isEmpty())
            return null;

        return filteredList;
    }

    public DirectorDto directorDetail(Long id) {
        Director existingDirector =  repository.getOne(id);
        if (existingDirector != null) {
            return new DirectorDto(existingDirector);
        } else
            return null;
    }

    public List<MovieDto> directorMovies(Long id) {
        List<MovieDto> directorMovies = new ArrayList<>();

        for (Movie movie:repository.getOne(id).getMovies()) {
            directorMovies.add(new MovieDto(movie));
        }

        if (directorMovies.isEmpty())
            return null;
        return directorMovies;
    }
}
