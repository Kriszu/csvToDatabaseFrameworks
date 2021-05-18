package app.repository;

import app.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {

    boolean existsByPhone(String phone);

    @Query("SELECT count(*) from Person")
    Long countAll();

    @Query("SELECT p from Person p where p.phone IS NOT NULL AND p.age = (SELECT max(p.age) from Person p)")
    Person theOldestPersonWithPhoneNumber();

    Page<Person> findByOrderByAge(Pageable pageable);

    ArrayList<Person> findAllBySurname(String surname);
}
