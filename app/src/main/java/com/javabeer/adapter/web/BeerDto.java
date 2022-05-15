package com.javabeer.adapter.web;

import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerCategory;
import com.javabeer.domain.BeerId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BeerDto implements Serializable {

    private String id;
    private String name;
    private String producer;
    private String category;

    public BeerDto(Beer beer) {
        this.id = beer.getId().getId();
        this.name = beer.getName();
        this.producer = beer.getProducer();
        this.category = beer.getCategory().name();
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
