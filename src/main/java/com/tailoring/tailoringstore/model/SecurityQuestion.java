package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SecurityQuestion {

  private String username;
  private int promptId;

  private String question;
  private String answer;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getPromptId() {
    return promptId;
  }

  public void setPromptId(int promptId) {
    this.promptId = promptId;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public static class SecurityQuestionRowMapper implements RowMapper<SecurityQuestion> {
    @Override
    public SecurityQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
      SecurityQuestion prompt = new SecurityQuestion();
      prompt.setUsername(rs.getString("username"));
      prompt.setPromptId(rs.getInt("promptId"));
      prompt.setQuestion(rs.getString("question"));
      prompt.setAnswer(rs.getString("answer"));
      return prompt;
    }
  }

}
