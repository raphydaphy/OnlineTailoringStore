package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Payment {
  private int orderId;
  private String cardName;
  private String cardNumber;
  private String cardExpiry;
  private String cardCvv;

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public String getCardName() {
    return cardName;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getCardExpiry() {
    return cardExpiry;
  }

  public void setCardExpiry(String cardExpiry) {
    this.cardExpiry = cardExpiry;
  }

  public String getCardCvv() {
    return cardCvv;
  }

  public void setCardCvv(String cardCvv) {
    this.cardCvv = cardCvv;
  }

  public static class PaymentRowMapper implements RowMapper<Payment> {
    @Override
    public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
      Payment payment = new Payment();
      payment.setOrderId(rs.getInt("orderId"));
      payment.setCardName(rs.getString("cardName"));
      payment.setCardNumber(rs.getString("cardNumber"));
      payment.setCardCvv(rs.getString("cardCvv"));
      payment.setCardExpiry(rs.getString("cardExpiry"));
      return payment;
    }
  }
}
