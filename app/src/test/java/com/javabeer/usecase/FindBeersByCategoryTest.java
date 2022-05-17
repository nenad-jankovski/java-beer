package com.javabeer.usecase;

import com.javabeer.ManualConfig;
import com.javabeer.domain.beer.Beer;
import com.javabeer.domain.beer.BeerCategory;
import com.javabeer.domain.beer.BeerId;
import com.javabeer.usecase.exception.InvalidBeerCategoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FindBeersByCategoryTest {
    private FindBeersByCategory findBeersByCategoryUseCase;
    private ManualConfig manualConfig;

    @BeforeEach
    void beforeEach() {
        manualConfig = new ManualConfig();
        findBeersByCategoryUseCase = manualConfig.findBeersByCategoryUseCase();
    }

    @Test
    void findBeerByCategory_successful_find() {
        CreateBeer createBeerUseCase = manualConfig.createBeerUseCase();
        // First beer
        var beerId = new BeerId("1");
        var beer = Beer.builder()
                .id(beerId)
                .name("Beer name 1")
                .producer("Producer 1")
                .category(BeerCategory.IPA)
                .build();
        createBeerUseCase.createBeer(beer);

        //Second beer
        var beerId2 = new BeerId("2");
        var beer2 = Beer.builder()
                .id(beerId2)
                .name("Beer name 2")
                .producer("Producer 2")
                .category(BeerCategory.IPA)
                .build();
        createBeerUseCase.createBeer(beer2);

        var foundBeersByCategory = findBeersByCategoryUseCase.findBeersByCategory(BeerCategory.IPA);
        assertThat(foundBeersByCategory).isNotNull();
        assertThat(foundBeersByCategory.size()).isEqualTo(2);
    }

    @Test
    void findBeerByCategory_not_found() {
        assertThat(findBeersByCategoryUseCase.findBeersByCategory(BeerCategory.PILSNER).size()).isEqualTo(0);
    }

    @Test
    void findBeerByCategory_fails_on_missing_category() {
        InvalidBeerCategoryException exception = assertThrows(
                InvalidBeerCategoryException.class,
                () -> findBeersByCategoryUseCase.findBeersByCategory(null),
                "Expected createBeer(beer) to throw BeerValidationException on missing BeerCategory"
        );
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Beer category is mandatory");
    }
}