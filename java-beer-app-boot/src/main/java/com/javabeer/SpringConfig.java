package com.javabeer;

import com.javabeer.adapter.web.BeerController;
import com.javabeer.generator.UuidGenerator;
import com.javabeer.usecase.CreateBeer;
import com.javabeer.usecase.FindBeerById;
import com.javabeer.usecase.FindBeersByCategory;
import com.javabeer.usecase.port.IdGenerator;
import com.javabeer.usecase.port.persistance.BeerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public CreateBeer createBeerUseCase(BeerRepository beerRepository, IdGenerator idGenerator) {
        return new CreateBeer(beerRepository, idGenerator);
    }

    @Bean
    public FindBeerById createFindBeerByIdUseCase(BeerRepository beerRepository) {
        return new FindBeerById(beerRepository);
    }

    @Bean
    public FindBeersByCategory createFindBeersByCategoryUseCase(BeerRepository beerRepository) {
        return new FindBeersByCategory(beerRepository);
    }

    @Bean
    public IdGenerator createIdGenerator() {
        return new UuidGenerator();
    }

    @Bean
    public BeerController createBeerController(CreateBeer createBeer, FindBeerById findBeerById, FindBeersByCategory findBeersByCategory) {
        return new BeerController(createBeer, findBeerById, findBeersByCategory);
    }
}
