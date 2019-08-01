package tr.com.obss.bartu.service.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.Unirest;
import tr.com.obss.bartu.model.dto.DirectorDto;
import tr.com.obss.bartu.model.dto.MovieDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RestClient {

    private final String API_KEY = "edaba70163b3e476e188142226a8d5e8";
    private final String API_URL = "https://api.themoviedb.org/3/";
    private final String API_URL_SEARCH_END = "search/movie";
    private final String API_URL_FIND_END = "/find/";
    private final String API_URL_DISCOVER_END = "/discover/movie";
    private final String API_URL_DETAIL_END = "/movie/";
    private final String API_URL_PEOPLE_END = "/person/";
    private final String IMAGE_URL = "http://image.tmdb.org/t/p/original/";

    public List<MovieDto> getMovieByName(String name) {
        List<MovieDto> movies = new ArrayList<>();
        String searchResponse = Unirest.get(API_URL+ API_URL_SEARCH_END +"?api_key="+API_KEY+"&language=en-US&query="+name+"&include_adult=false")
                .asString().getBody();

        JsonElement jElement = new JsonParser().parse(searchResponse);
        jElement.getAsJsonObject().get("results").getAsJsonArray().forEach(obj-> movies.add(getMovieById(obj.getAsJsonObject().get("id").getAsLong())));
        return movies;
    }

    private DirectorDto getDirectorById(Long IMDBId){
        String directorResponse = Unirest.get(API_URL+ API_URL_PEOPLE_END +IMDBId+"?api_key="+API_KEY+"&language=en-US")
                .asString().getBody();
        JsonElement jsonElementDirector = new JsonParser().parse(directorResponse);
        JsonObject jObjectDirector = jsonElementDirector.getAsJsonObject();
        DirectorDto director = new DirectorDto();
        try {
            director.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(jObjectDirector.get("birthday").getAsString()));
            director.setBirthPlace(jObjectDirector.get("place_of_birth").getAsString());
            director.setName(jObjectDirector.get("name").getAsString());//last name and name did not separated
            Set<MovieDto> movies = new HashSet<>();
            jObjectDirector.get("movie_credits").getAsJsonArray().forEach(credits->{
                credits.getAsJsonObject().get("crew").getAsJsonArray().forEach(movieCredit->{
                    if(movieCredit.getAsJsonObject().get("department").getAsString().equals("Directing")){
                        MovieDto movie = new MovieDto();
                        movie.setId(movieCredit.getAsJsonObject().get("id").getAsLong());
                        movies.add(movie);
                    }
                });
            });
            director.setMovies(movies);
        } catch (ParseException e) {
            e.printStackTrace();//TODO: use logger
            return null;
        }
        return director;
    }

    private MovieDto getMovieById(Long movieId){
        String movieDetailsResponse = Unirest.get(API_URL+ API_URL_DETAIL_END +movieId+"?api_key="+API_KEY+"&language=en-US&append_to_response=credits")
                .asString().getBody();
        JsonElement jElementMovie = new JsonParser().parse(movieDetailsResponse);
        JsonObject jObjectMovie = jElementMovie.getAsJsonObject();
        MovieDto movie = new MovieDto();
        try {
            movie.setId(jObjectMovie.get("id").getAsLong());
            movie.setImdbID(jObjectMovie.get("imdb_id").getAsLong());//Autocast denemesi
            movie.setName(jObjectMovie.get("original_title").getAsString());
            movie.setRelease_date(new SimpleDateFormat("yyyy-MM-dd").parse(jObjectMovie.get("release_date").getAsString()));
            movie.setImdbRating(jObjectMovie.get("vote_average").getAsFloat());//This is no IMDB rating
            movie.setRuntime(jObjectMovie.get("runtime").getAsInt());
            Set<DirectorDto> directors = new HashSet<>();
            jObjectMovie.get("credits").getAsJsonObject().get("crew").getAsJsonArray().forEach(crew->{
                if(crew.getAsJsonObject().get("job").getAsString().equals("Director")){
                    DirectorDto director = new DirectorDto();
                    director.setId(crew.getAsJsonObject().get("id").getAsLong());
                    directors.add(director);
                }
            });
            movie.setDirectors(directors);
            List<String> genres = new ArrayList<>();
            jObjectMovie.get("genres").getAsJsonArray().forEach(genre->{
                genres.add(genre.getAsJsonObject().get("name").getAsString());
            });
            movie.setGenre(genres);
            movie.setPosterUrl(IMAGE_URL + jObjectMovie.get("poster_path").getAsString());
        } catch (ParseException e) {
            e.printStackTrace();//TODO: use logger
            return null;
        }
        return movie;
    }

    /*

    public DirectorDto getDirectorByIMDBId(Long IMDBId){
        String personDetailsResponse = Unirest.get(API_URL+ API_URL_FIND_END +IMDBId+"?api_key="+API_KEY+"&language=en-US&external_source=imdb_id")
                .asString().getBody();
        JsonElement jElementDirector = new JsonParser().parse(personDetailsResponse);
        JsonArray personResults = jElementDirector.getAsJsonObject().get("person_results").getAsJsonArray();
        if (personResults != null) {
            return getDirectorById(personResults.get(0).getAsJsonObject().get("id").getAsLong());
        }
        return null;
    }

    public MovieDto getMovieByIMDBId(Long IMDBId){
        String movieDetailsResponse = Unirest.get(API_URL+ API_URL_FIND_END +IMDBId+"?api_key="+API_KEY+"&language=en-US&external_source=imdb_id")
                .asString().getBody();
        JsonElement jElementMovie = new JsonParser().parse(movieDetailsResponse);
        JsonArray movieResults = jElementMovie.getAsJsonObject().get("movie_results").getAsJsonArray();
        if (movieResults != null) {
            return getMovieById(movieResults.get(0).getAsJsonObject().get("id").getAsLong());
        }
        return null;
    }

    */

    public Object getObjectByIMDBId(Long IMDBId){
        String objectDetailsResponse = Unirest.get(API_URL+ API_URL_FIND_END +IMDBId+"?api_key="+API_KEY+"&language=en-US&external_source=imdb_id")
                .asString().getBody();
        JsonElement jElementObject = new JsonParser().parse(objectDetailsResponse);
        JsonArray personResults = jElementObject.getAsJsonObject().get("person_results").getAsJsonArray();
        JsonArray movieResults = jElementObject.getAsJsonObject().get("movie_results").getAsJsonArray();
        if(personResults != null){
            return getDirectorById(personResults.get(0).getAsJsonObject().get("id").getAsLong());
        }
        else {
            return getMovieById(movieResults.get(0).getAsJsonObject().get("id").getAsLong());
        }
    }

    public List<MovieDto> listMovies(int page){
        List<MovieDto> movies = new ArrayList<>();
        String listResponse = Unirest.get(API_URL+ API_URL_DISCOVER_END +"?api_key="+API_KEY+"&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page="+page)
                .asString().getBody();
        JsonElement jElement = new JsonParser().parse(listResponse);
        jElement.getAsJsonObject().get("results").getAsJsonArray().forEach(obj-> movies.add(getMovieById(obj.getAsJsonObject().get("id").getAsLong())));
        return movies;
    }
}
