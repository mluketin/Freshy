package com.akatsuki.freshy.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;

import com.akatsuki.freshy.MainActivity;
import com.akatsuki.freshy.R;
import com.akatsuki.freshy.model.ActionBig;
import com.akatsuki.freshy.util.FileHandler;
import com.akatsuki.freshy.util.HashGenerator;
import com.akatsuki.freshy.util.NetworkUtil;
import com.akatsuki.freshy.util.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ScrapingService extends IntentService {

  private HashMap<String, String> mapa = new HashMap<>();

  private int notifId = 1;

  public ScrapingService() {
    super("ScrapingService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
Log.i("SERVICE", "SERVICE");
    while (true) {

      try {
        int sleepTime = Integer.parseInt(preference.getString("TimeIntervals", "5"));
        boolean mobileData = preference.getBoolean("MobileDataEnabled", true);
        boolean wifi = preference.getBoolean("WIFIEnabled", true);

        Log.i("PREF", String.valueOf(wifi) + " " + String.valueOf(mobileData));

        int status = NetworkUtil.getConnectivityStatus(this);
        if (status == NetworkUtil.TYPE_MOBILE && mobileData)
          scrape();
        else if (status == NetworkUtil.TYPE_WIFI && wifi)
          scrape();
        else if (status == NetworkUtil.TYPE_NOT_CONNECTED)
          Log.i("NETWORK", "NOT CONNECTED");

        Thread.sleep(sleepTime * 1000);
        Log.i("SLEEP", "SLEEP");

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void scrape() throws IOException {
    Log.i("SCRAPE", "ULAZ");

//    SQLiteAdapter adapter = new SQLiteAdapter(this);
//    adapter.open();
//    List<ActionBig> lista = adapter.getExistingData();

    List<ActionBig> lista = (List<ActionBig>) FileHandler.readObject(MainActivity.listFile);

    Log.i("LISTA", String.valueOf(lista.size()));

    for (ActionBig action : lista) {
      if (action.isStatus()) {
        Document doc = Jsoup.connect(action.getUrl()).get();

        doc.select("head").remove();
        doc.select("script").remove();
        doc.select("iframe").remove();
        if (!action.isLink()) {
          doc.select("a").remove();
        }

        Log.i("ACTION TO STR", action.toString());

        String plainText = "";

        if (action.isImage()) {
          plainText += doc.select("img").toString();
        }
        if (action.isAudio()) {
          plainText += doc.select("audio").toString();
        }
        if (action.isVideo()) {
          plainText += doc.select("video").toString();
        }
        if (action.isLink()) {
          plainText += doc.select("a").toString();
        }
        if (action.isText()) {
          plainText += doc.text();
        }
        boolean flag = false;
        String words = action.getWords();
        String newString = "";
        if (words.length() > 0) {
          String[] wordsArray = words.split(" ");
          boolean[] flags = new boolean[wordsArray.length];
          int br = 0;
          for (String str : wordsArray) {
            if (doc.getElementsContainingOwnText(str).size() > 0) {
              flag = true;
              flags[br] = true;
              notif(action.getUrl());
              break;
            } else {
              newString += wordsArray[br] + " ";
            }
            br++;
          }
        }

        String sha = HashGenerator.GenerateStringSHA1(plainText);
        Log.i("NEW SHA2", sha);

        String oldSha = mapa.get(action.getUrl());

        if (oldSha == null) {
          mapa.put(action.getUrl(), sha);

        } else {

          if (flag) {
            action.setWords(newString);
            FileHandler.saveObject(lista, MainActivity.listFile);
            notif(action.getUrl());
            if (!oldSha.equals(sha)) {
              mapa.put(action.getUrl(), sha);
            }
          } else {
            if (!oldSha.equals(sha)) {
              notif(action.getUrl());
              mapa.put(action.getUrl(), sha);
            }
          }
        }
      }
    }
  }


  private void notif(String url) {
    SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    preference.edit().putBoolean("NotificationsEnabled", true).commit();
    Boolean strNotifPreference = preference.getBoolean("NotificationsEnabled", true);

    if (strNotifPreference == true) {
      String strRingtonePreference = preference.getString("NotificationSound", "DEFAULT_SOUND");
      Boolean strVibratePreference = preference.getBoolean("VibrationEnabled", true);
      Boolean strSoundPreferenceEnabled = preference.getBoolean("SoundEnabled", true);
      Boolean strLightsPreference = preference.getBoolean("LightsEnabled", true);

      int icon = R.mipmap.ikona;
      String tickerText = "Website has changed!";

      NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
              .setSmallIcon(icon)
              .setContentTitle("Freshy - website changed")
              .setContentText(url);
      if (strSoundPreferenceEnabled == true)
        builder.setSound(Uri.parse(strRingtonePreference));
      if (strVibratePreference == true)
        builder.setVibrate(new long[]{500, 1000, 500});
      if (strLightsPreference == true)
        builder.setLights(getResources().getColor(R.color.gray), 1000, 1000);

      NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); //ovo je ok
      int NOTIFICATION_REF = 1;

      notifManager.notify(NOTIFICATION_REF, builder.build());
      notifId++;
    }
  }



}
