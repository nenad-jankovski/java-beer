package com.javabeer.adapter.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javabeer.domain.BeerCategory;
import com.javabeer.usecase.port.IdGenerator;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest
@ActiveProfiles("postgres")
@AutoConfigureMockMvc
@SqlGroup({
  @Sql(scripts = "/database/init.sql", executionPhase = BEFORE_TEST_METHOD),
  @Sql(scripts = "/database/cleanup.sql", executionPhase = AFTER_TEST_METHOD)
})
class BeerRestControllerTestIT {
  @Autowired private MockMvc mockMvc;
  @Autowired private BeerController beerController;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private IdGenerator idGenerator;

  @Container
  private static final PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:15-alpine").withDatabaseName("jugmk");

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Test
  void createBeer_created() throws Exception {
    var id = UUID.randomUUID().toString();
    when(idGenerator.generate()).thenReturn(id);
    var beerDto = new BeerDto(null, "JUnit Beer", "ACME Producer", BeerCategory.PALE_ALE.name());
    this.mockMvc
        .perform(
            post("/v1/api/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerDto))
                .characterEncoding("utf-8"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(id))
        .andDo(print());
  }

  @Test
  void createBeer_validation_exception() throws Exception {
    this.mockMvc
        .perform(
            post("/v1/api/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void createBeer_validation_exception_invalid_category() throws Exception {
    this.mockMvc
        .perform(
            post("/v1/api/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
{"id":null,"name":"JUnit Beer","producer":"ACME Producer","category":null}
""")
                .characterEncoding("utf-8"))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  void findBeerById_found() throws Exception {
    this.mockMvc
        .perform(
            get("/v1/api/beers/2356b160-dcf2-4b60-b166-a565efe26542")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("2356b160-dcf2-4b60-b166-a565efe26542"))
        .andDo(print());
  }

  @Test
  void findBeerById_not_found() throws Exception {
    this.mockMvc
        .perform(
            get("/v1/api/beers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
        .andExpect(status().isNotFound())
        .andDo(print());
  }
}
