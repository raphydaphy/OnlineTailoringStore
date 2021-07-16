package com.tailoring.tailoringstore.controller;

import com.tailoring.tailoringstore.model.Ticket;
import com.tailoring.tailoringstore.model.User;
import com.tailoring.tailoringstore.service.TicketService;
import com.tailoring.tailoringstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TicketController {

  @Autowired
  private TicketService ticketService;

  @Autowired
  private UserService userService;

  @RequestMapping("/tickets")
  public String tickets(@ModelAttribute("user") User loginUser, ModelMap model, HttpServletRequest req) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    ticketService.addTicketsToModel(user, model);
    return "tickets";
  }

  @RequestMapping("/createTicket")
  public String createTicket(
    @ModelAttribute("user") User loginUser, @ModelAttribute("ticket") Ticket ticket,
    ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    if (user.isAdmin()) {
      model.put("error", "Admins are not allowed to open tickets!");
      return "account";
    }

    return "createTicket";
  }

  @RequestMapping(value="/createTicket", method=RequestMethod.POST)
  public String createTicketResponse(
    @ModelAttribute("user") User loginUser, @ModelAttribute("ticket") Ticket ticket,
    BindingResult result, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    if (result.hasErrors()) {
      System.out.println("Result errors: " + result.getAllErrors().toString());
      model.put("error", "Failed to create ticket. Please try again.");
      return "account";
    }

    ticket.setUser(user);
    if (ticketService.createTicket(ticket)) {
      model.put("message", "Your ticket has been created!");
    } else {
      model.put("error", "Failed to create ticket. Please try again.");
    }

    return "account";
  }

  @RequestMapping(value="/closeTicket", method=RequestMethod.GET)
  public String closeTicket(@ModelAttribute("user") User loginUser, @RequestParam String ticketId, ModelMap model, HttpServletRequest req) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";

    int ticketIdNum;
    try {
      ticketIdNum = Integer.parseInt(ticketId);
    } catch (NumberFormatException ex) {
      model.put("error", "Failed to close ticket: Invalid Ticket ID provided");
      ticketService.addTicketsToModel(user, model);
      return "tickets";
    }

    Ticket ticket = ticketService.getTicket(ticketIdNum);
    if (ticket == null) {
      model.put("error", "Failed to close ticket: Ticket not found");
    } else if (!user.isAdmin() && !user.getUsername().equals(ticket.getUser().getUsername())) {
      model.put("error", "Failed to close ticket: You can't close someone else's ticket");
    } else if (ticket.getClosed()) {
      model.put("error", "Failed to close ticket: Ticket was already closed");
    } else {
      ticketService.closeTicket(ticket.getTicketId());
      model.put("message", (user.isAdmin() ? "The" : "Your") + " ticket '" + ticket.getIssue() + "' has been closed");
    }

    ticketService.addTicketsToModel(user, model);
    return "tickets";
  }

  @RequestMapping(value="/ticketRespond", method=RequestMethod.GET)
  public String ticketRespond(@ModelAttribute("user") User loginUser, @RequestParam int ticketId, ModelMap model, HttpServletRequest req) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";


    Ticket ticket = ticketService.getTicket(ticketId);
    String error = null;

    if (ticket == null) {
      error = "Ticket not found";
    } else if (!user.isAdmin()) {
      error = "Only admins can respond to tickets";
    } else if (ticket.getResponse() != null && ticket.getResponse().length() > 0) {
      error = "You can't respond to a ticket more than once";
    } else if (ticket.getClosed()) {
      error = "The ticket has been closed";
    }

    if (error != null) {
      model.put("error", "You can't respond to that ticket: " + error);
      ticketService.addTicketsToModel(user, model);
      return "tickets";
    }

    model.put("ticket", ticket);
    return "ticketRespond";
  }


  @RequestMapping(value="/ticketRespond", method=RequestMethod.POST)
  public String ticketRespondResponse(
    @ModelAttribute("user") User loginUser, @ModelAttribute("ticket") Ticket ticket,
    BindingResult result, ModelMap model, HttpServletRequest req
  ) {
    User user = userService.addUserToModel(model, req);
    if (user == null) return "login";


    if (result.hasErrors()) {
      System.out.println("Result errors: " + result.getAllErrors().toString());
      model.put("error", "Failed to respond to ticket. Please try again.");
      ticketService.addTicketsToModel(user, model);
      return "tickets";
    }

    Ticket storedTicket = ticketService.getTicket(ticket.getTicketId());
    String error = null;

    if (!user.isAdmin()) {
      error = "Only admins can respond to tickets";
    } else if (storedTicket == null) {
      error = "The specified ticket could not be found";
    } else if (storedTicket.getClosed()) {
      error = "The ticket is closed";
    } else if (storedTicket.getResponse() != null && storedTicket.getResponse().length() > 0) {
      error = "You can't respond to a ticket more than once";
    }

    String ticketTitle = "ticket";
    if (storedTicket != null) ticketTitle = storedTicket.getUsername() + "'s ticket";

    if (error != null) {
      model.put("error", "Failed to respond to " + ticketTitle + ": " + error);
      ticketService.addTicketsToModel(user, model);
      return "tickets";
    }

    if (ticketService.respond(ticket.getTicketId(), ticket.getResponse(), user)) {
      model.put("message", "Ticket response sent to " + storedTicket.getUsername() + "!");
    } else {
      model.put("error", "Failed to respond to " + ticketTitle);
    }

    ticketService.addTicketsToModel(user, model);
    return "tickets";
  }
}
