package com.helpchat.scrapper;

import java.util.Comparator;


public class RestaurantDishRowComparator implements Comparator<RestaurantDishRow> {

  @Override
  public int compare(RestaurantDishRow o1, RestaurantDishRow o2) {
    return o1.getRestaurantName().compareTo(o2.getRestaurantName());
  }

}
