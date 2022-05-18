package com.epam.yermak.project.util;

import com.epam.yermak.project.util.exception.UtilException;
import com.epam.yermak.project.util.hash.HashGenerator;
import org.junit.Test;

import static org.junit.Assert.*;

public class HashGeneratorTest {
    private static final String PASSWORD = "passWord1234";
    private static final HashGenerator hashGenerator = new HashGenerator();

    @Test
    public void testGenerateHashPasswordNotNullPositive() throws UtilException {
        String hashPass = hashGenerator.generateHash(PASSWORD);
        assertNotNull(hashPass);
    }

    @Test
    public void testGeneratedHashPasswordIsEqualStoredPositive() throws UtilException {
        String hashPass = hashGenerator.generateHash(PASSWORD);
        boolean result = hashGenerator.validatePassword(PASSWORD, hashPass);
        assertTrue(result);
    }

    @Test
    public void testGeneratedHashPasswordIsNotEqualStoredPositive() throws UtilException {
        String hashPass = hashGenerator.generateHash(PASSWORD);
        String differentPassword = "diffPassWord";
        boolean result = hashGenerator.validatePassword(differentPassword, hashPass);
        assertFalse(result);
    }

    @Test(expected = UtilException.class)
    public void testGenerateHashPasswordNotNullNegative() throws UtilException {
        String hashPass = hashGenerator.generateHash(null);
        assertNotNull(hashPass);
    }

    @Test(expected = UtilException.class)
    public void testGeneratedHashPasswordIsNotEqualStoredNegative() throws UtilException {
        String hashPass = hashGenerator.generateHash(PASSWORD);
        boolean result = hashGenerator.validatePassword(null, hashPass);
        assertTrue(result);
    }
}

