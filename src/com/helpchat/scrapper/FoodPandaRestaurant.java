package com.helpchat.scrapper;

import java.util.List;


public class FoodPandaRestaurant {

  private String restaurantName;
  private String deliveryTime;
  private String minOrderValue;
  private String deliveryFee;
  private String streetAddress;
  private String addressRegion;
  private String postalCode;
  private List<String> cuisines;

  @Override
  public int hashCode() {
    return this.restaurantName.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    boolean result = false;
    if (object == null || object.getClass() != getClass()) {
      result = false;
    } else {
      FoodPandaRestaurant dishRow = (FoodPandaRestaurant) object;
      if (this.restaurantName.equalsIgnoreCase(dishRow.getRestaurantName())) {
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

  public String getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(String deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

  public String getMinOrderValue() {
    return minOrderValue;
  }

  public void setMinOrderValue(String minOrderValue) {
    this.minOrderValue = minOrderValue;
  }

  public String getDeliveryFee() {
    return deliveryFee;
  }

  public void setDeliveryFee(String deliveryFee) {
    this.deliveryFee = deliveryFee;
  }

  public List<String> getCuisines() {
    return cuisines;
  }

  public void setCuisines(List<String> cuisines) {
    this.cuisines = cuisines;
  }

public String getStreetAddress() {
	return streetAddress;
}

public void setStreetAddress(String streetAddress) {
	this.streetAddress = streetAddress;
}

public String getAddressRegion() {
	return addressRegion;
}

public void setAddressRegion(String addressRegion) {
	this.addressRegion = addressRegion;
}

public String getPostalCode() {
	return postalCode;
}

public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
}

}
