package com.javabeer.vertx.adapter.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javabeer.adapter.web.BeerController;
import com.javabeer.adapter.web.BeerDto;
import com.javabeer.usecase.exception.BeerNotFoundException;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import static java.util.Objects.isNull;

public class VertxBeerController {

    private final BeerController beerController;
    private final ObjectMapper mapper;

    public VertxBeerController(BeerController controller, ObjectMapper mapper) {
        this.beerController = controller;
        this.mapper = mapper;
    }

    public void createBeer(RoutingContext routingContext) {
        var response = routingContext.response();
        var body = routingContext.body().buffer();
        if (isNull(body)) {
            sendErrorResponse(400, response);
        } else {
            var beerDto = body.toJsonObject().mapTo(BeerDto.class);
            var createdBeerDto = beerController.createBeer(beerDto);
            var result = JsonObject.mapFrom(createdBeerDto);
            sendOk(result, response);
        }
    }

    public void findBeerById(RoutingContext routingContext) {
        var response = routingContext.response();
        var beerId = routingContext.pathParam("beerId");
        if (beerId == null) {
            sendErrorResponse(400, response);
        } else {
            try {
                var beer = beerController.findBeerById(beerId);
                var result = JsonObject.mapFrom(beer);
                sendOk(result, response);
            } catch (BeerNotFoundException e) {
                sendErrorResponse(404, response);
            }
        }
    }

    public void findBeersByCategory(RoutingContext routingContext) {
        var response = routingContext.response();
        var category = routingContext.pathParam("category");
        if (category == null) {
            sendErrorResponse(400, response);
        } else {
            try {
                var beers = beerController.findBeersByCategory(category);
                sendOk(mapper.writeValueAsString(beers), response);
            } catch (BeerNotFoundException e) {
                sendErrorResponse(404, response);
            } catch (JsonProcessingException e) {
                sendErrorResponse(500, response);
            }
        }
    }

    private void sendOk(JsonObject result, HttpServerResponse response) {
        this.sendOk(result.encodePrettily(), response);
    }

    private void sendOk(String result, HttpServerResponse response) {
        response.putHeader("content-type", "application/json")
                .end(result);
    }

    private void sendErrorResponse(int statusCode, HttpServerResponse response) {
        response.putHeader("content-type", "application/json")
                .setStatusCode(statusCode)
                .end();
    }
}
