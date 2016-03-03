package com.akatsuki.freshy.service.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StringUtils {
  public static String readLine() {
    return readLine(null);
  }

  public static String readLine(final String prompt) {
    try {
      final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      if (prompt != null) {
        System.out.print(prompt + ": ");
      }
      return br.readLine();
    } catch (final Exception e) {
      throw new RuntimeException("An error occurred while reading line!", e);
    }
  }

  public static void notNullOrEmpty(
      final String name,
      final String value) {
    if(value == null || value.isEmpty()) {
        throw new RuntimeException(name + " cannot be null or empty");
    }
  }
}
