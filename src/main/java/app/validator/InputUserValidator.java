package app.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InputUserValidator {

    public String nameValidator(String s) {
        s = deleteSpacesFromString(s);
        s = changeFirstLetterToUppercase(s);
        return s;
    }

    public boolean numberValidator(String s) {
        return s.length() == 9;
    }

    public Date dateValidator(String s) throws ParseException {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy.mm.dd");
        return formatter1.parse(s);
    }

    private String deleteSpacesFromString(String s){
        return s.replaceAll(" ", "");
    }

    private String changeFirstLetterToUppercase(String s){
        return s.replaceFirst(String.valueOf(s.charAt(0)),Character.toString(s.charAt(0)).toUpperCase());
    }
}
