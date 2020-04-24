package kss.springframework.msscbeerservice.web.controller;

import kss.springframework.msscbeerservice.web.model.BeerDto;
import kss.springframework.msscbeerservice.services.BeerService;
import kss.springframework.msscbeerservice.web.model.BeerPagedList;
import kss.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class BeerController {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping(produces = { "application/json" }, path = "beer")
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
        if(showInventoryOnHand == null){
            showInventoryOnHand = false;
        }

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);
        beerList.get().forEach(System.out::println);

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping("beer/{uuid}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("uuid") UUID uuid,
                                               @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
        if(showInventoryOnHand == null){
            showInventoryOnHand = false;
        }

        BeerDto beerById = beerService.getBeerById(uuid, showInventoryOnHand);
        return new ResponseEntity<>(beerById, HttpStatus.OK);
    }

    @PostMapping(path = "beer")
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDto beer){
        beerService.saveNewBeer(beer);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "api/v1/beer/" + beer.getId());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("beer/{uuid}")
    public ResponseEntity updateBeerById(@PathVariable("uuid") UUID uuid, @RequestBody @Validated BeerDto beer){
        beerService.updateBeerDto(uuid, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("beerUpc/{upc}")
    public ResponseEntity getBeerByUPC(@PathVariable("upc") String upc){

        BeerDto beerByUPC = beerService.findByUpc(upc);
        return new ResponseEntity<>(beerByUPC, HttpStatus.OK);
    }
}
