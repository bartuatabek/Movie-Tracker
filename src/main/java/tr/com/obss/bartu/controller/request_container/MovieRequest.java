package tr.com.obss.bartu.controller.request_container;

import tr.com.obss.bartu.model.Director;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public class MovieRequest {

    private String name;

    private Director director;

    private Date release_date;

    private Integer runtime;

    private List<String> genre;

    private Float imdb_rating;

    private String posterUrl;

    private Long imdb_id = null;

    public String getName() {
        return name;
    }

    public Director getDirector() {
        return director;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public List<String> getGenre() {
        return genre;
    }

    public Float getImdb_rating() {
        return imdb_rating;
    }

    public Long getImdb_id() {
        return imdb_id;
    }
}
