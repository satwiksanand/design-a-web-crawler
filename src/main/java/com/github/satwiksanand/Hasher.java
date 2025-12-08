package com.github.satwiksanand;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class Hasher {
    public static String hash(String str){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(encodedHash);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;//in case error happens
    }
}
