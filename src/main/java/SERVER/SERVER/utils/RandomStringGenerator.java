package SERVER.SERVER.utils;

import java.security.SecureRandom;

public class RandomStringGenerator {

    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    public static String generateRandomString(int length){
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHAR_SET.length());
            stringBuilder.append(randomIndex);
        }
        return stringBuilder.toString();
    }
}
