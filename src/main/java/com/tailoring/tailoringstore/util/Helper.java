package com.tailoring.tailoringstore.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
  public static String mysqlDate(String bootstrapDate) {
    if (bootstrapDate == null) return null;

    SimpleDateFormat fromFmt = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat toFmt = new SimpleDateFormat("yyyy-MM-dd");

    try {
      Date date = fromFmt.parse(bootstrapDate);
      return toFmt.format(date);
    } catch (Exception e) {
      System.err.println("Failed to format bootstrap date '" + bootstrapDate + "': " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  public static String bootstrapDate(String mysqlDate) {
    if (mysqlDate == null) return null;

    SimpleDateFormat fromFmt = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat toFmt = new SimpleDateFormat("MM/dd/yyyy");

    try {
      Date date = fromFmt.parse(mysqlDate);
      return toFmt.format(date);
    } catch (Exception e) {
      System.err.println("Failed to format mysql date '" + mysqlDate + "': " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }
}
