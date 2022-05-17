package com.javabeer.adapter.persistance;

import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerCategory;
import com.javabeer.domain.BeerId;
import com.javabeer.usecase.port.persistance.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ANNOTATION, classes = Repository.class
))
class BeerJpaRepositoryImplTest {

    @Autowired
    private BeerRepository beerRepository;

    @Test
    void saveBeer_successful(){
        beerRepository.save(Beer.builder()
            .id(new BeerId("1"))
            .category(BeerCategory.PALE_ALE)
            .name("beer 1 name")
            .producer("beer 1 producer")
            .build());

        Optional<Beer> beerById = beerRepository.findBeerById(new BeerId("1"));
        assertThat(beerById.isPresent()).isTrue();
        assertThat(beerById.get().getId().getId()).isEqualTo("1");
    }


}