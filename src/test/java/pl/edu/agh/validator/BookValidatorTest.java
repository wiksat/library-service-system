package pl.edu.agh.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookValidatorTest {

    @Test
    public void isTitleValidTest() {
        assertTrue(BookValidator.isTitleValid("Harry Potter"));
        assertTrue(BookValidator.isTitleValid("Harry Potter and the Philosopher's Stone"));
        assertTrue(BookValidator.isTitleValid("Harry Potter and the Chamber of Secrets"));
        assertTrue(BookValidator.isTitleValid("Przygody cz.1"));
        assertFalse(BookValidator.isTitleValid("Addskj@mail.pl"));
        assertFalse(BookValidator.isTitleValid("1234$#ASD AGH"));
    }

    @Test
    public void isAuthorValidTest() {
        assertTrue(BookValidator.isAuthorValid("J.K. Rowling"));
        assertTrue(BookValidator.isAuthorValid("Janusz Kowalski"));
        assertTrue(BookValidator.isAuthorValid("Janusz Kowalski-Nowak"));
        assertTrue(BookValidator.isAuthorValid("A. Mietek"));
        assertFalse(BookValidator.isAuthorValid("Hk& Ko"));
        assertFalse(BookValidator.isAuthorValid("Karol32"));
        assertFalse(BookValidator.isAuthorValid("tonieprzejdzie"));
        assertFalse(BookValidator.isAuthorValid("abc.abc@wc3cqp.pecxl"));
    }

    @Test
    public void isIsbnValidTest() {
        assertTrue(BookValidator.isIsbnValid("1234567890123"));
        assertTrue(BookValidator.isIsbnValid("2000080090122"));
        assertFalse(BookValidator.isIsbnValid("123456789012"));
        assertFalse(BookValidator.isIsbnValid("Null"));
        assertFalse(BookValidator.isIsbnValid("11111111178901234"));

    }
}
