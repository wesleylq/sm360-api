package com.agency.sm360api.domains.dealer;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.agency.sm360api.exceptions.NotFoundException;

@Service
public class DealerService {

  @Autowired
  private DealerRepository dealerRepository;

  Logger log = LoggerFactory.getLogger(this.getClass());

  public Page<Dealer> getDealers(Pageable paging) {
    return dealerRepository.findAll(paging);
  }

  public Dealer createDealer(Dealer dealer) {
    return dealerRepository.save(dealer);
  }

  public Dealer findDealer(UUID id) {
    Dealer dealer = dealerRepository.findById(id);

    if(dealer == null){
      log.error(String.format("Listing not found for id: %s", id), dealer);
      throw new RuntimeException(new NotFoundException(id));
    }

    return dealer;    
  }
}
