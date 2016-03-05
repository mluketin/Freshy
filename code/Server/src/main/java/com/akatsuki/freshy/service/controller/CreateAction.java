package com.akatsuki.freshy.service.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.akatsuki.freshy.service.actions.ActionResponse;
import com.google.gson.Gson;

@Path("/create")
public class CreateAction {

  @POST
  @Path("/create")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  public String create(String s) {

    System.out.println("CREATE METODA");

    System.out.println("STRING" + s);

    ActionResponse res = new ActionResponse("NOT OK ACTION RESP");

    return (new Gson()).toJson(res);
  }

  @GET
  @Path("/get")
  @Produces(MediaType.TEXT_PLAIN)
  public String getIt() {

      System.out.println("GET IT");



      return "Got it!";
  }

}
