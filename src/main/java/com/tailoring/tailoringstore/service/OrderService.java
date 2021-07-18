package com.tailoring.tailoringstore.service;

import com.tailoring.tailoringstore.model.Order;
import com.tailoring.tailoringstore.model.Payment;
import com.tailoring.tailoringstore.model.Review;
import com.tailoring.tailoringstore.model.User;
import com.tailoring.tailoringstore.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service("orderService")
public class OrderService {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public boolean placeOrder(Order order) {
    String sql = "INSERT INTO orders (customerUsername, tailorUsername, patternId, orderStatusId, topFabric, topMaterial, topDuration, topLength, topQuantity, neck, waist, ";
    sql += "chest, shoulderLength, bottomFabric, bottomMaterial, bottomDuration, bottomLength, bottomQuantity, hip, kneeLength, orderNotes, expectedDeliveryDate, courier) VALUES ";
    sql += "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try {
      jdbcTemplate.update(
        sql, order.getCustomerUsername(), order.getTailorUsername(), order.getPatternId(), 1, order.getTopFabric(), order.getTopMaterial(),
        order.getTopDuration(), order.getTopLength(), order.getTopQuantity(), order.getNeck(), order.getWaist(), order.getChest(), order.getShoulderLength(),
        order.getBottomFabric(), order.getBottomMaterial(), order.getBottomDuration(), order.getBottomLength(), order.getBottomQuantity(), order.getHip(),
        order.getKneeLength(), order.getOrderNotes(), Helper.mysqlDate(order.getExpectedDeliveryDate()), order.isCourier()
      );
      return true;
    } catch (Exception e) {
      System.err.println("Failed to create order: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public List<Order> getOrders(String customerUsername, String tailorUsername, int orderStatusId) {
    String sql = "SELECT o.*, s.orderStatus, p.`name` AS patternName, r.review, z.paid FROM orders o INNER JOIN orderStatus s ON o.orderStatusId = s.orderStatusId ";
    sql += "INNER JOIN patterns p ON o.patternID = p.patternId LEFT JOIN reviews r ON o.orderId = r.orderId LEFT JOIN payments z ON o.orderId = z.orderId ";

    List<Object> args = new ArrayList<>();

    if (customerUsername != null) {
      sql += "WHERE o.customerUsername = ? ";
      args.add(customerUsername);
    } else if (tailorUsername != null) {
      sql += "WHERE o.tailorUsername = ? ";
      args.add(tailorUsername);
    }

    if (orderStatusId > 0) {
      if (args.size() > 0) sql += "AND o.orderStatusId = ? ";
      else sql += "WHERE o.orderStatusId = ? ";
      args.add(orderStatusId);
    }

    try {
      return jdbcTemplate.query(sql, args.toArray(), new Order.OrderRowMapper(true));
    } catch (Exception e) {
      System.err.println("Failed to get orders: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public void addOrdersToModel(User user, ModelMap model) {
    model.put("newOrders", getOrders(user.isCustomer() ? user.getUsername() : null, user.isTailor() ? user.getUsername() : null, 1));
    model.put("activeOrders", getOrders(user.isCustomer() ? user.getUsername() : null, user.isTailor() ? user.getUsername() : null, 2));
    model.put("completedOrders", getOrders(user.isCustomer() ? user.getUsername() : null, user.isTailor() ? user.getUsername() : null, 3));
  }

  public Order getOrder(int orderId) {
    String sql = "SELECT o.*, s.orderStatus, p.`name` AS patternName, r.review, z.paid FROM orders o INNER JOIN orderStatus s ON o.orderStatusId = s.orderStatusId ";
    sql += "INNER JOIN patterns p ON o.patternID = p.patternId LEFT JOIN reviews r ON o.orderId = r.orderId LEFT JOIN payments z ON o.orderId = z.orderId WHERE o.orderId = ? ";

    try {
      List<Order> orders = jdbcTemplate.query(sql, new Object[]{orderId}, new Order.OrderRowMapper(true));
      return orders.size() == 0 ? null : orders.get(0);
    } catch (Exception e) {
      System.err.println("Failed to get order # " + orderId + ": " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  public boolean deleteOrder(int orderId) {
    String sql = "DELETE FROM orders WHERE orderId = ?";
    try {
      jdbcTemplate.update(sql, orderId);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to delete order #" + orderId + ": " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean acceptOrder(Order order) {
    String sql = "UPDATE orders SET expectedDeliveryDate = ?, orderStatusId = ?, amount = ?, topFabric = ?, topMaterial = ?, topDuration = ?, ";
    sql += "topLength = ?, topQuantity = ?, neck = ?, waist = ?, chest = ?, shoulderLength = ?, bottomFabric = ?, bottomMaterial = ?, bottomDuration = ?, ";
    sql += "bottomLength = ?, bottomQuantity = ?, hip = ?, kneeLength = ? WHERE orderId = ?";
    try {
      jdbcTemplate.update(
        sql, Helper.mysqlDate(order.getExpectedDeliveryDate()), 2, order.getAmount(), order.getTopFabric(), order.getTopMaterial(), order.getTopDuration(),
        order.getTopLength(), order.getTopQuantity(), order.getNeck(), order.getWaist(), order.getChest(), order.getShoulderLength(), order.getBottomFabric(),
        order.getBottomMaterial(), order.getBottomDuration(), order.getBottomLength(), order.getBottomQuantity(), order.getHip(), order.getKneeLength(), order.getOrderId()
      );
      return true;
    } catch (Exception e) {
      System.err.println("Failed to delete update #" + order.getOrderId() + ": " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean updateOrder(Order order) {
    String sql = "UPDATE orders SET expectedDeliveryDate = ?, amount = ?, topFabric = ?, topMaterial = ?, topDuration = ?, ";
    sql += "topLength = ?, topQuantity = ?, neck = ?, waist = ?, chest = ?, shoulderLength = ?, bottomFabric = ?, bottomMaterial = ?, bottomDuration = ?, ";
    sql += "bottomLength = ?, bottomQuantity = ?, hip = ?, kneeLength = ? WHERE orderId = ?";
    try {
      jdbcTemplate.update(
        sql, Helper.mysqlDate(order.getExpectedDeliveryDate()), order.getAmount(), order.getTopFabric(), order.getTopMaterial(), order.getTopDuration(),
        order.getTopLength(), order.getTopQuantity(), order.getNeck(), order.getWaist(), order.getChest(), order.getShoulderLength(), order.getBottomFabric(),
        order.getBottomMaterial(), order.getBottomDuration(), order.getBottomLength(), order.getBottomQuantity(), order.getHip(), order.getKneeLength(), order.getOrderId()
      );
      return true;
    } catch (Exception e) {
      System.err.println("Failed to update order #" + order.getOrderId() + ": " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean completeOrder(int orderId) {
    String sql = "UPDATE orders SET deliveredDate = CURRENT_DATE, orderStatusId = 3 WHERE orderId = ?";
    try {
      jdbcTemplate.update(sql, orderId);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to complete order #" + orderId + ": " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  // Get the first unreviewed complete order, if any
  public Order getUnreviewedOrder(User user) {
    String sql = "SELECT o.*, s.orderStatus, p.`name` AS patternName FROM orders o INNER JOIN orderStatus s ON o.orderStatusId = s.orderStatusId ";
    sql += "INNER JOIN patterns p ON o.patternID = p.patternId WHERE o.customerUsername = ? AND o.orderStatusId = 3 AND o.orderId NOT IN ";
    sql += "(SELECT orderId FROM reviews) ";
    try {
      List<Order> orders = jdbcTemplate.query(sql, new Object[]{user.getUsername()}, new Order.OrderRowMapper(false));
      return orders.size() > 0 ? orders.get(0) : null;
    } catch (Exception e) {
      System.err.println("Failed to get unreviewed orders for " + user.getUsername() + ": " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  public Review getReview(Order order) {
    String sql = "SELECT * from reviews WHERE orderId = ?";
    try {
      List<Review> reviews = jdbcTemplate.query(sql, new Object[]{order.getOrderId()}, new Review.ReviewRowMapper());
      return reviews.size() > 0 ? reviews.get(0) : null;
    } catch (Exception e) {
      System.err.println("Failed to get reviews for order #" + order.getOrderId() + ": " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  public boolean addReview(Review review) {
    String sql = "INSERT INTO reviews (orderId, review) VALUES (?, ?)";
    try {
      jdbcTemplate.update(sql, review.getOrderId(), review.getReview());
      return true;
    } catch (Exception e) {
      System.err.println("Failed to review order #" + review.getOrderId() + ": " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public boolean makePayment(Payment payment) {
    String sql = "INSERT INTO payments (orderId, cardName, cardNumber, cardExpiry, cardCvv, paid) VALUES (?, ?, ?, ?, ?, ?)";
    try {
      jdbcTemplate.update(sql, payment.getOrderId(), payment.getCardName(), payment.getCardNumber(), payment.getCardExpiry(), payment.getCardCvv(), true);
      return true;
    } catch (Exception e) {
      System.err.println("Failed to pay for order #" + payment.getOrderId() + ": " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }
}
