package com.tailoring.tailoringstore.service;

import com.tailoring.tailoringstore.model.Pattern;
import com.tailoring.tailoringstore.model.TailorShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

  public List<Pattern> getPatterns(String tailorUsername) {
    String sql = "SELECT p.*, c.categoryName, s.*, d.dressTypeName FROM patterns p INNER JOIN categories c ON p.categoryId = c.categoryId ";
    sql += "INNER JOIN dressTypes d ON p.dressTypeId = d.dressTypeId INNER JOIN subcategories s ON d.subcategoryId = s.subcategoryId WHERE p.tailorUsername = ?";
    try {
      return jdbcTemplate.query(sql, new Object[]{tailorUsername}, new Pattern.PatternRowMapper(true));
    } catch (Exception e) {
      System.err.println("Failed to get " + tailorUsername + "'s patterns: " + e.getMessage());
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public boolean uploadPattern(Pattern pattern) {
    String sql = "INSERT INTO patterns (tailorUsername, categoryId, dressTypeId, `name`, description, cost, imageData) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try {
      jdbcTemplate.update(
        sql, pattern.getTailorUsername(), pattern.getCategory().getCategoryId(), pattern.getDressType().getDressTypeId(),
        pattern.getName(), pattern.getDescription(), pattern.getCost(), pattern.getImageUpload().getBytes()
      );
      return true;
    } catch (Exception e) {
      System.err.println("Failed to upload pattern: " + e.getMessage());
      e.printStackTrace();
      return false;
    }
  }

  public Pattern getPattern(int patternId) {
    String sql = "SELECT p.*, c.categoryName, s.*, d.dressTypeName FROM patterns p INNER JOIN categories c ON p.categoryId = c.categoryId ";
    sql += "INNER JOIN dressTypes d ON p.dressTypeId = d.dressTypeId INNER JOIN subcategories s ON d.subcategoryId = s.subcategoryId WHERE p.patternId = ?";
    try {
      List<Pattern> patterns = jdbcTemplate.query(sql, new Object[]{patternId}, new Pattern.PatternRowMapper(true));
      return patterns.size() == 0 ? null : patterns.get(0);
    } catch (Exception e) {
      System.err.println("Failed to get pattern #" + patternId + ": " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }
}