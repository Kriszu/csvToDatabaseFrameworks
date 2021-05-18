package app.service;

import app.model.Person;
import app.repository.PersonRepo;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

@Service
public class ParserService {

    final static Logger logger = Logger.getLogger(ParserService.class);

    @Autowired
    PersonRepo personRepo;

    public void csvPersonToDb(String fileName) throws IOException {
        Path myPath = Paths.get("C:\\Users\\karol\\IdeaProjects\\csvToDatabaseFrameworks\\src\\main\\resources\\uploadedFiles\\" + fileName);

        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8);
             CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser)
                     .build()) {
            List<String[]> rows = reader.readAll();

            for (String[] row : rows.subList(1, rows.size())) {
                if (!(row[0].isEmpty() || row[1].isEmpty() || row[2].isEmpty())) {
                    Person person = new Person(row[0], row[1], row[2], !row[3].isEmpty() ? row[3] : "");
                    if (!row[3].isEmpty()) {
                        if (!personRepo.existsByPhone(row[3])) {
                            personRepo.save(person);
                            logger.info("Saved person to database: " + person.toString());
                        }
                    } else {
                        personRepo.save(person);
                        logger.info("Saved person to database: " + person.toString());
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            logger.fatal("Parsing doesn't work!");
        }
    }
}
