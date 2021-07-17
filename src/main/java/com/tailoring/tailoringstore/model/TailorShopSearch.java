package com.tailoring.tailoringstore.model;

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
}
