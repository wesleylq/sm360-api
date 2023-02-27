package com.agency.sm360api.domains.dealer;

import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerRepository extends PagingAndSortingRepository<Dealer, UUID> {

  Dealer save(Dealer dealer);

  Dealer findById(UUID dealerId);

}
