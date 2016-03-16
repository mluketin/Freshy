package com.akatsuki.freshy.service.threads;

public class Waiter implements Runnable {

  int sleepTime;

  public Waiter(int sleepTime) {
    this.sleepTime = sleepTime;
  }

  @Override
  public void run() {

    try {
      Thread.sleep(sleepTime);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void start() throws InterruptedException {
    Thread t = new Thread(this, "threadName");
    t.start();
    t.join();
  }

}
