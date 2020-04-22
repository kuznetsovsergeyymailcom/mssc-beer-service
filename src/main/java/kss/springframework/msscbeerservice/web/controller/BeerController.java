package kss.springframework.msscbeerservice.web.controller;

import kss.springframework.msscbeerservice.web.model.BeerDto;
import kss.springframework.msscbeerservice.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{uuid}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("uuid") UUID uuid){
        BeerDto beerById = beerService.getBeerById(uuid);
        return new ResponseEntity<>(beerById, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDto beer){
        beerService.saveNewBeer(beer);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "api/v1/beer/" + beer.getId());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity updateBeerById(@PathVariable("uuid") UUID uuid, @RequestBody @Validated BeerDto beer){
        beerService.updateBeerDto(uuid, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
