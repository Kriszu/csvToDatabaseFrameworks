package app.controller;

import app.model.Person;
import app.repository.PersonRepo;
import app.service.ParserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class TestController {

    private static String UPLOADED_FOLDER = "C:\\Users\\48734\\IdeaProjects\\csvUserWeb\\src\\main\\resources\\";
    final static Logger logger = Logger.getLogger(ParserService.class);

    @Autowired
    private ParserService parserService;

    @Autowired
    private PersonRepo personRepo;

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id){
        logger.info("Trying to delete user with id: " + id);
        personRepo.deleteById(id);
    }

    @DeleteMapping("/deleteAll")
    public void clearDatabase(){
        logger.info("Trying to delete table");
        personRepo.deleteAll();
    }
    @GetMapping("/countUsers")
    public ResponseEntity<Long> countUsers(){
        logger.info("Trying to return amount of users");
        return new ResponseEntity<>(personRepo.countAll(), HttpStatus.OK);
    }

    @GetMapping("/userList")
    public ResponseEntity<List<Person>> userList(){
        logger.info("Trying to return userList");
        return new ResponseEntity<>(personRepo.findByOrderByDate(), HttpStatus.OK);
    }

    @GetMapping("/theOldestPersonWithNumber")
    public ResponseEntity<Person> theOldestPersonWithNumber(){
        logger.info("Trying to return the oldest person with number");
        return new ResponseEntity<>(personRepo.theOldestPersonWithPhoneNumber(), HttpStatus.OK);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file")MultipartFile uploadFile
            ){
        if(uploadFile.isEmpty()){
            logger.warn("There is no file");
            return new ResponseEntity<>("Please select file!", HttpStatus.OK);
        }
        try{
            saveUploadedFiles(Arrays.asList(uploadFile));
        } catch (IOException e) {
            logger.warn("Bad request");
            return new ResponseEntity<>("File exists!",HttpStatus.BAD_REQUEST);
        }
        try {
            logger.info("trying to save person from csv to db");
            parserService.csvPersonToDb(uploadFile.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Uploaded: " + uploadFile, HttpStatus.OK);
    }

    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        Stream<Path> filesList = Files.walk(Paths.get(UPLOADED_FOLDER));


        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
                boolean check = new File(UPLOADED_FOLDER, Objects.requireNonNull(file.getOriginalFilename())).exists();
                if(check) throw new FileAlreadyExistsException("File exists");
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
            }
        }


}

