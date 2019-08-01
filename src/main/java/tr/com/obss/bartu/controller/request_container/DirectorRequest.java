package tr.com.obss.bartu.controller.request_container;

import tr.com.obss.bartu.model.Movie;

import java.util.Date;
import java.util.Set;

public class DirectorRequest {

    private Set<Movie> movies;

    private String name;

    private Date birthdate;

    private String birth_place;

    public String getName() {
        return name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getBirth_place() {
        return birth_place;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

}
