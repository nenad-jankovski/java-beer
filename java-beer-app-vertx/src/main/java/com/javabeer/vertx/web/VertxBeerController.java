package com.javabeer.vertx.web;

import com.javabeer.adapter.web.BeerController;
import com.javabeer.adapter.web.BeerDto;
import com.javabeer.usecase.exception.BeerNotFoundException;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import static java.util.Objects.isNull;

public class VertxBeerController {

    private final BeerController beerController;

    public VertxBeerController(BeerController controller) {
        this.beerController = controller;
    }

    public void createBeer(RoutingContext routingContext) {
        var response = routingContext.response();
        var body = routingContext.body().buffer();
        if (isNull(body)) {
            sendErrorResponse(400, response);
        } else {
            var beerDto = body.toJsonObject().mapTo(BeerDto.class);
            var user = beerController.createBeer(beerDto);
            var result = JsonObject.mapFrom(user);
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
                var result = JsonObject.mapFrom(beers);
                sendOk(result, response);
            } catch (BeerNotFoundException e) {
                sendErrorResponse(404, response);
            }
        }
    }

    private void sendOk(JsonObject result, HttpServerResponse response) {
        response.putHeader("content-type", "application/json")
                .end(result.encodePrettily());
    }

    private void sendErrorResponse(int statusCode, HttpServerResponse response) {
        response.putHeader("content-type", "application/json")
                .setStatusCode(statusCode)
                .end();
    }
}
