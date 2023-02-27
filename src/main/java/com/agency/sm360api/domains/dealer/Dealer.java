package com.agency.sm360api.domains.dealer;

import java.util.Set;
import java.util.UUID;

import com.agency.sm360api.domains.listing.Listing;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Dealer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(updatable = false, unique = true, nullable = false)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer tierLimit;

  @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL)
  private Set<Listing> listings;

  public Dealer() {
  }

  public Dealer(String name, int tierLimit) {
    this.name = name;
    this.tierLimit = tierLimit;
  }

  public Dealer(UUID id, String name, int tierLimit) {
    this.id = id;
    this.name = name;
    this.tierLimit = tierLimit;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getTierLimit() {
    return tierLimit;
  }

  public void setTierLimit(Integer tierLimit) {
    this.tierLimit = tierLimit;
  }
}
