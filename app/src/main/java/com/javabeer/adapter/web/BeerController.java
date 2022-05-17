package com.javabeer.adapter.web;

import com.javabeer.domain.beer.BeerCategory;
import com.javabeer.domain.beer.BeerId;
import com.javabeer.usecase.CreateBeer;
import com.javabeer.usecase.FindBeerById;
import com.javabeer.usecase.FindBeersByCategory;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class BeerController {

    // Usecase declaration
    private final CreateBeer createBeer;
    private final FindBeerById findBeerById;
    private final FindBeersByCategory findBeersByCategory;

    public BeerDto createBeer(final BeerDto beerDto) {
        var beer = beerDto.toBeer();
        return new BeerDto(createBeer.createBeer(beer));
    }

    public BeerDto findBeerById(String id) {
        var beerId = new BeerId(id);
        return new BeerDto(findBeerById.findBeerById(beerId));
    }

    public Collection<BeerDto> findBeersByCategory(String category) {
        var beerCategory = BeerCategory.valueOf(category);
        return findBeersByCategory.findBeersByCategory(beerCategory)
                .stream().map(BeerDto::new)
                .toList();
    }
}
