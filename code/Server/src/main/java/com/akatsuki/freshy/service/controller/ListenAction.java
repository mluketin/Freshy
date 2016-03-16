package com.akatsuki.freshy.service.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.akatsuki.freshy.service.actions.ActionSmall;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("listen")
public class ListenAction {

  @POST
  @Path("listen")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  public String listen(String request) {
    Gson gson = new GsonBuilder().create();
    ActionSmall action = gson.fromJson(request, ActionSmall.class);
    System.out.println(action);



    return "NOT";
  }

}
