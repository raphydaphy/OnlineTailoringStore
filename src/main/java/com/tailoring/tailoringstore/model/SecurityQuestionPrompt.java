package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SecurityQuestionPrompt {
  private int promptId;
  private String question;

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

  public static class SecurityQuestionPromptRowMapper implements RowMapper<SecurityQuestionPrompt> {
    @Override
    public SecurityQuestionPrompt mapRow(ResultSet rs, int rowNum) throws SQLException {
      SecurityQuestionPrompt prompt = new SecurityQuestionPrompt();
      prompt.setPromptId(rs.getInt("promptId"));
      prompt.setQuestion(rs.getString("question"));
      return prompt;
    }
  }

}
