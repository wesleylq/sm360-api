package com.agency.sm360api.domains.listing;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.agency.sm360api.domains.dealer.Dealer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Listing {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", updatable = false, unique = true, nullable = false)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "dealer_id", nullable = false)
  private Dealer dealer;

  @Column(nullable = false)
  private String vehicle;

  @Column(nullable = false)
  private Float price;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private State state = State.DRAFT;

  @CreationTimestamp
  @Column(nullable = false)
  private Date createdAt;

  public Listing() {
  }

  public Listing(UUID id, Dealer dealer, String vehicle, Float price, State state, Date createdAt) {
    this.id = id;
    this.dealer = dealer;
    this.vehicle = vehicle;
    this.price = price;
    this.createdAt = createdAt;
    this.state = state;
  }

  public UUID getId() {
    return id;
  }

  public Dealer getDealer() {
    return dealer;
  }

  public void setDealer(Dealer dealer) {
    this.dealer = dealer;
  }

  public String getVehicle() {
    return vehicle;
  }

  public void setVehicle(String vehicle) {
    this.vehicle = vehicle;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }
}