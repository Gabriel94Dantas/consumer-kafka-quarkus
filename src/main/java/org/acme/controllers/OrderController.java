package org.acme.controllers;

import io.quarkus.runtime.annotations.QuarkusMain;
import org.acme.services.OrderService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(){
        this.orderService = new OrderService();
    }

    @GET
    @Path("clientId/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByClientId(@PathParam("clientId") String clientId){
        try{
            String order = this.orderService.returnOrderByClientId(clientId);
            if(order != null){
                return Response.ok(order).build();
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
