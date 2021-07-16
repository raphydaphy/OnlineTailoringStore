package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Ticket {
  private int ticketId;
  private String issue;

  private String description;
  private String date;

  private boolean closed;

  private User user;

  private String response;
  private String responseUsername;

  public int getTicketId() {
    return ticketId;
  }

  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }

  public String getIssue() {
    return issue;
  }

  public void setIssue(String issue) {
    this.issue = issue;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public boolean getClosed() {
    return closed;
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public String getResponseUsername() {
    return responseUsername;
  }

  public void setResponseUsername(String responseUsername) {
    this.responseUsername = responseUsername;
  }

  @Override
  public String toString() {
    return "Ticket [issue=" + issue + ", description=" + description + ", date=" + date + ", closed=" + closed +
      ", response=" + response + ", responseUsername=" + responseUsername + ", user=" + user + "]";
  }

  public static class TicketRowMapper implements RowMapper<Ticket> {
    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
      Ticket ticket = new Ticket();
      ticket.setTicketId(rs.getInt("ticketId"));
      ticket.setIssue(rs.getString("issue"));
      ticket.setDescription(rs.getString("description"));
      ticket.setDate(rs.getString("ticketDate"));
      ticket.setClosed(rs.getBoolean("closed"));
      ticket.setResponse(rs.getString("response"));
      ticket.setResponseUsername(rs.getString("responseUsername"));
      try {
        ticket.setUser(new User.UserRowMapper().mapRow(rs, rowNum));
      } catch (SQLException err) {
        ticket.setUser(new User());
      }
      return ticket;
    }
  }
}
