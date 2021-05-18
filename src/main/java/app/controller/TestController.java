package app.controller;

import app.model.Person;
import app.repository.PersonRepo;
import app.service.ParserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class TestController {

    private static String UPLOADED_FOLDER = "C:\\Users\\karol\\IdeaProjects\\csvToDatabaseFrameworks\\src\\main\\resources\\uploadedFiles\\";
    final static Logger logger = Logger.getLogger(ParserService.class);

    @Autowired
    private ParserService parserService;

    @Autowired
    private PersonRepo personRepo;

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id) {
        Optional<Person> person = Optional.ofNullable(personRepo.findById(id));
        if(person.isPresent()){
            personRepo.deleteById(id);
            logger.info(person.get().toString() + " deleted");
        } else {
            logger.info("There is no user with id: " + id);
        }
    }

    @DeleteMapping("/deleteAll")
    public void clearDatabase() {
        List<Person> usersBeforeDeletion = personRepo.findAll();
        personRepo.deleteAll();
        List<Person> usersAfterDeletion = personRepo.findAll();
        if(usersAfterDeletion.size() - usersBeforeDeletion.size() == usersAfterDeletion.size()){
            logger.info("All users were deleted");
        } else {
            logger.info("Some users weren't deleted");
        }
    }

    @GetMapping("/countUsers")
    public ResponseEntity<Long> countUsers() {
        Long amount = personRepo.countAll();
        logger.info(amount == 1 ? "There is: " + amount + " person" : "There is: " + amount + " users");
        return new ResponseEntity<>(amount, HttpStatus.OK);
    }

    @GetMapping("/findUsersBySurname")
    public ResponseEntity<List<Person>> findUsersBySurname(@RequestParam String surname) {
        List<Person> users = personRepo.findAllBySurname(surname);
        logger.info("There is: " + users.toString() + "with surname: " + surname);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> users(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size) {
        try {
            List<Person> users;
            Pageable paging = PageRequest.of(page, size);
            Page<Person> pageUsers = personRepo.findByOrderByAge(paging);
            users = pageUsers.getContent();
            Map<String, Object> respone = new HashMap<>();
            respone.put("users", users);
            respone.put("currentPage", pageUsers.getNumber());
            respone.put("totalItems", pageUsers.getTotalElements());
            respone.put("totalPages", pageUsers.getTotalPages());
            logger.info("There is: " + users);
            return new ResponseEntity<>(respone, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/theOldestPersonWithNumber")
    public ResponseEntity<Person> theOldestPersonWithNumber() {
        Person person = personRepo.theOldestPersonWithPhoneNumber();
        logger.info("Returned oldest person with number: " + person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadFile
    ) {
        if (uploadFile.isEmpty()) {
            logger.warn("There is no file");
            return new ResponseEntity<>("Please select file!", HttpStatus.OK);
        }
        try {
            saveUploadedFiles(Arrays.asList(uploadFile));
        } catch (IOException e) {
            logger.warn("Bad request");
            return new ResponseEntity<>("File exists!", HttpStatus.BAD_REQUEST);
        }
        try {
            logger.info("trying to save person from csv to db");
            parserService.csvPersonToDb(uploadFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Uploaded: " + uploadFile, HttpStatus.OK);
    }

    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                logger.info(file.getOriginalFilename() + "is empty!");
            } else {
                boolean check = new File(UPLOADED_FOLDER, Objects.requireNonNull(file.getOriginalFilename())).exists();
                if (check) throw new FileAlreadyExistsException("File exists");
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
            }
        }
    }
}

