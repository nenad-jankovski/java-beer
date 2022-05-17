package com.javabeer.usecase;

import com.javabeer.ManualConfig;
import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerCategory;
import com.javabeer.domain.BeerId;
import com.javabeer.usecase.exception.BeerValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateBeerTest {

    private CreateBeer createBeerUseCase;
    private ManualConfig manualConfig;

    @BeforeEach
    void beforeEach() {
        manualConfig = new ManualConfig();
        createBeerUseCase = manualConfig.createBeerUseCase();
    }

    @Test
    void createBeer_successful_creation() {
        var beer = Beer.builder()
                .id(new BeerId("1"))
                .name("Beer name 1")
                .producer("Producer 1")
                .category(BeerCategory.IPA)
                .build();
        Beer createdBeer = createBeerUseCase.createBeer(beer);
        assertThat(createdBeer).isNotNull();
        assertThat(createdBeer.getId().getValue()).isNotNull();
    }

    @Test
    void createBeer_fail_on_missing_category() {
        var beer = Beer.builder()
                .id(new BeerId("1"))
                .name("Beer name 1")
                .producer("Producer 1")
                .build();
        BeerValidationException exception = assertThrows(
                BeerValidationException.class,
                () -> createBeerUseCase.createBeer(beer),
                "Expected createBeer(beer) to throw BeerValidationException on missing BeerCategory"
        );
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Beer category is mandatory");
    }

    @Test
    void createBeer_fail_on_missing_beer_name() {
        var beer = Beer.builder()
                .id(new BeerId("1"))
                .producer("Producer 1")
                .category(BeerCategory.IPA)
                .build();
        BeerValidationException exception = assertThrows(
                BeerValidationException.class,
                () -> createBeerUseCase.createBeer(beer),
                "Expected createBeer(beer) to throw BeerValidationException on missing name"
        );
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Beer name is mandatory");
    }

    @Test
    void createBeer_fail_on_null() {
        BeerValidationException exception = assertThrows(
                BeerValidationException.class,
                () -> createBeerUseCase.createBeer(null),
                "Expected createBeer(beer) to throw BeerValidationException on missing name"
        );
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Beer should not be null");
    }
}