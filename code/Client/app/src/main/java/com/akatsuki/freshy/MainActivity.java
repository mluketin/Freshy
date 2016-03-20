package com.akatsuki.freshy;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akatsuki.freshy.database.SQLiteAdapter;
import com.akatsuki.freshy.menu.AboutActivity;
import com.akatsuki.freshy.menu.HelpActivity;
import com.akatsuki.freshy.menu.InternetActivity;
import com.akatsuki.freshy.menu.PreferencesActivity;
import com.akatsuki.freshy.model.ActionBig;
import com.akatsuki.freshy.service.ScrapingService;
import com.akatsuki.freshy.util.DataProvider;
import com.akatsuki.freshy.util.FileHandler;
import com.akatsuki.freshy.util.NetworkUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {



  private static File dirRoot;
  public static File listFile;

  private static String LIST_FILE = "list";

  private RelativeLayout lastOpenedChild;

  private MyListAdapter adapter;
  public static List<ActionBig> listData;
  private ListView listView;

  //ovo dolje je index otvorenog djeteta
  //njega zatvaramo kad novi otvorimo
  private int openedChildIndex = -1;

  private EditText openedEditText;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
    setSupportActionBar(myToolbar);

    ActionBar ab = getSupportActionBar();
    ab.setHomeAsUpIndicator(R.mipmap.ikona);
    ab.setDisplayHomeAsUpEnabled(true);

    initializeAppFolders(this);


//    SQLiteAdapter sqLiteAdapter = new SQLiteAdapter(this);
//    sqLiteAdapter.open();
//    sqLiteAdapter.dropTable();
//    sqLiteAdapter.close();

//    listData = DataProvider.getData(this);
    listData = (List<ActionBig>) FileHandler.readObject(listFile);
    listView = (ListView) findViewById(R.id.list);
    adapter = new MyListAdapter(this, listData);
    listView.setAdapter(adapter);
    listView.setItemsCanFocus(true);


    ScrapingService service = new ScrapingService();
    startService(new Intent(getBaseContext(), ScrapingService.class));



    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

        listView.smoothScrollToPosition(position);

        //zatvaram otvoreno dijete
        if (openedChildIndex != -1) {

          Log.i("openedChildIndex", String.valueOf(openedChildIndex));
          View viewSame = lastOpenedChild;
          Log.i("LIST SIZE", String.valueOf(listView.getChildCount()));
          ActionBig action = listData.get(openedChildIndex);

          if (viewSame == null) {
            Log.i("NULL", "VIEW SAME NULL");
          }
          EditText editText = (EditText) viewSame.findViewById(R.id.editTextName);
          action.setName(editText.getText().toString());

          editText = (EditText) viewSame.findViewById(R.id.editTextURL);
          action.setUrl(editText.getText().toString());

          CheckBox cbLink = (CheckBox) viewSame.findViewById(R.id.cb1);
          action.setLink(cbLink.isChecked());
          CheckBox cbImage = (CheckBox) viewSame.findViewById(R.id.cb2);
          action.setImage(cbImage.isChecked());
          CheckBox cbVideo = (CheckBox) viewSame.findViewById(R.id.cb3);
          action.setVideo(cbVideo.isChecked());
          CheckBox cbAudio = (CheckBox) viewSame.findViewById(R.id.cb4);
          action.setAudio(cbAudio.isChecked());
          CheckBox cbText = (CheckBox) viewSame.findViewById(R.id.cb5);
          action.setText(cbText.isChecked());

          editText = (EditText) viewSame.findViewById(R.id.editTextWords);
          action.setWords(editText.getText().toString());

          Button btnService = (Button) viewSame.findViewById(R.id.btn_service);
          if (btnService.getText().toString().equals("ON"))
            action.setStatus(true);
          else
            action.setStatus(false);

//          DataProvider.update(action, getBaseContext());
          FileHandler.saveObject(listData, listFile);


          Log.i("NOTIFY", "NOTIFY");
          adapter.notifyDataSetChanged();

          //ako je kliknuti item razlicit od vec otvorenog onda otvorenog zatvaramo
          if (openedChildIndex != position) {
            RelativeLayout body = (RelativeLayout) viewSame.findViewById(R.id.body); //body tog djeteta koje je otvoreno
            body.setVisibility(View.GONE);
          }
        }

        openedChildIndex = position; //postavljamo trenutni item kao otvoreni
        lastOpenedChild = (RelativeLayout) view;

        //expandira body
        final RelativeLayout body = (RelativeLayout) view.findViewById(R.id.body);
        body.setVisibility(View.VISIBLE);

        //ovo omoguci da se edit textu moze pristupit
        final RelativeLayout mainContainer = (RelativeLayout) view.findViewById(R.id.mainContainer);
        mainContainer.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        Log.i("Donji list size", String.valueOf(listView.getChildCount()));

        final AdapterView<?> parent1 = parent;

        //ovaj dolje kod sluzi da ako se klikne na trenutni item da se sve resetira
        final RelativeLayout head = (RelativeLayout) view.findViewById(R.id.head);
        head.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            ActionBig action = listData.get(position);

            EditText editText = (EditText) view.findViewById(R.id.editTextName);
            action.setName(editText.getText().toString());

            editText = (EditText) view.findViewById(R.id.editTextURL);
            action.setUrl(editText.getText().toString());

            CheckBox cbLink = (CheckBox) view.findViewById(R.id.cb1);
            action.setLink(cbLink.isChecked());
            CheckBox cbImage = (CheckBox) view.findViewById(R.id.cb2);
            action.setImage(cbImage.isChecked());
            CheckBox cbVideo = (CheckBox) view.findViewById(R.id.cb3);
            action.setVideo(cbVideo.isChecked());
            CheckBox cbAudio = (CheckBox) view.findViewById(R.id.cb4);
            action.setAudio(cbAudio.isChecked());
            CheckBox cbText = (CheckBox) view.findViewById(R.id.cb5);
            action.setText(cbText.isChecked());

            editText = (EditText) view.findViewById(R.id.editTextWords);
            action.setWords(editText.getText().toString());

            Button btnService = (Button) view.findViewById(R.id.btn_service);
            if (btnService.getText().toString().equals("ON"))
              action.setStatus(true);
            else
              action.setStatus(false);
            FileHandler.saveObject(listData, listFile);


//            boolean flag = DataProvider.update(action, getBaseContext());
//            Log.i("UPDATE SUCC", String.valueOf(flag));

            openedChildIndex = -1;
            Log.i("NOTIFY", "NOTIFY");
            adapter.notifyDataSetChanged();
          }
        });


        //OVO DOLJE se uzme textView koji je ime, koje se mijenja kad se u drugom editTextu editira
        final TextView nameView = (TextView) view.findViewById(R.id.name);
        openedEditText = (EditText) view.findViewById(R.id.editTextName);
        openedEditText.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            nameView.setText(openedEditText.getText().toString());
          }

          @Override
          public void afterTextChanged(Editable s) {
          }
        });
      }
    });


    final RelativeLayout cont = (RelativeLayout) findViewById(R.id.mainContainer);

    SwipeDismissListViewTouchListener touchListener =
            new SwipeDismissListViewTouchListener(
                    listView,
                    new SwipeDismissListViewTouchListener.DismissCallbacks() {
                      @Override
                      public boolean canDismiss(int position) {
                        return true;
                      }

                      @Override
                      public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {

                          Log.i("POSITION: ", String.valueOf(position));
                          Log.i("SIZE: ", String.valueOf(listData.size()));
//                          DataProvider.delete(listData.get(position), getBaseContext());

                          adapter.remove(adapter.getItem(position));
                          Log.i("SIZE: ", String.valueOf(listData.size()));
                        }
                        adapter.notifyDataSetChanged();
                        Log.i("SIZE: ", String.valueOf(listData.size()));
                        FileHandler.saveObject(listData, listFile);
                      }
                    });

    listView.setOnTouchListener(touchListener);
    listView.setOnScrollListener(touchListener.makeScrollListener());




  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();

    if (id == android.R.id.home) {
    }

    if (id == R.id.action_settings) {
      Intent settingsActivity = new Intent(getBaseContext(), PreferencesActivity.class);
      startActivity(settingsActivity);
    }
    if (id == R.id.action_internet) {
      Intent settingsActivity = new Intent(getBaseContext(), InternetActivity.class);
      startActivity(settingsActivity);
    }
    if (id == R.id.action_help) {
      Intent settingsActivity = new Intent(getBaseContext(), HelpActivity.class);
      startActivity(settingsActivity);
    }
    if (id == R.id.action_about) {
      Intent settingsActivity = new Intent(getBaseContext(), AboutActivity.class);
      startActivity(settingsActivity);
    }
    return super.onOptionsItemSelected(item);
  }

  public void addNew(View view) {

    Log.i("ADD NEW", "asd");
    listData.add(new ActionBig("", "", "My URL", false, false, false, false, false, false));

    adapter.notifyDataSetChanged();
    listView.smoothScrollToPosition(listView.getAdapter().getCount());

//    (new MyTask(this)).execute();





//    int status = NetworkUtil.getConnectivityStatus(this);
//    if(status == NetworkUtil.TYPE_MOBILE)
//      Log.i("NETWORK", "MOBILE");
//    else if(status == NetworkUtil.TYPE_WIFI)
//      Log.i("NETWORK", "WIFI");
//    else if (status == NetworkUtil.TYPE_NOT_CONNECTED)
//      Log.i("NETWORK", "NOT CONNECTED");
  }

  private void deleteAllInAppFolder(Context context) {
    File appDir = context.getDir("Freshy", MODE_PRIVATE); // kreira folder aplikacije
    deleteRecursive(appDir); //brise sve u njemu ukljucujuci i folder
  }

  private static void deleteRecursive(File fileOrDirectory) {
    if (fileOrDirectory.isDirectory())
      for (File child : fileOrDirectory.listFiles())
        deleteRecursive(child);

    fileOrDirectory.delete();
  }

  private static void initializeAppFolders(Context context) {
    dirRoot = context.getDir("Freshy", MODE_PRIVATE); // kreira folder aplikacije

    listFile = new File(dirRoot, LIST_FILE);
    if (!listFile.exists()) {
      List<ActionBig> lista = new ArrayList<>();
      FileHandler.saveObject(lista, listFile);
    }
  }

  @Override
  protected void onPause() {
    Log.i("PAUSE", "PAUSE");
    FileHandler.saveObject(listData, listFile);

    super.onPause();
  }

  @Override
  protected void onStop() {
    Log.i("STOP", "STOP");
    super.onStop();
  }

  @Override
  protected void onResume() {
//    listData = (List<ActionBig>) FileHandler.readObject(listFile);
//    adapter.notifyDataSetChanged();
    super.onResume();
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_HOME) {

    } else if (keyCode == KeyEvent.KEYCODE_BACK) {
      Log.i("LDA", "STISNT BACK");
      return super.onKeyDown(KeyEvent.KEYCODE_BACK, event);
    }
    return false;
  }

  private class MyTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    public MyTask(Context ctx) {
      context = ctx;
    }

    @Override
    protected Void doInBackground(Void... params) {
      SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
      Boolean strNotifPreference = preference.getBoolean("NotificationsEnabled", false);

      if (strNotifPreference == true) {
        String strRingtonePreference = preference.getString("NotificationSound", "DEFAULT_SOUND");
        Boolean strVibratePreference = preference.getBoolean("VibrationEnabled", false);
        Boolean strSoundPreferenceEnabled = preference.getBoolean("SoundEnabled", false);
        Boolean strLightsPreference = preference.getBoolean("LightsEnabled", false);

        int icon = R.mipmap.ikona;
        String tickerText = "Website has changed!";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle("Freshy")
                .setContentText(tickerText);
        if (strSoundPreferenceEnabled == true)
          builder.setSound(Uri.parse(strRingtonePreference));
        if (strVibratePreference == true)
          builder.setVibrate(new long[]{500, 1000, 500});
        if (strLightsPreference == true)
          builder.setLights(getResources().getColor(R.color.gray), 1000, 1000);

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); //ovo je ok
        int NOTIFICATION_REF = 1;

        notifManager.notify(NOTIFICATION_REF, builder.build());
      }
      return null;
    }
  }
}
