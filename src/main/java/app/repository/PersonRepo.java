package app.repository;

import app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {

    boolean existsByPhone(String phone);
    @Query("SELECT count(*) from Person")
    Long countAll();
    List<Person> findByOrderByDate();
    @Query("SELECT p from Person p where p.phone IS NOT NULL AND p.date = (SELECT min(p.date) from Person p)")
    Person theOldestPersonWithPhoneNumber();
}
