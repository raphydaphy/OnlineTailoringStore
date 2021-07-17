package com.tailoring.tailoringstore.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
  public static String mysqlDate(String inputDate) {
    SimpleDateFormat fromFmt = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat toFmt = new SimpleDateFormat("yyyy/MM/dd");

    try {
      Date date = fromFmt.parse(inputDate);
      return toFmt.format(date);
    } catch (Exception e) {
      System.err.println("Failed to format date '" + inputDate + "': " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }
}
