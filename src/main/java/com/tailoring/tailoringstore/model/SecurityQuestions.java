package com.tailoring.tailoringstore.model;

import java.util.Arrays;
import java.util.List;

public class SecurityQuestions {
  private String username;
  private String email;
  private String contactNumber;

  private String newPassword;

  private SecurityQuestion firstQuestion;
  private SecurityQuestion secondQuestion;
  private SecurityQuestion thirdQuestion;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public SecurityQuestion getFirstQuestion() {
    return firstQuestion;
  }

  public void setFirstQuestion(SecurityQuestion firstQuestion) {
    this.firstQuestion = firstQuestion;
  }

  public SecurityQuestion getSecondQuestion() {
    return secondQuestion;
  }

  public void setSecondQuestion(SecurityQuestion secondQuestion) {
    this.secondQuestion = secondQuestion;
  }

  public SecurityQuestion getThirdQuestion() {
    return thirdQuestion;
  }

  public void setThirdQuestion(SecurityQuestion thirdQuestion) {
    this.thirdQuestion = thirdQuestion;
  }

  public List<SecurityQuestion> getQuestionList() {
    return Arrays.asList(firstQuestion, secondQuestion, thirdQuestion);
  }
}
