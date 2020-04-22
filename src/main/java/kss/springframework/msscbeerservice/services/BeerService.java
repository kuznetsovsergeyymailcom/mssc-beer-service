package kss.springframework.msscbeerservice.services;

import kss.springframework.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID id);
    BeerDto saveNewBeer(BeerDto beerDto);
    BeerDto updateBeerDto(UUID uuid, BeerDto beerDto);
}
