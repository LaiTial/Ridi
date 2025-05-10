package org.example.utils;

public class PasswordValidator {

    public static boolean isValid(String password) {

        boolean hasLetter = password.chars().anyMatch(Character::isLetter);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecialChar = password.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));

        int count = 0;
        if (hasLetter) count++;
        if (hasDigit) count++;
        if (hasSpecialChar) count++;

        return count >= 2; // 2가지 이상일 경우 유효
    }
}