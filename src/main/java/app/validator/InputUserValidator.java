package app.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InputUserValidator {

    public String nameValidator(String s) {
        s = s.replace(" ", "");
        s = s.replace("    ", "");
        return s.replaceFirst(Character.toString(s.charAt(0)), Character.toString(s.charAt(0)).toUpperCase());
    }

    public boolean numberValidator(String s) {
        return s.length() == 9;
    }

    public Date dateValidator(String s) throws ParseException {
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy.mm.dd");
        return formatter1.parse(s);
    }
}
