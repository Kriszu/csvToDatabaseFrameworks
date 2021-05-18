package app;

import app.repository.PersonRepo;
import app.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    @Autowired
    static PersonRepo repo;

    @Autowired
    static ParserService parserService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}

