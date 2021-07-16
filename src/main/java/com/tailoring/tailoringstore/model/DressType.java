package com.tailoring.tailoringstore.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DressType {
  private int dressTypeId;
  private String name;

  private int subcategoryId;
  private String subcategoryName;

  public int getDressTypeId() {
    return dressTypeId;
  }

  public void setDressTypeId(int dressTypeId) {
    this.dressTypeId = dressTypeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSubcategoryId() {
    return subcategoryId;
  }

  public void setSubcategoryId(int subcategoryId) {
    this.subcategoryId = subcategoryId;
  }

  public String getSubcategoryName() {
    return subcategoryName;
  }

  public void setSubcategoryName(String subcategoryName) {
    this.subcategoryName = subcategoryName;
  }

  public static class DressTypeRowMapper implements RowMapper<DressType> {
    @Override
    public DressType mapRow(ResultSet rs, int rowNum) throws SQLException {
      DressType dressType = new DressType();
      dressType.setDressTypeId(rs.getInt("dressTypeId"));
      dressType.setName(rs.getString("dressTypeName"));

      dressType.setSubcategoryId(rs.getInt("subcategoryId"));
      dressType.setSubcategoryName(rs.getString("subcategoryName"));
      return dressType;
    }
  }
}
