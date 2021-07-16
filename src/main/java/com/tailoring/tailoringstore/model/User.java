package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class User {
  private String username;
  private String password;
  private String passwordHash;

  private String firstName;
  private String lastName;

  private String email;
  private String contactNumber;
  private boolean isTailor;

  private String dateOfBirth;
  private String gender;

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

  public String getPasswordHash() {
    return this.passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public boolean getIsTailor() {
    return isTailor;
  }

  public void setIsTailor(boolean isTailor) {
    this.isTailor = isTailor;
  }

  public boolean isTailor() {
    return getIsTailor();
  }
  public boolean isCustomer() {
    return !isTailor();
  }

  @Override
  public String toString() {
    return "User [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", gender="
      + gender + ", email=" + email + ", contactNumber=" + contactNumber + ", isTailor=" + isTailor + "]";
  }

  public static class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User user = new User();
      user.setUsername(rs.getString("username"));
      user.setPasswordHash(rs.getString("passwordHash"));
      user.setFirstName(rs.getString("firstName"));
      user.setLastName(rs.getString("lastName"));
      user.setDateOfBirth(rs.getString("dateOfBirth"));
      user.setGender(rs.getString("gender"));
      user.setEmail(rs.getString("email"));
      user.setContactNumber(rs.getString("contactNumber"));
      user.setIsTailor(rs.getBoolean("isTailor"));
      return user;
    }
  }
}
