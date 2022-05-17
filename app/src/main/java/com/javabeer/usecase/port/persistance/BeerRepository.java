package com.javabeer.usecase.port.persistance;

import com.javabeer.domain.beer.Beer;
import com.javabeer.domain.beer.BeerCategory;
import com.javabeer.domain.beer.BeerId;

import java.util.Collection;
import java.util.Optional;

public interface BeerRepository {
    Optional<Beer> save(Beer beer);
    Optional<Beer> findBeerById(BeerId beerId);
    Collection<Beer> findBeersByCategory(BeerCategory beerCategory);
}
