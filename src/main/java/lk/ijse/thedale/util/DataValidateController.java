package lk.ijse.thedale.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidateController {

    //Employee Form

    public static boolean validateEmpName(String name) {
        String nameRegex = "^[A-z|\\\\s]{3,}$";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean validateEmpType(String type) {
        String typeRegex = "^[A-z|\\\\s]{3,}$";
        Pattern pattern = Pattern.compile(typeRegex);
        Matcher matcher = pattern.matcher(type);
        return matcher.matches();
    }

    public static boolean validateEmail(String email) {
        String emailRegex = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



    //Customer Form


}
