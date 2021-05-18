package app.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;

public class InputUserValidator {

    public String nameValidator(String s) {
        s = deleteSpacesFromString(s);
        s = changeFirstLetterToUppercase(s);
        return s;
    }

    public boolean numberValidator(String s) {
        return s.length() == 9;
    }

    public int countAge(String s) throws ParseException {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy.mm.dd");
        LocalDateTime currentDate = LocalDateTime.now();
        Date dateOfBirth = formatter1.parse(s);
        LocalDate localDateOfBirth = convertToLocalDateViaInstant(dateOfBirth);
        Period age = Period.between(localDateOfBirth, currentDate.toLocalDate());
        return age.getYears();
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private String deleteSpacesFromString(String s){
        return s.replaceAll(" ", "");
    }

    private String changeFirstLetterToUppercase(String s){
        return s.replaceFirst(String.valueOf(s.charAt(0)),Character.toString(s.charAt(0)).toUpperCase());
    }
}
