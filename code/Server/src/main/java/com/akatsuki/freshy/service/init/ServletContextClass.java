package com.akatsuki.freshy.service.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.akatsuki.freshy.service.threads.ThreadManager;

public class ServletContextClass implements ServletContextListener {

  private ThreadManager threadManager;

  @Override
  public void contextDestroyed(ServletContextEvent arg0) {
    // TODO Auto-generated method stub
  }

  @Override
  public void contextInitialized(ServletContextEvent arg0) {
    threadManager = ThreadManager.getInstance();
    try {
      threadManager.startScraper();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // threadManager = ThreadManager.getInstance();
//    DbAdapter dbAdapter = new DbAdapter();
//    try {
//      dbAdapter.open();
//
//      List<String> urls = dbAdapter.getAllActiveUrls();
//      System.out.println("LIST SIZE " + urls.size());
//
//      dbAdapter.close();
//    } catch (ClassNotFoundException | SQLException e) {
//      e.printStackTrace();
//    }
  }
}
