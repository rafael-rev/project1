package com.raf.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;


import javax.crypto.spec.PBEKeySpec;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.crypto.SecretKeyFactory;

public class PasswordUtil {
    
    // Logger logger = LogManager.getLogger(PasswordUtil.class);
    
    // private static final Integer iterations = 5000;
    // private static final Integer salt_size = 32; // min in bytes
    // private static final Integer hash_size = 512;
    
    // private constructor
    // private PasswordUtil() {
    // }

    // functions for password encode & decode
    // public String encode(String password){
    //     // initializing a salt
    //     return genPasswordHash(password);
    // }

    // public String genPasswordHash(String password){
    //     char[] split_password = password.toCharArray();

    //     try{
    //         // Initialize key factory with specific PBKDF2 alg using SHA-1
    //         // SHA-1 isn't as strong, but is faster and produces a smaller string to store
    //         SecretKeyFactory sc = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    //         byte[] salt = genSalt();

    //         byte[] hash = calcHash(sc, password, salt);
    //         Boolean checked = verifyPass(sc, password, salt);
    //     }catch(NoSuchAlgorithmException | InvalidKeySpecException e){
    //         logger.error("Password Hashing Error - PasswordUtil - genPasswordHash", e);
    //     }
    //     }


    //     return null;
    // }
}

