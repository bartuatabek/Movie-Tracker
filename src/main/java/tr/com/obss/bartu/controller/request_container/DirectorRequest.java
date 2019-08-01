package tr.com.obss.bartu.controller.request_container;

import java.util.Date;

public class DirectorRequest {

    private String name;

    private String lastname;

    private Date birthdate;

    private String birth_place;

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getBirth_place() {
        return birth_place;
    }

}
