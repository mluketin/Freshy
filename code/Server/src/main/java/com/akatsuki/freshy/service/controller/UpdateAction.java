package com.akatsuki.freshy.service.controller;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.akatsuki.freshy.service.actions.ActionBig;
import com.akatsuki.freshy.service.actions.ActionResponse;
import com.akatsuki.freshy.service.database.DbAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("update")
public class UpdateAction extends ControllerBase{

  @POST
  @Path("update")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  public String update(String request) {
    Gson gson = new GsonBuilder().create();
    ActionBig action = gson.fromJson(request, ActionBig.class);
    System.out.println(action);

    DbAdapter dbAdapter = new DbAdapter();
    try {
      dbAdapter.open();
      dbAdapter.update(action);
      dbAdapter.close();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

    ActionResponse res = new ActionResponse("Action not implemented");
    return (new Gson()).toJson(res);
  }
}
