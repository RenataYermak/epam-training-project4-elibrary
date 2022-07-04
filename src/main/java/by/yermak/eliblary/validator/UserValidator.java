package by.yermak.eliblary.validator;

import by.yermak.eliblary.entity.user.User;

public final class UserValidator extends AbstractValidator {
    private static final String NAME_REGEX = "^[\\p{L}]{2,25}$";
    private static final String PASSWORD_REGEX = "(?=.*[\\d])(?=.*[\\p{Ll}])(?=.*[\\p{Lu}])(?=\\S+$).{8,49}";
    private static final String EMAIL_REGEX = "^([\\w-]+[?:[\\w-]+.)*]@([?:[\\w-]+.]*\\w[\\w-]{0,50})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)";
    private static final String LOGIN_REGEX = "^[\\w-]{2,25}$";
    private static final String SEARCH_REGEX = "^[\\p{L}\\d-]{1,25}$";

    private static UserValidator instance;

    private UserValidator() {
    }

    public static UserValidator getInstance() {
        if (instance == null) {
            instance = new UserValidator();
        }
        return instance;
    }

    public boolean isUserValid(User user) {
        return isLoginValid(user.getLogin()) && isPasswordValid(user.getPassword()) &&
               isNameValid(user.getFirstName()) && isNameValid(user.getSecondName()) &&
               isEmailValid(user.getEmail());

    }

    public boolean isLoginValid(String login) {
        return login != null && isFieldValid(LOGIN_REGEX,login);
    }

    public boolean isNameValid(String name) {
        return name != null && isFieldValid(NAME_REGEX, name);
    }

    public boolean isPasswordValid(String password) {
        return password != null && isFieldValid(PASSWORD_REGEX,password);
    }

    public boolean isSearchValid(String searchQuery) {
        return searchQuery != null && isFieldValid(SEARCH_REGEX,searchQuery);
    }

    public boolean isEmailValid(String email) {
        return email != null && isFieldValid(EMAIL_REGEX,email);
    }

}


