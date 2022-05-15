package com.javabeer;

import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerCategory;

public class Main {

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

        System.out.println("\n---Creating beers---");
        System.out.println("Beers created: %s \n %s \n %s".formatted(createBeerUseCase.createBeer(skopsko),
                createBeerUseCase.createBeer(skopskoIpa),
                createBeerUseCase.createBeer(dab)));

        System.out.println("\n---Finding beers by id---");
        System.out.println("finding beer with id: %s \n -> %s ".formatted(skopsko.getId(), findBeerByIdUseCase.findBeerById(skopsko.getId())));

        System.out.println("\n---Finding all beers in PILSNER category---");
        System.out.println("Beers in PILSNER category: %s ".formatted(beersByCategoryUseCase.findBeersByCategory(BeerCategory.PILSNER)));
    }
}
