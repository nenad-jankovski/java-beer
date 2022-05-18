package com.javabeer.adapter.web;

import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerCategory;
import com.javabeer.domain.BeerId;

public record BeerDto(String id, String name, String producer, String category) {

    public BeerDto(Beer beer) {
        this(beer.getId().getValue(), beer.getName(), beer.getProducer(), beer.getCategory().name());
    }
    public Beer toBeer() {
        return Beer.builder()
                .id(new BeerId(id))
                .name(name)
                .producer(producer)
                .category(BeerCategory.valueOf(category))
                .build();
    }
}
