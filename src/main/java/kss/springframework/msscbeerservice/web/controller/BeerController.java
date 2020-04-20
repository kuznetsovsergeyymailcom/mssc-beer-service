package kss.springframework.msscbeerservice.web.controller;

import kss.springframework.msscbeerservice.web.model.Beer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    @GetMapping("/{uuid}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("uuid") UUID uuid){
        return new ResponseEntity<>(Beer.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody Beer beer){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "api/v1/beer/" + beer.getId());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity updateBeerById(@PathVariable("uuid") UUID uuid, @RequestBody Beer beer){
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
