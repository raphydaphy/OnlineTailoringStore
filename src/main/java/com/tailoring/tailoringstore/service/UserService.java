package com.tailoring.tailoringstore.service;

import com.tailoring.tailoringstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Service("userService")
public class UserService {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public boolean addUser(User user) {
    String sql = "INSERT INTO users (username, password_hash, firstName, lastName, dateOfBirth, gender, email, contactNumber, isTailor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, new SecureRandom());
    String passwordHash = encoder.encode(user.getPassword());

    try {
      jdbcTemplate.update(sql, user.getUsername(), passwordHash, user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getGender(), user.getEmail(), user.getContactNumber(), user.getIsTailor());
      return true;
    } catch (Exception e) {
      System.err.println("Failed to create user: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }
}
