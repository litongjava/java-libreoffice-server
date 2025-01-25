package com.litongjava.office.convert;

import com.litongjava.office.convert.config.WebHelloConfig;
import com.litongjava.tio.boot.TioApplication;

public class OfficeApp {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    TioApplication.run(OfficeApp.class, new WebHelloConfig(), args);
    long end = System.currentTimeMillis();
    System.out.println((end - start) + "ms");
  }
}
