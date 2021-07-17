package com.tailoring.tailoringstore.service;

import com.tailoring.tailoringstore.model.SecurityQuestion;
import com.tailoring.tailoringstore.model.SecurityQuestionPrompt;
import com.tailoring.tailoringstore.model.SecurityQuestions;
import com.tailoring.tailoringstore.model.User;
import com.tailoring.tailoringstore.struct.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.ArrayList;
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

  public User getUserWithEmailOrNumber(String email, String contactNumber) {
    try {
      List<User> user = jdbcTemplate.query("SELECT * from users WHERE email = ? OR contactNumber = ?", new Object[]{email, contactNumber}, new User.UserRowMapper());
      if (user.size() == 0) return null;

      return user.get(0);
    } catch (Exception e) {
      System.err.println("Failed to get user from email/number: " + e.getMessage());
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

  public boolean resetPassword(String username, String newPassword) {
    String sql = "UPDATE users SET passwordHash = ? WHERE username = ?";

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, new SecureRandom());
    String passwordHash = encoder.encode(newPassword);

    try {
      jdbcTemplate.update(sql, passwordHash, username);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to reset " + username + "'s password: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public UserResponse login(User user) {
    User existingUser = getUser(user.getUsername());

    if (existingUser == null) {
      return new UserResponse("username");
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, new SecureRandom());
    if (!encoder.matches(user.getPassword(), existingUser.getPasswordHash())) {
      return new UserResponse("password");
    }

    return new UserResponse(existingUser);
  }

  public User addUserToModel(ModelMap model, HttpServletRequest req) {
    String username = (String) req.getSession().getAttribute("user");

    if (username == null) {
      model.put("error", "Please log in to continue");
      return null;
    }

    User user = getUser(username);
    if (user == null) {
      model.put("error", "Invalid login.");
      return null;
    }

    model.put("user", user);
    return user;
  }

  public List<SecurityQuestionPrompt> getSecurityQuestionPrompts() {
    try {
      return jdbcTemplate.query("SELECT * FROM securityQuestionPrompts", new SecurityQuestionPrompt.SecurityQuestionPromptRowMapper());
    } catch (Exception e) {
      System.err.println("Failed to get security question prompts: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public List<SecurityQuestion> getSecurityQuestions(User user) {
    String sql = "SELECT s.*, p.question FROM securityQuestions s INNER JOIN securityQuestionPrompts p ON s.promptId = p.promptID WHERE s.username = ?";
    try {
      return jdbcTemplate.query(sql, new Object[] {user.getUsername()}, new SecurityQuestion.SecurityQuestionRowMapper());
    } catch (Exception e) {
      System.err.println("Failed to get " + user.getUsername() + "'s security questions: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public boolean addSecurityQuestion(User user, SecurityQuestion question) {
    String sql = "INSERT INTO securityQuestions (username, promptId, answer) VALUES (?, ?, ?)";
    try {
      jdbcTemplate.update(sql, user.getUsername(), question.getPromptId(), question.getAnswer());
      return true;
    } catch (Exception e) {
      System.err.println("Failed to add security question: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }
}
