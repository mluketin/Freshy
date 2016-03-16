package com.akatsuki.freshy.service.threads;

public class ThreadManager {
  private static ThreadManager instance;

  private Worker worker;
  private Scraper scraper;



  private ThreadManager() {
  }

  public static ThreadManager getInstance() {
    if (instance == null) {
      synchronized (ThreadManager.class) {
        if (instance == null) {
          instance = new ThreadManager();
        }
      }
    }
    return instance;
  }

//  public void create() throws InterruptedException {
//    worker = new Worker();
//    worker.start();
//  }
//
//  public void stop() throws InterruptedException {
//    worker.stop();
//  }

  public void startScraper() throws InterruptedException {
    scraper = new Scraper();
    scraper.start();
  }

  public void stopScraper() throws InterruptedException {
    scraper.stop();
  }






}
