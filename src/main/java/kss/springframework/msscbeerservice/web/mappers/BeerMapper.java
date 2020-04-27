package kss.springframework.msscbeerservice.web.mappers;


import kss.springframework.msscbeerservice.domain.Beer;
import kss.brewery.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDto dto);
    BeerDto beerDtoToBeerWithInventory(Beer beer);
    BeerDto beerToBeerDto(Beer beer);
}
