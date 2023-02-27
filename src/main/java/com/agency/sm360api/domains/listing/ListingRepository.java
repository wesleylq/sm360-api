package com.agency.sm360api.domains.listing;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agency.sm360api.domains.dealer.Dealer;

@Repository
public interface ListingRepository extends PagingAndSortingRepository<Listing, UUID> {

  Listing save(Listing listing);

  Listing findById(UUID id);

  Page<Listing> findByDealerAndState(Dealer dealer, State state, Pageable paging);

  Integer countByDealerAndState(Dealer dealer, State published);

  void deleteById(UUID id);

}
