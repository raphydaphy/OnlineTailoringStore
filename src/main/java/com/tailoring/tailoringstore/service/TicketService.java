package com.tailoring.tailoringstore.service;

import com.tailoring.tailoringstore.model.Ticket;
import com.tailoring.tailoringstore.model.User;
import com.tailoring.tailoringstore.struct.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service("ticketService")
public class TicketService {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public boolean createTicket(Ticket ticket) {
    String sql = "INSERT INTO tickets (issue, description, username) VALUES (?, ?, ?)";

    try {
      jdbcTemplate.update(sql, ticket.getIssue(), ticket.getDescription(), ticket.getUser().getUsername());
      return true;
    } catch (Exception e) {
      System.err.println("Failed to create ticket: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean closeTicket(int ticketId) {
    String sql = "UPDATE tickets SET closed = TRUE WHERE ticketID = ?";
    try {
      jdbcTemplate.update(sql, ticketId);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to create ticket: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public Ticket getTicket(int ticketId) {
    try {
      List<Ticket> tickets = jdbcTemplate.query("SELECT t.*, u.* from tickets t INNER JOIN users u ON t.username = u.username WHERE t.ticketId = ?", new Object[] {ticketId}, new Ticket.TicketRowMapper());
      if (tickets.size() == 0) return null;
      return tickets.get(0);
    } catch (Exception e) {
      System.err.println("Failed to get ticket #" + ticketId + ": " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  public List<Ticket> getTickets() {
    try {
      return jdbcTemplate.query("SELECT t.*, u.* from tickets t INNER JOIN users u ON t.username = u.username WHERE t.closed = FALSE ORDER BY t.ticketDate", new Ticket.TicketRowMapper());
    } catch (Exception e) {
      System.err.println("Failed to get tickets: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<Ticket>();
    }
  }

  public List<Ticket> getTicketsByUser(User user) {
    try {
      List<Ticket> tickets = jdbcTemplate.query("SELECT * from tickets WHERE username = ? AND closed = FALSE ORDER BY ticketDate", new Object[] {user.getUsername()}, new Ticket.TicketRowMapper());
      tickets.forEach((ticket) -> ticket.setUser(user));
      return tickets;
    } catch (Exception e) {
      System.err.println("Failed to get tickets: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<Ticket>();
    }
  }

  public void addTicketsToModel(User user, ModelMap model) {
    List<Ticket> tickets = user.isAdmin() ? getTickets() : getTicketsByUser(user);
    model.put("tickets", tickets);
  }
}
