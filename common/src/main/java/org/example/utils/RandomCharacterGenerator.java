package org.example.utils;

import java.security.SecureRandom;

public class RandomCharacterGenerator {

    public static String generateRandomString(int LENGTH) {

        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom(); // 안전한 랜덤 숫자 생성
        StringBuilder stringBuilder = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length()); // 무작위 숫자 생성
            stringBuilder.append(CHARACTERS.charAt(index));
        }

        return stringBuilder.toString();
    }
}
