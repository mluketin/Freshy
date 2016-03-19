package com.akatsuki.freshy.service.threads;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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

        Thread.sleep(1000*5); //1min
        DbAdapter dbAdapter = new DbAdapter();
        try {
          dbAdapter.open();

          List<String> urls = dbAdapter.getAllActiveUrls();
          System.out.println("LIST SIZE " + urls.size());


          for (String url : urls) {

            String words = dbAdapter.getWords(url);
            try {

              Document doc = Jsoup.connect(url).get();
              Document iterable = Jsoup.parse(doc.toString());

              int imgCount = iterable.select("img").size();

              System.out.println("Number of img tags: " + imgCount);

//System.out.println(iterable.children().get(0).tagName());



              System.out.println(doc.toString().contains(words));

//              String onlyText = "";
//              for(Element element : doc.getAllElements()) {
//                if(element.text() != null)
//                  onlyText += element.text();
//              }

//              Whitelist whitelist = Whitelist.simpleText();
//              String result = Jsoup.clean(doc.select("a").remove().html(), whitelist);

//              System.out.println("URL: " + url);
//System.out.println("SHA1: " +  HashGenerator.GenerateStringSHA1(whitelist.toString()));
//System.out.println("Hash: " + whitelist.hashCode());
//System.out.println();


            } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          System.out.println("##################################");

          dbAdapter.close();
        } catch (ClassNotFoundException | SQLException e) {
          e.printStackTrace();
        }
        // System.out.println("Objava");
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
