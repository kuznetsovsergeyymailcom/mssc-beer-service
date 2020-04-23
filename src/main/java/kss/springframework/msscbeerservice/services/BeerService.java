package kss.springframework.msscbeerservice.services;

import kss.springframework.msscbeerservice.web.model.BeerDto;
import kss.springframework.msscbeerservice.web.model.BeerPagedList;
import kss.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);
    BeerDto getBeerById(UUID id, Boolean showInventoryOnHand);
    BeerDto saveNewBeer(BeerDto beerDto);
    BeerDto updateBeerDto(UUID uuid, BeerDto beerDto);
    BeerDto findByUpc(String upcPage);
}
