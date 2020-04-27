package kss.springframework.msscbeerservice.services;

import kss.brewery.model.BeerDto;
import kss.brewery.model.BeerPagedList;
import kss.brewery.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);
    BeerDto getBeerById(UUID id, Boolean showInventoryOnHand);
    BeerDto saveNewBeer(BeerDto beerDto);
    BeerDto updateBeerDto(UUID uuid, BeerDto beerDto);
    BeerDto findByUpc(String upcPage);
}
