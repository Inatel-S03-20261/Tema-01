package com.inatel.auth_service.validator;

import com.inatel.auth_service.exception.PasswordsDoNotMatchException;

public class PasswordMatchValidator {
    public static void comparePasswords(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new PasswordsDoNotMatchException("Passwords do not match!");
        }
    }
}
