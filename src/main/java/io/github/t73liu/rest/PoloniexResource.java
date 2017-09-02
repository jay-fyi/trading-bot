package io.github.t73liu.rest;

import io.github.t73liu.model.ExceptionWrapper;
import io.github.t73liu.model.PoloniexPair;
import io.github.t73liu.service.PoloniexService;
import io.github.t73liu.service.PoloniexTicker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Component
@Path("/poloniex")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api("PoloniexResource")
@ApiResponses(@ApiResponse(code = 500, message = "Internal Server Error", response = ExceptionWrapper.class))
public class PoloniexResource {
    private final PoloniexService service;
    private final PoloniexTicker ticker;

    @Autowired
    public PoloniexResource(PoloniexService service, PoloniexTicker ticker) {
        this.service = service;
        this.ticker = ticker;
    }

    @GET
    @Path("/tickers")
    @ApiResponses(@ApiResponse(code = 200, message = "Retrieved Ticker of Specified Pair in Poloniex", response = Map.class))
    public Response getTicker(@QueryParam("tradingPair") @NotNull PoloniexPair tradingPair) throws Exception {
        // FIXME not catching null 400 bad request
        return Response.ok(ticker.getTickerValue(tradingPair)).build();
    }

    @GET
    @Path("/balance")
    @ApiResponses(@ApiResponse(code = 200, message = "Retrieved Balance in Poloniex", response = Map.class))
    public Response getBalance() throws Exception {
        return Response.ok(service.getBalance()).build();
    }

    @GET
    @Path("/orders/open")
    @ApiResponses(@ApiResponse(code = 200, message = "Retrieved Open Orders in Poloniex", response = Map.class))
    public Response getOpenOrders() throws Exception {
        return Response.ok(service.getOpenOrders()).build();
    }
}
