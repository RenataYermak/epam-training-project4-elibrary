package com.epam.project.validator;

import com.epam.project.model.user.User;

public class Validator {
    private static final String NAME_REGEX = "^[\\p{L}-]{2,25}$";
    private static final String PASSWORD_REGEX = "^[\\w-]{8,16}$";
    //  private static final String EMAIL_REGEX = "^(([\\w-]+)@([\\w]+)\\.([\\p{Lower}]{2,6})){45}$";
    // private static final String PHONE_REGEX = "^[+]?[\\d]{7,15}$";
    private static final String LOGIN_REGEX = "^[\\w\\d-]{2,25}$";
    private static final String BOOK_SEARCH_REGEX = "^[\\p{L}\\d-.]{2,25}$";

    public Validator() {
    }

    public static class ValidatorHolder {
        public static final Validator HOLDER_INSTANCE = new Validator();
    }

    public static Validator getInstance() {
        return ValidatorHolder.HOLDER_INSTANCE;
    }

    public boolean isUserValid(User user) {

        return isLoginValid(user.getLogin()) && isPasswordValid(user.getPassword()) &&
                isNameValid(user.getFirstName()) && isNameValid(user.getSecondName());

    }

    public boolean isLoginValid(String login) {
        return login != null && login.matches(LOGIN_REGEX);
    }

    public boolean isNameValid(String name) {
        return name != null && name.matches(NAME_REGEX);
    }

    public boolean isPasswordValid(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    public boolean isSearchValid(String searchQuery) {
        return searchQuery != null && searchQuery.matches(BOOK_SEARCH_REGEX);
    }
}

//    public boolean isPhoneValid(String phone) {
//        return phone != null && phone.matches(PHONE_REGEX);
//    }

//    public boolean isEmailValid(String email) {
//        return email != null && email.matches(EMAIL_REGEX);
//    }


