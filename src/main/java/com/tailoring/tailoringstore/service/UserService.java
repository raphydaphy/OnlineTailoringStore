package com.tailoring.tailoringstore.service;

import com.tailoring.tailoringstore.model.User;
import com.tailoring.tailoringstore.struct.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.List;

@Service("userService")
public class UserService {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public User getUser(String username) {
    try {
      List<User> user = jdbcTemplate.query("SELECT * from users WHERE username = ?", new Object[]{username}, new User.UserRowMapper());
      if (user.size() == 0) return null;

      return user.get(0);
    } catch (Exception e) {
      System.err.println("Failed to get user '" + username + "': " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  public UserResponse addUser(User user) {
    User existingUser = getUser(user.getUsername());
    if (existingUser != null) {
      return new UserResponse("That username is already registered");
    }

    String sql = "INSERT INTO users (username, passwordHash, firstName, lastName, dateOfBirth, gender, email, contactNumber, category) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, new SecureRandom());
    String passwordHash = encoder.encode(user.getPassword());

    try {
      jdbcTemplate.update(sql, user.getUsername(), passwordHash, user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getGender(), user.getEmail(), user.getContactNumber(), user.getCategory());
      user.setPassword(null);
      user.setPasswordHash(passwordHash);
      return new UserResponse(user);
    } catch (Exception e) {
      System.err.println("Failed to create user: " + e.getMessage());
      e.printStackTrace();
      return new UserResponse("Failed to create user");
    }
  }

  public UserResponse login(User user) {
    User existingUser = getUser(user.getUsername());

    if (existingUser == null) {
      return new UserResponse("Invalid username");
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, new SecureRandom());
    if (!encoder.matches(user.getPassword(), existingUser.getPasswordHash())) {
      return new UserResponse("Incorrect password");
    }

    return new UserResponse(existingUser);
  }
}
