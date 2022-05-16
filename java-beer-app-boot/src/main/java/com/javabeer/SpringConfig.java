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
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
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

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.javabeer.adapter.web"))
                .build();
    }
}
