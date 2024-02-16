package pl.edu.agh.validator;

import org.junit.jupiter.api.Test;
import pl.edu.agh.validator.UserValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidatorTest {

    @Test
    public void isMailValidTest() {
        assertTrue(UserValidator.isMailValid("asd@wp.pl"));
        assertTrue(UserValidator.isMailValid("1-2-3-4-5@wp.pl"));
        assertTrue(UserValidator.isMailValid("1@wp23.plqwe"));
        assertTrue(UserValidator.isMailValid("asd_dst@wp.pldsa"));
        assertTrue(UserValidator.isMailValid("abc.abc@wc3cqp.pecxl"));
        assertTrue(UserValidator.isMailValid("12345679123456789123456789@wp.pl"));
        assertFalse(UserValidator.isMailValid("wrongmail"));
        assertFalse(UserValidator.isMailValid("wrongmail@wp"));
        assertFalse(UserValidator.isMailValid("wrongmail@wp."));
        assertFalse(UserValidator.isMailValid("wrongmail@wppl"));
        assertFalse(UserValidator.isMailValid("wrongmail@wp!wp.pl"));
        assertFalse(UserValidator.isMailValid("wrongmail.pl"));
        assertFalse(UserValidator.isMailValid("wrongmail@wp.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    public void isFirstNameValidTest() {
        assertTrue(UserValidator.isFirstNameValid("Adam"));
        assertTrue(UserValidator.isFirstNameValid("Adamdlugieimieaaaaaaaaaaaaaaa"));
        assertFalse(UserValidator.isFirstNameValid("wrongName"));
        assertFalse(UserValidator.isFirstNameValid("EvenWorseName"));
        assertFalse(UserValidator.isFirstNameValid("TerribleIdea1"));
        assertFalse(UserValidator.isFirstNameValid("Don'tEvenTry"));
    }

    @Test
    public void isLastNameValidTest() {
        assertTrue(UserValidator.isLastNameValid("Nowak"));
        assertTrue(UserValidator.isLastNameValid("Nowak-Kowalski"));
        assertTrue(UserValidator.isLastNameValid("Nowak-Kowalski-Nowak-Kowalski"));
        assertTrue(UserValidator.isLastNameValid("Dobre-nazwisko"));
        assertFalse(UserValidator.isLastNameValid("adamAdamczyk"));
        assertFalse(UserValidator.isLastNameValid("Numer1"));
        assertFalse(UserValidator.isLastNameValid("tonieprzejdzie"));
        assertFalse(UserValidator.isLastNameValid("To tez nie"));
        assertFalse(UserValidator.isLastNameValid(":("));
    }
}
