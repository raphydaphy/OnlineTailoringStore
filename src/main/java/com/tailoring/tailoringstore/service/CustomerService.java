package com.tailoring.tailoringstore.service;

import com.tailoring.tailoringstore.model.Order;
import com.tailoring.tailoringstore.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("customerService")
public class CustomerService {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public boolean placeOrder(Order order) {
    String sql = "INSERT INTO orders (customerUsername, tailorUsername, patternId, orderStatusId, topFabric, topMaterial, topDuration, topLength, topQuantity, neck, waist, ";
    sql += "chest, shoulderLength, bottomFabric, bottomMaterial, bottomDuration, bottomLength, bottomQuantity, hip, kneeLength, orderNotes, expectedDeliveryDate) VALUES ";
    sql += "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try {
      jdbcTemplate.update(
        sql, order.getCustomerUsername(), order.getTailorUsername(), order.getPatternId(), 1, order.getTopFabric(), order.getTopMaterial(),
        order.getTopDuration(), order.getTopLength(), order.getTopQuantity(), order.getNeck(), order.getWaist(), order.getChest(), order.getShoulderLength(),
        order.getBottomFabric(), order.getBottomMaterial(), order.getBottomDuration(), order.getBottomLength(), order.getBottomQuantity(), order.getHip(),
        order.getKneeLength(), order.getOrderNotes(), Helper.mysqlDate(order.getExpectedDeliveryDate())
      );
      return true;
    } catch (Exception e) {
      System.err.println("Failed to create order: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public List<Order> getOrders(String customerUsername, String tailorUsername) {
    String sql = "SELECT o.*, s.orderStatus, p.`name` AS patternName FROM orders o INNER JOIN orderStatus s ON o.orderStatusId = s.orderStatusId ";
    sql += "INNER JOIN patterns p ON o.patternID = p.patternId ";

    List<Object> args = new ArrayList<>();

    if (customerUsername != null) {
      sql += "WHERE customerUsername = ? ";
      args.add(customerUsername);
    }

    if (tailorUsername != null) {
      sql += "WHERE tailorUsername = ? ";
      args.add(tailorUsername);
    }

    try {
      return jdbcTemplate.query(sql, args.toArray(), new Order.OrderRowMapper());
    } catch (Exception e) {
      System.err.println("Failed to get orders: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public Order getOrder(int orderId) {
    String sql = "SELECT o.*, s.orderStatus, p.`name` AS patternName FROM orders o INNER JOIN orderStatus s ON o.orderStatusId = s.orderStatusId ";
    sql += "INNER JOIN patterns p ON o.patternID = p.patternId WHERE orderId = ?";

    try {
      List<Order> orders = jdbcTemplate.query(sql, new Object[]{orderId}, new Order.OrderRowMapper());
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
}
