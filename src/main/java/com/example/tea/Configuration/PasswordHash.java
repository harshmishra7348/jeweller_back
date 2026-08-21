package com.example.tea.Configuration;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {/*implements PasswordEncoder {*/
    private final String algorithm;

    public PasswordHash() {
        this.algorithm = "SHA-256";
    }

    public PasswordHash(String algorithm) {
        this.algorithm = algorithm;
    }

    public String encode(CharSequence rawPassword) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(this.algorithm);
            byte[] inputDigest = messageDigest.digest(rawPassword.toString().getBytes());
            BigInteger inputDigestBigInt = new BigInteger(1, inputDigest);
            String hashtext = inputDigestBigInt.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equalsIgnoreCase(encodedPassword);
    }
}
