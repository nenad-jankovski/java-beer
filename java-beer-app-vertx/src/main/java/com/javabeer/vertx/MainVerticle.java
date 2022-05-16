package com.javabeer.vertx;

import com.javabeer.adapter.web.BeerController;
import com.javabeer.vertx.adapter.web.VertxBeerController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

  private final VertxConfig vertxConfig = new VertxConfig();
  private final BeerController beerController = new BeerController(
          vertxConfig.createBeerUseCase(),
          vertxConfig.findBeerByIdUseCase(),
          vertxConfig.findBeersByCategoryUseCase());
  private final VertxBeerController vertxBeerController = new VertxBeerController(beerController);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.get("/v1/api/beers/:beerId").handler(vertxBeerController::findBeerById);
    router.get("/v1/api/beers/categories/:category").handler(vertxBeerController::findBeersByCategory);
    router.post("/v1/api/beers").handler(vertxBeerController::createBeer);
    vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  public static void main(final String[] args) {
    Launcher.executeCommand("run", MainVerticle.class.getName());
  }
}
