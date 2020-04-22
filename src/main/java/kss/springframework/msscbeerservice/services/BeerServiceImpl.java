package kss.springframework.msscbeerservice.services;

import kss.springframework.msscbeerservice.domain.Beer;
import kss.springframework.msscbeerservice.exceptions.NotFoundException;
import kss.springframework.msscbeerservice.web.mappers.BeerMapper;
import kss.springframework.msscbeerservice.web.model.BeerDto;
import kss.springframework.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto getBeerById(UUID id) {
        Beer beer = beerRepository.findById(id).orElseThrow(NotFoundException::new);
        BeerDto beerDto = beerMapper.beerToBeerDto(beer);
        return beerDto;
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        Beer beer = beerMapper.beerDtoToBeer(beerDto);
        Beer saved_beer = beerRepository.save(beer);
        BeerDto beer_for_front = beerMapper.beerToBeerDto(saved_beer);
        return beer_for_front;
    }

    @Override
    public BeerDto updateBeerDto(UUID uuid, BeerDto beerDto) {
        Beer beer = beerRepository.findById(uuid).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());
        Beer saved_beer = beerRepository.save(beer);

        return beerMapper.beerToBeerDto(saved_beer);
    }
}
