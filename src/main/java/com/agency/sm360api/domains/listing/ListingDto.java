package com.agency.sm360api.domains.listing;

import java.util.UUID;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ListingDto {

  public UUID id;

  public UUID dealerId;
  
  public String vehicle;

  public Float price;

  @Enumerated(EnumType.STRING)
  public State state;

  
  public ListingDto(UUID dealerId, String vehicle, Float price, State state) {
    this.dealerId = dealerId;
    this.vehicle = vehicle;
    this.price = price;
    this.state = state;
  }
}
