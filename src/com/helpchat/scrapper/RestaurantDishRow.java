package com.helpchat.scrapper;

public class RestaurantDishRow {

  private String restaurantName;
  private String dishCategory;
  private String dishName;
  private String priceVariation;

  @Override
  public int hashCode() {
    return this.restaurantName.hashCode() ^ this.dishCategory.hashCode() ^ this.dishName.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    boolean result = false;
    if (object == null || object.getClass() != getClass()) {
      result = false;
    } else {
      RestaurantDishRow dishRow = (RestaurantDishRow) object;
      if (this.restaurantName.equalsIgnoreCase(dishRow.getRestaurantName())
          && this.dishCategory.equalsIgnoreCase(dishRow.getDishCategory())
          && this.dishName.equalsIgnoreCase(dishRow.getDishName())) {
        result = true;
      }
    }
    return result;
  }

  public String getRestaurantName() {
    return restaurantName;
  }

  public void setRestaurantName(String restaurantName) {
    this.restaurantName = restaurantName;
  }

  public String getDishCategory() {
    return dishCategory;
  }

  public void setDishCategory(String dishCategory) {
    this.dishCategory = dishCategory;
  }

  public String getDishName() {
    return dishName;
  }

  public void setDishName(String dishName) {
    this.dishName = dishName;
  }

  public String getPriceVariation() {
    return priceVariation;
  }

  public void setPriceVariation(String priceVariation) {
    this.priceVariation = priceVariation;
  }

}
