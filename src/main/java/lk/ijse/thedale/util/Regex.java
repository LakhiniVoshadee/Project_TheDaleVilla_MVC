/*package lk.ijse.thedale.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static boolean isTextFieldValid(TextField textField, String text) {
        String field = "";

        switch (textField){
            case ID :
                field = "^([A-Z0-9]{3})$";
            case NIC :
                field = "^([0-9]{9}[X|x|V|v]|[0-9]{12})$";
                break;
        }
        Pattern pattern = Pattern.compile(field);

        if(text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }


    public static boolean setTextColour(TextField location, TextField field){
//        if (Regex.isTextFieldValid(location,field.getText())){
//            field.setFocusColo
//        }
        return true;
    }
}
*/