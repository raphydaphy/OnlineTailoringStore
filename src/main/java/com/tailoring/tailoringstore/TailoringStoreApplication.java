package com.tailoring.tailoringstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages={"com.tailoring.tailoringstore.config", "com.tailoring.tailoringstore.controller", "com.tailoring.tailoringstore.model", "com.tailoring.tailoringstore.service"})
public class TailoringStoreApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(TailoringStoreApplication.class);
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(TailoringStoreApplication.class, args);
  }
}