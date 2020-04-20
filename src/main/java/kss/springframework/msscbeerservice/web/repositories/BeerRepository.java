package kss.springframework.msscbeerservice.web.repositories;

import kss.springframework.msscbeerservice.web.domain.Beer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
}
