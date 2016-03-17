package com.akatsuki.freshy.service.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

  public static byte[] GenerateByteSHA1(String string) {
    try {
      byte[] bHash = new byte[20];
      bHash = encryptPassword(string);
      return bHash;

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String GenerateStringSHA1(String string) {

    try {
      byte[] bHash = new byte[20];
      bHash = encryptPassword(string);

      String hash;
      hash = new BigInteger(1, bHash).toString(16);
      return hash;

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static byte[] encryptPassword(String string)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {

    MessageDigest crypt = MessageDigest.getInstance("SHA-1");
    crypt.reset();
    crypt.update(string.getBytes("UTF-8"));

    // return new BigInteger(1, crypt.digest()).toString(16);
    return crypt.digest();
  }

}
