package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TailorShopSearch {
  private Category category;
  private DressType dressType;
  private String area;

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

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }


  public static class AreaRowMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
      return rs.getString("shopAddress");
    }
  }
}
