package com.tailoring.tailoringstore.model;

import org.springframework.stereotype.Component;

@Component
public class User {
  private String firstName;
  private String lastName;

  private String username;
  private String password;

  public User() {
    super();
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstNAme) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
