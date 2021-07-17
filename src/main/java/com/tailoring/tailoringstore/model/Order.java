package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Order {
  private int orderId;

  private String customerUsername;
  private String tailorUsername;

  private int patternId;
  private String patternName;

  private String placedDate;
  private String expectedDeliveryDate;
  private String deliveredDate;

  private String orderStatusId;
  private String orderStatus;

  private float amount;
  private String orderNotes;

  private String topFabric;
  private String topMaterial;
  private String topDuration;
  private String topLength;
  private String topQuantity;
  private String neck;
  private String waist;
  private String chest;
  private String shoulderLength;
  private String bottomFabric;
  private String bottomMaterial;
  private String bottomDuration;
  private String bottomLength;
  private String bottomQuantity;
  private String hip;
  private String kneeLength;

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public String getCustomerUsername() {
    return customerUsername;
  }

  public void setCustomerUsername(String customerUsername) {
    this.customerUsername = customerUsername;
  }

  public String getTailorUsername() {
    return tailorUsername;
  }

  public void setTailorUsername(String tailorUsername) {
    this.tailorUsername = tailorUsername;
  }

  public int getPatternId() {
    return patternId;
  }

  public void setPatternId(int patternId) {
    this.patternId = patternId;
  }

  public String getPatternName() {
    return patternName;
  }

  public void setPatternName(String patternName) {
    this.patternName = patternName;
  }

  public String getPlacedDate() {
    return placedDate;
  }

  public void setPlacedDate(String placedDate) {
    this.placedDate = placedDate;
  }

  public String getExpectedDeliveryDate() {
    return expectedDeliveryDate;
  }

  public void setExpectedDeliveryDate(String expectedDeliveryDate) {
    this.expectedDeliveryDate = expectedDeliveryDate;
  }

  public String getDeliveredDate() {
    return deliveredDate;
  }

  public void setDeliveredDate(String deliveredDate) {
    this.deliveredDate = deliveredDate;
  }

  public String getOrderStatusId() {
    return orderStatusId;
  }

  public void setOrderStatusId(String orderStatusId) {
    this.orderStatusId = orderStatusId;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public float getAmount() {
    return amount;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }

  public String getTopFabric() {
    return topFabric;
  }

  public void setTopFabric(String topFabric) {
    this.topFabric = topFabric;
  }

  public String getTopMaterial() {
    return topMaterial;
  }

  public void setTopMaterial(String topMaterial) {
    this.topMaterial = topMaterial;
  }

  public String getTopDuration() {
    return topDuration;
  }

  public void setTopDuration(String topDuration) {
    this.topDuration = topDuration;
  }

  public String getTopLength() {
    return topLength;
  }

  public void setTopLength(String topLength) {
    this.topLength = topLength;
  }

  public String getTopQuantity() {
    return topQuantity;
  }

  public void setTopQuantity(String topQuantity) {
    this.topQuantity = topQuantity;
  }

  public String getNeck() {
    return neck;
  }

  public void setNeck(String neck) {
    this.neck = neck;
  }

  public String getWaist() {
    return waist;
  }

  public void setWaist(String waist) {
    this.waist = waist;
  }

  public String getChest() {
    return chest;
  }

  public void setChest(String chest) {
    this.chest = chest;
  }

  public String getShoulderLength() {
    return shoulderLength;
  }

  public void setShoulderLength(String shoulderLength) {
    this.shoulderLength = shoulderLength;
  }

  public String getBottomFabric() {
    return bottomFabric;
  }

  public void setBottomFabric(String bottomFabric) {
    this.bottomFabric = bottomFabric;
  }

  public String getBottomMaterial() {
    return bottomMaterial;
  }

  public void setBottomMaterial(String bottomMaterial) {
    this.bottomMaterial = bottomMaterial;
  }

  public String getBottomDuration() {
    return bottomDuration;
  }

  public void setBottomDuration(String bottomDuration) {
    this.bottomDuration = bottomDuration;
  }

  public String getBottomLength() {
    return bottomLength;
  }

  public void setBottomLength(String bottomLength) {
    this.bottomLength = bottomLength;
  }

  public String getBottomQuantity() {
    return bottomQuantity;
  }

  public void setBottomQuantity(String bottomQuantity) {
    this.bottomQuantity = bottomQuantity;
  }

  public String getHip() {
    return hip;
  }

  public void setHip(String hip) {
    this.hip = hip;
  }

  public String getKneeLength() {
    return kneeLength;
  }

  public void setKneeLength(String kneeLength) {
    this.kneeLength = kneeLength;
  }

  public String getOrderNotes() {
    return orderNotes;
  }

  public void setOrderNotes(String orderNotes) {
    this.orderNotes = orderNotes;
  }

  public static class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
      Order order = new Order();
      order.setOrderId(rs.getInt("orderId"));
      order.setCustomerUsername(rs.getString("customerUsername"));
      order.setTailorUsername(rs.getString("tailorUsername"));

      order.setPatternId(rs.getInt("patternId"));
      order.setPatternName(rs.getString("patternName"));

      order.setPlacedDate(rs.getString("placedDate"));
      order.setExpectedDeliveryDate(rs.getString("expectedDeliveryDate"));
      order.setDeliveredDate(rs.getString("deliveredDate"));

      order.setOrderStatusId(rs.getString("orderStatusId"));
      order.setOrderStatusId(rs.getString("orderStatus"));

      order.setOrderNotes(rs.getString("orderNotes"));

      order.setTopFabric(rs.getString("topFabric"));
      order.setTopMaterial(rs.getString("topMaterial"));
      order.setTopDuration(rs.getString("topDuration"));
      order.setTopLength(rs.getString("topLength"));
      order.setTopQuantity(rs.getString("topQuantity"));

      order.setBottomFabric(rs.getString("bottomFabric"));
      order.setBottomMaterial(rs.getString("bottomMaterial"));
      order.setBottomDuration(rs.getString("bottomDuration"));
      order.setBottomLength(rs.getString("bottomLength"));
      order.setBottomQuantity(rs.getString("bottomQuantity"));

      order.setNeck(rs.getString("neck"));
      order.setWaist(rs.getString("waist"));
      order.setChest(rs.getString("chest"));
      order.setHip(rs.getString("hip"));

      order.setShoulderLength(rs.getString("shoulderLength"));
      order.setKneeLength(rs.getString("kneeLength"));

      return order;
    }
  }
}
