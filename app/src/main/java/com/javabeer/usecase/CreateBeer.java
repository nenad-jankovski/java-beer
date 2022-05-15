package com.javabeer.usecase;

import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerId;
import com.javabeer.usecase.port.IdGenerator;
import com.javabeer.usecase.port.persistance.BeerRepository;
import com.javabeer.usecase.validation.BeerValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateBeer {

    private final BeerRepository repository;
    private final IdGenerator generator;

    public Beer createBeer(Beer beer) {
        BeerValidator.validateCreateBeer(beer);
        beer.setId(new BeerId(generator.generate()));
        return repository.save(beer).orElse(null);
    }
}
