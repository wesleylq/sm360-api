package com.agency.sm360api.domains.listing;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.agency.sm360api.domains.dealer.Dealer;
import com.agency.sm360api.domains.dealer.DealerService;
import com.agency.sm360api.exceptions.LimitReachedException;
import com.agency.sm360api.exceptions.NotFoundException;

@Service
public class ListingService {

  @Autowired
  ListingRepository listingRepository;

  @Autowired
  DealerService dealerService;

  Logger log = LoggerFactory.getLogger(this.getClass());

  public Listing createOrUpdateListing(ListingDto listingDto) {

    Dealer dealer = findDealer(listingDto.dealerId);
    Listing listing = listingDto.id == null ? new Listing() : findListing(listingDto.id);

    return saveListing(listing, listingDto, dealer);
  }

  public Listing updateListingState(ListingDto listingDto, State state) {

    Listing listing = findListing(listingDto.id);
    Dealer dealer = findDealer(listingDto.dealerId);

    listing.setDealer(dealer);
    listing.setState(state);

    validateDealerLimit(dealer, state);

    return listingRepository.save(listing);
  }

  public Page<Listing> getListings(ListingDto listingDto, Pageable paging) {
    Dealer dealer = findDealer(listingDto.dealerId);

    return listingRepository.findByDealerAndState(dealer, listingDto.state, paging);
  }

  public void deleteListing(ListingDto listingDto) {
    listingRepository.deleteById(listingDto.id);
  }

  private Listing saveListing(Listing listing, ListingDto listingDto, Dealer dealer) {

    validateDealerLimit(dealer, listingDto.state);

    listing.setDealer(dealer);
    listing.setVehicle(listingDto.vehicle);
    listing.setPrice(listingDto.price);
    listing.setState(listingDto.state);

    return listingRepository.save(listing);
  }

  private void validateDealerLimit(Dealer dealer, State state) {

    Integer limitCount = listingRepository.countByDealerAndState(dealer, State.PUBLISHED);

    if (state == State.PUBLISHED && limitCount >= dealer.getTierLimit()){
      log.warn(String.format("Limit reached for dealer with id: %s", dealer.getId()), dealer);
      throw new RuntimeException(new LimitReachedException());

    }
  }

  private Listing findListing(UUID id) {
    Listing listing = listingRepository.findById(id);

    if (listing == null){
      log.error(String.format("Listing not found for id: %s", id), listing);
      throw new RuntimeException(new NotFoundException(id));      
    }

    return listing;
  }

  private Dealer findDealer(UUID dealerId) {
    return dealerService.findDealer(dealerId);
  }

}
