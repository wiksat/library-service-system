package pl.edu.agh.validator;

import java.util.regex.Pattern;

public class UserValidator {

    private static boolean checkPattern(String word, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(word).matches();
    }
    public static boolean isMailValid(String mail) {
        String mailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return checkPattern(mail, mailRegex);
    }
    public static boolean isFirstNameValid(String mail) {
        String firstNameRegex = "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]{1,29}$";
        return checkPattern(mail, firstNameRegex);
    }
    public static boolean isLastNameValid(String mail) {
        String lastNameRegex = "^[A-ZĄĆĘŁŃÓŚŹŻ][a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż\\-']{1,29}$";
        return checkPattern(mail, lastNameRegex);
    }
    public static boolean isPasswordStrong(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-ząćęłńóśźż])(?=.*[A-ZĄĆĘŁŃÓŚŹŻ])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return checkPattern(password, passwordRegex);
    }
    // Password contains at least 8 characters.
    // Password contains at least one digit.
    // Password contains at least one upper case alphabet.
    // Password contains at least one lower case alphabet.
    // Password contains at least one special character.

}
