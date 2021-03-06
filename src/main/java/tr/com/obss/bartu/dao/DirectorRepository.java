package tr.com.obss.bartu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.obss.bartu.model.Director;

import java.util.Date;
import java.util.List;

public interface DirectorRepository extends JpaRepository<Director, Long> {

 Director findDirectorByNameAndBirthDateAndBirthPlace(String name, Date birthDate,  String birthPlace);
 List<Director> findDirectorByName(String name);
}
