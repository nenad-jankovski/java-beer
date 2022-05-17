package com.javabeer.usecase;

import com.javabeer.ManualConfig;
import com.javabeer.domain.beer.Beer;
import com.javabeer.domain.beer.BeerCategory;
import com.javabeer.domain.beer.BeerId;
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
        createBeerUseCase.createBeer(beer);

        var foundBeer = findBeerByIdUseCase.findBeerById(beerId);
        assertThat(foundBeer).isNotNull();
        assertThat(foundBeer.getId()).isEqualTo(beerId);
    }

    @Test
    void findBeerById_when_beer_not_present() {
        CreateBeer createBeerUseCase = manualConfig.createBeerUseCase();
        var beerId = new BeerId("1");
        var beer = Beer.builder()
                .id(beerId)
                .name("Beer name 1")
                .producer("Producer 1")
                .category(BeerCategory.IPA)
                .build();
        createBeerUseCase.createBeer(beer);

        BeerNotFoundException exception = assertThrows(
                BeerNotFoundException.class,
                () -> findBeerByIdUseCase.findBeerById(new BeerId("2")),
                "Expected createBeer(beer) to throw BeerValidationException on missing BeerCategory"
        );
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("The beer with id: '2' is not found");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void findBeerById_when_beerId_is_missing(String id) {
        InvalidBeerIdException exception = assertThrows(
                InvalidBeerIdException.class,
                () -> findBeerByIdUseCase.findBeerById(new BeerId(id)),
                "Expected createBeer(beer) to throw BeerValidationException on missing BeerCategory"
        );
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("BeerId should not be null or empty");
    }
}