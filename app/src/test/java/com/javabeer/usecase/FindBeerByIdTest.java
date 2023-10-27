package com.javabeer.usecase;

import com.javabeer.ManualConfig;
import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerCategory;
import com.javabeer.domain.BeerId;
import com.javabeer.usecase.exception.BeerNotFoundException;
import com.javabeer.usecase.exception.InvalidBeerIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FindBeerByIdTest {
    private FindBeerById findBeerByIdUseCase;
    private ManualConfig manualConfig;

    @BeforeEach
    void beforeEach() {
        manualConfig = new ManualConfig();
        findBeerByIdUseCase = manualConfig.findBeerByIdUseCase();
    }

    @Test
    void findBeerById_successful_find() {
        CreateBeer createBeerUseCase = manualConfig.createBeerUseCase();
        var beerId = new BeerId("1");
        var beer = Beer.builder()
                .id(beerId)
                .name("Beer name 1")
                .producer("Producer 1")
                .category(BeerCategory.IPA)
                .build();
        createBeerUseCase.apply(beer);

        var foundBeer = findBeerByIdUseCase.apply(beerId);
        assertThat(foundBeer).isNotNull();
        assertThat(foundBeer.getId()).isEqualTo(beerId);
    }

    @Test
    void findBeerById_when_beer_not_present() {
        CreateBeer createBeerUseCase = manualConfig.createBeerUseCase();
        var beerId1 = new BeerId("1");
        var beer = Beer.builder()
                .id(beerId1)
                .name("Beer name 1")
                .producer("Producer 1")
                .category(BeerCategory.IPA)
                .build();
        createBeerUseCase.apply(beer);

        var beerId2 = new BeerId("2");
        BeerNotFoundException exception = assertThrows(
                BeerNotFoundException.class,
                () -> findBeerByIdUseCase.apply(beerId2),
                "Expected findBeerById(BeerId) to throw BeerNotFoundException on unexisting BeerId"
        );
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("The beer with id: '2' is not found");
    }

    @ParameterizedTest(name = "{index} => value=''{0}''")
    @NullSource
    @ValueSource(strings = {"", " "})
    void findBeerById_when_beerId_is_missing(String id) {
        var beerId = new BeerId(id);
        assertThrows(
                InvalidBeerIdException.class,
                () -> findBeerByIdUseCase.apply(beerId),
                "Expected findBeerById(BeerId) to throw InvalidBeerIdException on missing, or empty, BeerId"
        );
//        assertThat(exception).isNotNull();
//        assertThat(exception.getMessage()).isEqualTo("BeerId should not be null or empty");
    }
}