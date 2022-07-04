package by.yermak.eliblary.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract class that has method {@link AbstractValidator#isFieldValid(String, String)}
 * to check for the field validity using regular expression pattern.
 */
public abstract class AbstractValidator {

    public boolean isFieldValid(String pattern, String field) {
        Pattern patternCompile = Pattern.compile(pattern);
        Matcher matcher = patternCompile.matcher(field);
        return matcher.matches();
    }
}

