package com.javabeer.usecase;

import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerId;
import com.javabeer.usecase.exception.BeerNotFoundException;
import com.javabeer.usecase.exception.InvalidBeerIdException;
import com.javabeer.usecase.port.persistance.BeerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindBeerById {

    private final BeerRepository repository;

    public Beer findBeerById(BeerId beerId) {
        if (beerId == null || beerId.getId() == null) {
            // Validation logic can be placed in constructor
            throw new InvalidBeerIdException("BeerId should not be null");
        }
        return repository.findBeerById(beerId)
                .orElseThrow(() -> new BeerNotFoundException(
                        "The beer with id: '%s' not found".formatted(beerId.getId())));
    }
}
