package com.tailoring.tailoringstore.struct;

import com.tailoring.tailoringstore.model.User;

public class UserResponse {
  private String error = null;
  private User user = null;

  public UserResponse(String error) {
    this.error = error;
  }

  public UserResponse(User user) {
    this.user = user;
  }

  public boolean hasError() {
    return error != null;
  }

  public String getError() {
    return this.error;
  }

  public User getUser() {
    return this.user;
  }
}
