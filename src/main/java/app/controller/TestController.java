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
        logger.info("Trying to delete user with id: " + id);
        personRepo.deleteById(id);
    }

    @DeleteMapping("/deleteAll")
    public void clearDatabase() {
        logger.info("Trying to delete table");
        personRepo.deleteAll();
    }

    @GetMapping("/countUsers")
    public ResponseEntity<Long> countUsers() {
        logger.info("Trying to return amount of users");
        return new ResponseEntity<>(personRepo.countAll(), HttpStatus.OK);
    }

    @GetMapping("/findUsersBySurname")
    public ResponseEntity<List<Person>> findUsersBySurname(@RequestParam String surname) {
        return new ResponseEntity<>(personRepo.findAllBySurname(surname), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> users(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size) {
        try {
            List<Person> users;
            Pageable paging = PageRequest.of(page, size);
            Page<Person> pageUsers = personRepo.findByOrderByAge(paging);
            users = pageUsers.getContent();
            Map<String, Object> resposne = new HashMap<>();
            resposne.put("users", users);
            resposne.put("currentPage", pageUsers.getNumber());
            resposne.put("totalItems", pageUsers.getTotalElements());
            resposne.put("totalPages", pageUsers.getTotalPages());
            return new ResponseEntity<>(resposne, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/theOldestPersonWithNumber")
    public ResponseEntity<Person> theOldestPersonWithNumber() {
        logger.info("Trying to return the oldest person with number");
        return new ResponseEntity<>(personRepo.theOldestPersonWithPhoneNumber(), HttpStatus.OK);
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

