package com.agency.sm360api.domains.dealer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "/dealers" })
public class DealerController {

  @Autowired
  private DealerService dealerService;

  @GetMapping
  public ResponseEntity<Page<Dealer>> getDealers(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(defaultValue = "name") String sortBy) {

    Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
    Page<Dealer> result = dealerService.getDealers(paging);

    return new ResponseEntity<Page<Dealer>>(result, new HttpHeaders(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Dealer> createDealer(@RequestBody Dealer dealer){
    Dealer dealerCreated = dealerService.createDealer(dealer);
      return new ResponseEntity<>(dealerCreated, HttpStatus.CREATED);
  }

}
