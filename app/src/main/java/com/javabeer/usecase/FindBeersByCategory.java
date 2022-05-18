package com.javabeer.usecase;

import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerCategory;
import com.javabeer.usecase.exception.InvalidBeerCategoryException;
import com.javabeer.usecase.port.persistance.BeerRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class FindBeersByCategory {

    private final BeerRepository repository;

    public Collection<Beer> apply(BeerCategory beerCategory) {
        if (beerCategory == null) {
            throw new InvalidBeerCategoryException("Beer category is mandatory");
        }
        return repository.findBeersByCategory(beerCategory);
    }
}
