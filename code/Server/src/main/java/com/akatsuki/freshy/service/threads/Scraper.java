package com.akatsuki.freshy.service.threads;

import java.sql.SQLException;
import java.util.List;

import com.akatsuki.freshy.service.database.DbAdapter;

public class Scraper implements Runnable {
  private Thread t;

  @Override
  public void run() {
    while (true) {
      try {
        if (Thread.currentThread().isInterrupted()) {
          System.out.println("Interupted");
          break;
        }

        Thread.sleep(5000);
        DbAdapter dbAdapter = new DbAdapter();
        try {
          dbAdapter.open();

          List<String> urls = dbAdapter.getAllActiveUrls();
          System.out.println("LIST SIZE " + urls.size());

          dbAdapter.close();
        } catch (ClassNotFoundException | SQLException e) {
          e.printStackTrace();
        }
//        System.out.println("Objava");
      } catch (InterruptedException e) {
        e.printStackTrace();
        break;
      }
    }
  }

  public void start() throws InterruptedException {
    t = new Thread(this, "threadName");
    t.start();
  }

  public void stop() throws InterruptedException {
    t.interrupt();
  }
}
