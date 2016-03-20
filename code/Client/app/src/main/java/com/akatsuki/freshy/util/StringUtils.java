package com.akatsuki.freshy.util;

import java.util.List;

public class StringUtils {

  public static String listToString(List<String> list) {
    if(list == null) {
      return "";
    }
    String str = "";
    for (String string : list) {
      str += string + " ";
    }
    return str;
  }
}
