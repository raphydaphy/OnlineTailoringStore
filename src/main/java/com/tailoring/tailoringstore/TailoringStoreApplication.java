package com.tailoring.tailoringstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

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