package com.javabeer;

import com.javabeer.domain.beer.Beer;
import com.javabeer.domain.beer.BeerCategory;

import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger("com.javabeer");
    public static void main(String[] args) {
        var config = new ManualConfig();
        var createBeerUseCase = config.createBeerUseCase();
        var findBeerByIdUseCase = config.findBeerByIdUseCase();
        var beersByCategoryUseCase = config.findBeersByCategoryUseCase();

        // Create some beers
        Beer skopsko = Beer.builder()
                .category(BeerCategory.PILSNER)
                .name("Skopsko")
                .producer("ACME beer company")
                .build();
        Beer skopskoIpa = Beer.builder()
                .category(BeerCategory.IPA)
                .name("Skopsko IPA")
                .producer("ACME beer company")
                .build();
        Beer dab = Beer.builder()
                .category(BeerCategory.PILSNER)
                .name("Zlaten Dab")
                .producer("ACME beer company Prilep")
                .build();

        logger.info("\n---Creating beers---");
        logger.info("Beers created: %s \n %s \n %s".formatted(createBeerUseCase.createBeer(skopsko),
                createBeerUseCase.createBeer(skopskoIpa),
                createBeerUseCase.createBeer(dab)));

        logger.info("\n---Finding beers by id---");
        logger.info("finding beer with id: %s \n -> %s ".formatted(skopsko.getId(), findBeerByIdUseCase.findBeerById(skopsko.getId())));

        logger.info("\n---Finding all beers in PILSNER category---");
        logger.info("Beers in PILSNER category: %s ".formatted(beersByCategoryUseCase.findBeersByCategory(BeerCategory.PILSNER)));
    }
}
