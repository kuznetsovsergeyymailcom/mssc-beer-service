package kss.springframework.msscbeerservice.services;

import kss.springframework.msscbeerservice.domain.Beer;
import kss.springframework.msscbeerservice.exceptions.NotFoundException;
import kss.springframework.msscbeerservice.web.mappers.BeerMapper;
import kss.brewery.model.BeerDto;
import kss.springframework.msscbeerservice.repositories.BeerRepository;
import kss.brewery.model.BeerPagedList;
import kss.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand==false")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle,
                                   PageRequest pageRequest, Boolean showInventoryOnHand) {

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(showInventoryOnHand){
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerDtoToBeerWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());

        }else{
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());

        }

        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand==false")
    @Override
    public BeerDto getBeerById(UUID id, Boolean showInventoryOnHand) {

        Beer beer = beerRepository.findById(id).orElseThrow(NotFoundException::new);
        BeerDto beerDto;
        if(showInventoryOnHand){
            beerDto = beerMapper.beerDtoToBeerWithInventory(beer);
        }else{
            beerDto = beerMapper.beerToBeerDto(beer);
        }
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

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public BeerDto findByUpc(String upc) {
        System.out.println("Method find by upc calls");

        Beer beer = beerRepository.findByUpc(upc);

        return beerMapper.beerToBeerDto(beer);
    }
}
