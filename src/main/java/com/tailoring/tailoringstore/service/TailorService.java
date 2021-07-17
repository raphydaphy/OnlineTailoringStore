package com.tailoring.tailoringstore.service;

import com.tailoring.tailoringstore.model.TailorShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tailorService")
public class TailorService {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public TailorShop getShop(String tailorUsername) {
    String sql = "SELECT * from tailorShops WHERE tailorUsername = ?";
    try {
      List<TailorShop> shops = jdbcTemplate.query(sql, new Object[]{tailorUsername}, new TailorShop.TailorShopRowMapper());
      return shops.size() == 0 ? null : shops.get(0);
    } catch (Exception e) {
      System.err.println("Failed to get " + tailorUsername + "'s tailor shop: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  public boolean updateShopDetails(TailorShop shop) {
    TailorShop existing = getShop(shop.getTailorUsername());
    String sql = "UPDATE tailorShops SET shopName = ?, shopAddress = ?, shopContactNumber = ?, shopWorkingHours = ?, availableServices = ?, courierAvailable = ? WHERE tailorUsername = ?";
    if (existing == null) {
      sql = "INSERT INTO tailorShops (shopName, shopAddress, shopContactNumber, shopWorkingHours, availableServices, courierAvailable, tailorUsername) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    try {
      jdbcTemplate.update(sql, shop.getName(), shop.getAddress(), shop.getContactNumber(), shop.getWorkingHours(), shop.getAvailableServices(), shop.isCourierAvailable(), shop.getTailorUsername());
      return true;
    } catch (Exception e) {
      System.err.println("Failed to update " + shop.getTailorUsername() + "'s tailor shop: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }
}
