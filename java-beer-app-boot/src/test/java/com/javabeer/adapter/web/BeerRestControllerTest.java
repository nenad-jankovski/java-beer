package com.javabeer.adapter.web;

import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerCategory;
import com.javabeer.domain.BeerId;
import com.javabeer.usecase.exception.BeerNotFoundException;
import com.javabeer.usecase.exception.BeerValidationException;
import com.javabeer.usecase.exception.InvalidBeerCategoryException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BeerRestController.class)
class BeerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeerController beerController;

    @Test
    void createBeer_created() throws Exception {
        var createdBeer = Beer.builder()
                .id(new BeerId("1"))
                .producer("ACME producer")
                .name("JUnit Beer")
                .category(BeerCategory.PALE_ALE)
                .build();
        BeerDto beerDto = new BeerDto(createdBeer);
        when(beerController.createBeer(any())).thenReturn(beerDto);
        this.mockMvc.perform(post("/v1/api/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .characterEncoding("utf-8")).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1")).andDo(print());

    }

    @Test
    void createBeer_validation_exception() throws Exception {
        var createdBeer = Beer.builder()
                .id(new BeerId("1"))
                .producer("ACME producer")
                .name("JUnit Beer")
                .category(BeerCategory.PALE_ALE)
                .build();
        when(beerController.createBeer(any())).thenThrow(new BeerValidationException("Invalid beer"));
        this.mockMvc.perform(post("/v1/api/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .characterEncoding("utf-8")).andExpect(status().isBadRequest()).andDo(print());

    }

    @Test
    void createBeer_validation_exception_invalid_category() throws Exception {
        when(beerController.createBeer(any())).thenThrow(new InvalidBeerCategoryException("Category is invalid"));
        this.mockMvc.perform(post("/v1/api/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .characterEncoding("utf-8")).andExpect(status().isBadRequest()).andDo(print());

    }

    @Test
    void findBeerById_found() throws Exception {
        var createdBeer = Beer.builder()
                .id(new BeerId("1"))
                .producer("ACME producer")
                .name("JUnit Beer")
                .category(BeerCategory.PALE_ALE)
                .build();
        BeerDto beerDto = new BeerDto(createdBeer);
        when(beerController.findBeerById(any())).thenReturn(beerDto);
        this.mockMvc.perform(get("/v1/api/beers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1")).andDo(print());
    }

    @Test
    void findBeerById_not_found() throws Exception {
        when(beerController.findBeerById(any())).thenThrow(BeerNotFoundException.class);
        this.mockMvc.perform(get("/v1/api/beers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")).andExpect(status().isNotFound())
                .andDo(print());
    }
}