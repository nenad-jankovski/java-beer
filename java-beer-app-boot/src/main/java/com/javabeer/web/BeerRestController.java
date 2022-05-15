package com.javabeer.web;

import com.javabeer.adapter.web.BeerController;
import com.javabeer.adapter.web.BeerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerRestController {

    private BeerController beerController;

    public BeerRestController(BeerController beerController) {
        this.beerController = beerController;
    }
    @GetMapping("hi")
    public String hello() {
        return "hello";
    }

    @PostMapping
    public ResponseEntity<BeerDto> createBeer(@RequestBody BeerDto beer) {
        var createdBeer = beerController.createBeer(beer);
        return new ResponseEntity<>(createdBeer, HttpStatus.CREATED);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> findBeerById(@PathVariable String beerId) {
        var beer = beerController.findBeerById(beerId);
        return new ResponseEntity<>(beer, HttpStatus.OK);
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<Collection<BeerDto>> findBeerByCategory(@PathVariable String category) {
        var beers = beerController.findBeersByCategory(category);
        return new ResponseEntity<>(beers, HttpStatus.OK);
    }
}
