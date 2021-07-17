package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Pattern {
  private int patternId;
  private String tailorUsername;

  private Category category;
  private DressType dressType;

  private String name;
  private String description;
  private float cost;
  private byte[] imageData;

  private MultipartFile imageUpload;

  public int getPatternId() {
    return patternId;
  }

  public void setPatternId(int patternId) {
    this.patternId = patternId;
  }

  public String getTailorUsername() {
    return tailorUsername;
  }

  public void setTailorUsername(String tailorUsername) {
    this.tailorUsername = tailorUsername;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public DressType getDressType() {
    return dressType;
  }

  public void setDressType(DressType dressType) {
    this.dressType = dressType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public float getCost() {
    return cost;
  }

  public void setCost(float cost) {
    this.cost = cost;
  }

  public byte[] getImageData() {
    return imageData;
  }

  public void setImageData(byte[] imageData) {
    this.imageData = imageData;
  }

  public MultipartFile getImageUpload() {
    return imageUpload;
  }

  public void setImageUpload(MultipartFile imageUpload) {
    this.imageUpload = imageUpload;
  }

  public static class PatternRowMapper implements RowMapper<Pattern> {
    private boolean extras = false;

    public PatternRowMapper(boolean extras) {
      this.extras = extras;
    }

    @Override
    public Pattern mapRow(ResultSet rs, int rowNum) throws SQLException {
      Pattern pattern = new Pattern();
      pattern.setPatternId(rs.getInt("patternId"));
      pattern.setTailorUsername(rs.getString("tailorUsername"));
      pattern.setName(rs.getString("name"));
      pattern.setDescription(rs.getString("description"));
      pattern.setCost(rs.getFloat("cost"));
      pattern.setImageData(rs.getBytes("imageData"));

      pattern.setCategory(new Category());
      pattern.setDressType(new DressType());

      pattern.getCategory().setCategoryId(rs.getInt("categoryId"));
      pattern.getDressType().setDressTypeId(rs.getInt("dressTypeId"));

      if (extras) {
        pattern.getCategory().setName(rs.getString("categoryName"));
        pattern.getDressType().setName(rs.getString("dressTypeName"));

        pattern.getDressType().setSubcategoryId(rs.getInt("subcategoryId"));
        pattern.getDressType().setSubcategoryName(rs.getString("subcategoryName"));
      }
      return pattern;
    }
  }
}
