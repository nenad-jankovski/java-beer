package com.javabeer.persistance;

import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerCategory;
import com.javabeer.domain.BeerId;
import com.javabeer.persistance.entity.BeerJpaEntity;

public class BeerEntityMapper {

    public static BeerJpaEntity fromDomainModel(Beer beer) {
        var beerJpaEntity = new BeerJpaEntity();
        beerJpaEntity.setId(beer.getId().getId());
        beerJpaEntity.setCategory(beer.getCategory().name());
        beerJpaEntity.setProducer(beer.getProducer());
        beerJpaEntity.setName(beer.getName());
        return beerJpaEntity;
    }

    public static Beer toDomainModel(BeerJpaEntity beerJpa) {
        return Beer.builder()
                .id(new BeerId(beerJpa.getId()))
                .category(BeerCategory.valueOf(beerJpa.getCategory()))
                .producer(beerJpa.getProducer())
                .name(beerJpa.getName())
                .build();
    }
}
