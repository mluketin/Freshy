package com.akatsuki.freshy.service.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/create")
public class CreateAction {

  @POST
  @Path("/create")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  public String create() {

    System.out.println("CREATE METODA");



    return "NOT OK SRYS";
  }

  @GET
  @Path("/get")
  @Produces(MediaType.TEXT_PLAIN)
  public String getIt() {

      System.out.println("GET IT");

      return "Got it!";
  }

}
