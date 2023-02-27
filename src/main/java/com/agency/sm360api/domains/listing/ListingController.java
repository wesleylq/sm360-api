package com.agency.sm360api.domains.listing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/listings")
public class ListingController {

  @Autowired
  ListingService listingService;

  Logger log = LoggerFactory.getLogger(this.getClass());

  @GetMapping
  public ResponseEntity<Page<Listing>> getListings(
      @RequestBody ListingDto listingDto,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(defaultValue = "createdAt") String sortBy) {

    Pageable paging = PageRequest.of(page, size, Sort.by(sortBy).descending());

    Page<Listing> result = listingService.getListings(listingDto, paging);

    return new ResponseEntity<Page<Listing>>(result, new HttpHeaders(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Listing> createOrUpdateListing(@RequestBody ListingDto listingDto) {
    Listing listingCreated = listingService.createOrUpdateListing(listingDto);
    return new ResponseEntity<>(listingCreated, HttpStatus.CREATED);
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteListing(@RequestBody ListingDto listingDto) {
    listingService.deleteListing(listingDto);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("publish")
  public ResponseEntity<Listing> publishListing(@RequestBody ListingDto listingDto) {
    Listing listingUpdated = listingService.updateListingState(listingDto, State.PUBLISHED);
    return new ResponseEntity<>(listingUpdated, HttpStatus.OK);
  }

  @PostMapping("unpublish")
  public ResponseEntity<Listing> unpublishListing(@RequestBody ListingDto listingDto) {
    Listing listingUpdated = listingService.updateListingState(listingDto, State.DRAFT);
    return new ResponseEntity<>(listingUpdated, HttpStatus.OK);
  }

}
