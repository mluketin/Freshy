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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

  private RelativeLayout lastOpenedChild;

  private MyListAdapter adapter;
  private List<ActionBig> listData;
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

    // Get a support ActionBar corresponding to this toolbar
    ActionBar ab = getSupportActionBar();
//ab.setLogo(R.drawable.ikona);
    // Enable the Up button
    //ab.setDisplayUseLogoEnabled(true);
    //ab.setLogo(R.drawable.ikona);
    ab.setHomeAsUpIndicator(R.mipmap.ikona);

    ab.setDisplayHomeAsUpEnabled(true);

    listData = DataProvider.getData();
    listView = (ListView) findViewById(R.id.list);
    adapter = new MyListAdapter(this, listData);
    listView.setAdapter(adapter);

    listView.setItemsCanFocus(true);


    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

        listView.smoothScrollToPosition(position);

        //zatvaram otvoreno dijete
        if (openedChildIndex != -1) {

          Log.i("openedChildIndex", String.valueOf(openedChildIndex));
          View viewSame = listView.getChildAt(openedChildIndex);
          Log.i("LIST SIZE", String.valueOf(listView.getChildCount()));
          ActionBig action = listData.get(openedChildIndex);


          viewSame = lastOpenedChild;

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
          List<String> words = new ArrayList<String>();
          StringTokenizer tokens = new StringTokenizer(editText.getText().toString(), " ");
          String[] splited = new String[tokens.countTokens()];
          int index = 0;
          while (tokens.hasMoreTokens())
            words.add(tokens.nextToken());
          action.setWords(words);

          Button btnService = (Button) viewSame.findViewById(R.id.btn_service);
          if (btnService.getText().toString().equals("ON"))
            action.setStatus(true);
          else
            action.setStatus(false);

          Log.i("NOTIFY", "NOTIFY");
          adapter.notifyDataSetChanged();

          //ako je kliknuti item razlicit od vec otvorenog onda otvorenog zatvaramo
          if (openedChildIndex != position) {
//            RelativeLayout row = (RelativeLayout) listView.getChildAt(openedChildIndex);
//            RelativeLayout body = (RelativeLayout) row.findViewById(R.id.body); //body tog djeteta koje je otvoreno
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

        Log.i("DOnji list size", String.valueOf(listView.getChildCount()));

        final AdapterView<?> parent1 = parent;

        //ovaj dolje kod sluzi da ako se klikne na trenutni item da se sve resetira
        final RelativeLayout head = (RelativeLayout) view.findViewById(R.id.head);
        head.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            //listView.smoothScrollToPosition(position);
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
            List<String> words = new ArrayList<String>();
            StringTokenizer tokens = new StringTokenizer(editText.getText().toString(), " ");
            String[] splited = new String[tokens.countTokens()];
            int index = 0;
            while (tokens.hasMoreTokens())
              words.add(tokens.nextToken());
            action.setWords(words);

            Button btnService = (Button) view.findViewById(R.id.btn_service);
            if (btnService.getText().toString().equals("ON"))
              action.setStatus(true);
            else
              action.setStatus(false);


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
                          adapter.remove(adapter.getItem(position));
                        }
                        adapter.notifyDataSetChanged();
                      }
                    });

    listView.setOnTouchListener(touchListener);
    // Setting this scroll listener is required to ensure that during ListView scrolling,
    // we don't look for swipes.
    listView.setOnScrollListener(touchListener.makeScrollListener());

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
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

  public void ispis(View view) {
    Log.i("AOKSJDLKASJDLKASJ", "asdasd");

  }

  //metoda koja se pokrene kad se klikne New botun
  public void addNew(View view) {
    listData.add(new ActionBig(null, "", new ArrayList<String>(), "My URL", false, false, false, false, false, false));

    adapter.notifyDataSetChanged();
/*    listView.performItemClick(
            listView.getAdapter().getView(listView.getAdapter().getCount() - 1, null, null),
            listView.getAdapter().getCount() - 1,
            listView.getAdapter().getItemId(listView.getAdapter().getCount()-1));*/
// ne diraj ovo dole
    listView.smoothScrollToPosition(listView.getAdapter().getCount());

    (new MyTask(this)).execute();


  }

  private class MyTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    public MyTask(Context ctx) {
      context = ctx;
    }

    @Override
    protected Void doInBackground(Void... params) {
      SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
      String strRingtonePreference = preference.getString("NotificationSound", "DEFAULT_SOUND");
      Boolean strVibratePreference = preference.getBoolean("VibrationEnabled", false);
      Boolean strSoundPreferenceEnabled = preference.getBoolean("SoundEnabled", false);
      Boolean strLightsPreference = preference.getBoolean("LightsEnabled", false);
      Boolean strNotifPreference = preference.getBoolean("NotificationsEnabled", false);

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

      return null;
    }
  }
}
