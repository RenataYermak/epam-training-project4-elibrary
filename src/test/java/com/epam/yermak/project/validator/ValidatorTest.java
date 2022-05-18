package com.epam.yermak.project.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {
    private Validator validator;

    @BeforeEach
    void init() {
        validator = Validator.getInstance();
    }

    @Test
    void testCorrectPasswordShouldBeValid() {
        String password = "Password1234";
        boolean isPasswordValid = validator.isPasswordValid(password);
        assertTrue(isPasswordValid);
    }

    @Test
    void testNullPasswordShouldBeInvalid() {
        boolean isPasswordValid = validator.isPasswordValid(null);
        assertFalse(isPasswordValid);
    }

    @Test
    void testEmptyPasswordShouldBeInvalid() {
        String password = "";
        boolean isPasswordValid = validator.isPasswordValid(password);
        assertFalse(isPasswordValid);
    }

    @Test
    void testPasswordWithoutNumberAndCapitalShouldBeInvalid() {
        String password = "no_numbers_and_capital_letters";
        boolean isPasswordValid = validator.isPasswordValid(password);
        assertFalse(isPasswordValid);
    }

    @Test
    void testPasswordWithoutLettersShouldBeInvalid() {
        String password = "17#*${83!@24";
        boolean isPasswordValid = validator.isPasswordValid(password);
        assertFalse(isPasswordValid);
    }

    @Test
    void testShortPasswordShouldBeInvalid() {
        String password = "Short";
        boolean isPasswordValid = validator.isPasswordValid(password);
        assertFalse(isPasswordValid);
    }

    @Test
    void testLongPasswordWithSpacesShouldBeInvalid() {
        String password = "very long email  that contains spaces";
        boolean isPasswordValid = validator.isPasswordValid(password);
        assertFalse(isPasswordValid);
    }

    @Test
    void testCorrectNameShouldBeValid() {
        String name = "Valid";
        boolean isNameValid = validator.isNameValid(name);
        assertTrue(isNameValid);
    }

    @Test
    void testNullNameShouldBeInvalid() {
        boolean isNameValid = validator.isNameValid(null);
        assertFalse(isNameValid);
    }

    @Test
    void testEmptyNameShouldBeInvalid() {
        String name = "";
        boolean isNameValid = validator.isNameValid(name);
        assertFalse(isNameValid);
    }

    @Test
    void testNameWithNumbersAndSpecialSymbolsShouldBeInvalid() {
        String name = "2%fjw48wf@#3hj8";
        boolean isNameValid = validator.isNameValid(name);
        assertFalse(isNameValid);
    }

    @Test
    void testCorrectEmailShouldBeValid() {
        String email = "test.valid@email.com";
        boolean isEmailValid = validator.isEmailValid(email);
        assertTrue(isEmailValid);
    }

    @Test
    void testNullEmailShouldBeInvalid() {
        boolean isEmailValid = validator.isEmailValid(null);
        assertFalse(isEmailValid);
    }

    @Test
    void testEmptyEmailShouldBeInvalid() {
        String email = "";
        boolean isEmailValid = validator.isEmailValid(email);
        assertFalse(isEmailValid);
    }

    @Test
    void testEmailWithSpacesShouldBeInvalid() {
        String email = "not valid email ";
        boolean isEmailValid = validator.isEmailValid(email);
        assertFalse(isEmailValid);
    }

    @Test
    void testEmailWithoutAtSymbolShouldBeInvalid() {
        String email = "not_valid.email .com";
        boolean isEmailValid = validator.isEmailValid(email);
        assertFalse(isEmailValid);
    }
}

