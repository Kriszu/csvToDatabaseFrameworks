package app;

import app.model.Person;
import app.service.ParserService;
import app.repository.PersonRepo;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class App {

    @Autowired
    static PersonRepo repo;

    @Autowired
    static ParserService parserService;

    public static void main(String[] args){
        SpringApplication.run(App.class, args);
    }

}

