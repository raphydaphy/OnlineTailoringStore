package com.tailoring.tailoringstore.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecurityQuestions {
  private SecurityQuestion firstQuestion;
  private SecurityQuestion secondQuestion;
  private SecurityQuestion thirdQuestion;

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
