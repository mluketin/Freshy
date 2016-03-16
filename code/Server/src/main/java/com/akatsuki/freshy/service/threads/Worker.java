package com.akatsuki.freshy.service.threads;

public class Worker implements Runnable {
  private Thread t;

  @Override
  public void run() {
    while (true) {
      try {
        if(Thread.currentThread().isInterrupted()){
          System.out.println("Interupted");
          break;
        }

        Thread.sleep(1000);
        System.out.println("Objava");
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
