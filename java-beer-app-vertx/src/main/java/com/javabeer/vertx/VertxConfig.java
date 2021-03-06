package com.javabeer.vertx;

import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerCategory;
import com.javabeer.domain.BeerId;
import com.javabeer.usecase.CreateBeer;
import com.javabeer.usecase.FindBeerById;
import com.javabeer.usecase.FindBeersByCategory;
import com.javabeer.usecase.port.IdGenerator;
import com.javabeer.usecase.port.persistance.BeerRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VertxConfig {
    int generatedId = 0;

    private final BeerRepository beerRepository = new BeerRepository() {
        List<Beer> beers = new ArrayList<>();
        @Override
        public Optional<Beer> save(Beer beer) {
            beers.add(beer);
            return Optional.of(beer);
        }

        @Override
        public Optional<Beer> findBeerById(BeerId beerId) {
            return beers.stream().filter(beer -> beerId.getValue().equals(beer.getId().getValue())).findFirst();
        }

        @Override
        public Collection<Beer> findBeersByCategory(BeerCategory beerCategory) {
            return beers.stream().filter(beer -> beerCategory == beer.getCategory()).collect(Collectors.toList());
        }
    };

    private IdGenerator idGenerator = () -> String.valueOf(++generatedId);

    public CreateBeer createBeerUseCase() {
        return new CreateBeer(beerRepository, idGenerator);
    }

    public FindBeerById findBeerByIdUseCase() {
        return new FindBeerById(beerRepository);
    }

    public FindBeersByCategory findBeersByCategoryUseCase() {
        return new FindBeersByCategory(beerRepository);
    }
}
