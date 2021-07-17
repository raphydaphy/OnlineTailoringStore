package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TailorShop {
  private String tailorUsername;
  private String name;
  private String address;
  private String contactNumber;
  private String workingHours;
  private String availableServices;
  private boolean courierAvailable;

  public String getTailorUsername() {
    return tailorUsername;
  }

  public void setTailorUsername(String tailorUsername) {
    this.tailorUsername = tailorUsername;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public String getWorkingHours() {
    return workingHours;
  }

  public void setWorkingHours(String workingHours) {
    this.workingHours = workingHours;
  }

  public String getAvailableServices() {
    return availableServices;
  }

  public void setAvailableServices(String availableServices) {
    this.availableServices = availableServices;
  }

  public boolean isCourierAvailable() {
    return courierAvailable;
  }

  public void setCourierAvailable(boolean courierAvailable) {
    this.courierAvailable = courierAvailable;
  }

  public static class TailorShopRowMapper implements RowMapper<TailorShop> {
    @Override
    public TailorShop mapRow(ResultSet rs, int rowNum) throws SQLException {
      TailorShop shop = new TailorShop();
      shop.setTailorUsername(rs.getString("tailorUsername"));
      shop.setName(rs.getString("shopName"));
      shop.setAddress(rs.getString("shopAddress"));
      shop.setContactNumber(rs.getString("shopContactNumber"));
      shop.setWorkingHours(rs.getString("shopWorkingHours"));
      shop.setAvailableServices(rs.getString("availableServices"));
      shop.setCourierAvailable(rs.getBoolean("courierAvailable"));
      return shop;
    }
  }
}
