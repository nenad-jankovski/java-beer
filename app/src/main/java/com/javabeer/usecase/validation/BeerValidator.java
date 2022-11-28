package com.javabeer.usecase.validation;

import com.javabeer.domain.Beer;
import com.javabeer.usecase.exception.BeerValidationException;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class BeerValidator {

    private BeerValidator() {
    }

    public static void validateCreateBeer(final Beer beer) {
        if (beer == null) {
            throw new BeerValidationException("Beer should not be null");
        }
        if (beer.getCategory() == null) {
            throw new BeerValidationException("Beer category is mandatory");
        }

        if (isBlank(beer.getName())) {
            throw new BeerValidationException("Beer name is mandatory");
        }
    }
}
