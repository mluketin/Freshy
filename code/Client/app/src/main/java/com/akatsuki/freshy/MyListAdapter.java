package com.akatsuki.freshy;

import android.app.Notification;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akatsuki.freshy.model.ActionBig;
import com.akatsuki.freshy.util.DataProvider;
import com.akatsuki.freshy.util.HashGenerator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class MyListAdapter extends ArrayAdapter<ActionBig> {

  private Context context;
  private List<ActionBig> values;

  public MyListAdapter(Context context, List<ActionBig> values) {
    super(context, R.layout.row_layout, values);
    this.context = context;
    this.values = values;
  }

  @Override
  public void setNotifyOnChange(boolean notifyOnChange) {
    Log.i("NOTIFY", "NOTIFY");
    super.setNotifyOnChange(notifyOnChange);
  }

  //android:layout_width="40dp"
//  android:layout_height="?attr/actionBarSize"
  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.row_layout, parent, false);
    RelativeLayout rel = (RelativeLayout) rowView.findViewById(R.id.head);

    rel.setBackground(context.getResources().getDrawable(R.drawable.back_drawable));

    if (values.get(position).getUrl() != null) {
      TextView nameView = (TextView) rowView.findViewById(R.id.name);
      nameView.setText(values.get(position).getName());

      EditText editText = (EditText) rowView.findViewById(R.id.editTextURL);
      editText.setText(values.get(position).getUrl());

      editText = (EditText) rowView.findViewById(R.id.editTextName);
      editText.setText(values.get(position).getName());
    }

    CheckBox cbLink = (CheckBox) rowView.findViewById(R.id.cb1);
    if (values.get(position).isLink()) {
      cbLink.setChecked(true);
    }

    CheckBox cbImage = (CheckBox) rowView.findViewById(R.id.cb2);
    if (values.get(position).isImage()) {
      cbImage.setChecked(true);
    }

    CheckBox cbVideo = (CheckBox) rowView.findViewById(R.id.cb3);
    if (values.get(position).isVideo()) {
      cbVideo.setChecked(true);
    }

    CheckBox cbAudio = (CheckBox) rowView.findViewById(R.id.cb4);
    if (values.get(position).isAudio()) {
      cbAudio.setChecked(true);
    }

    CheckBox cbText = (CheckBox) rowView.findViewById(R.id.cb5);
    if (values.get(position).isText()) {
      cbText.setChecked(true);
    }

    EditText editText = (EditText) rowView.findViewById(R.id.editTextWords);
    String words = values.get(position).getWords();//words null
    editText.setText(words);

    RelativeLayout relServ = (RelativeLayout) rowView.findViewById(R.id.rel_btn);
    Button btnServ = (Button) rowView.findViewById(R.id.btn_service);
    if (values.get(position).isStatus()) {
      btnServ.setText("ON");
      btnServ.setTextColor(context.getResources().getColor(R.color.gray));
      btnServ.setBackgroundColor(context.getResources().getColor(R.color.white));
      relServ.setBackgroundColor(context.getResources().getColor(R.color.white));
    } else {
      btnServ.setText("OFF");
      btnServ.setTextColor(context.getResources().getColor(R.color.white));
      btnServ.setBackgroundColor(context.getResources().getColor(R.color.silver));
      relServ.setBackgroundColor(context.getResources().getColor(R.color.silver));

    }

    RelativeLayout body = (RelativeLayout) rowView.findViewById(R.id.body);
    body.setVisibility(View.GONE);

    final RelativeLayout relService = (RelativeLayout) rowView.findViewById(R.id.rel_btn);
    final Button btnService = (Button) rowView.findViewById(R.id.btn_service);
    btnService.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String status = btnService.getText().toString();

        if (status.equals("ON")) {
          btnService.setText("OFF");
          btnService.setTextColor(context.getResources().getColor(R.color.white));
          btnService.setBackgroundColor(context.getResources().getColor(R.color.silver));
          values.get(position).setStatus(false);
          relService.setBackgroundColor(context.getResources().getColor(R.color.silver));
//          DataProvider.pause(values.get(position), context);

        } else {
          btnService.setText("ON");
          btnService.setTextColor(context.getResources().getColor(R.color.gray));
          btnService.setBackgroundColor(context.getResources().getColor(R.color.white));
          relService.setBackgroundColor(context.getResources().getColor(R.color.white));
          values.get(position).setStatus(true);
//          ShaAsyncTask task = new ShaAsyncTask(values.get(position), context);
//          task.execute();
        }
      }
    });
    return rowView;
  }


  private class ShaAsyncTask extends AsyncTask<Void, Void, Void> {

    private ActionBig action;
    private Context context;

    public ShaAsyncTask(ActionBig action, Context ctx) {
      this.action = action;
      this.context = ctx;
    }

    @Override
    protected Void doInBackground(Void... params) {
      try {
        Document doc = Jsoup.connect(action.getUrl()).get();

        doc.select("head").remove();
        doc.select("script").remove();
        doc.select("iframe").remove();
        if (!action.isLink()) {
          doc.select("a").remove();
        }

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

        String sha =  HashGenerator.GenerateStringSHA1(plainText);
        DataProvider.resume(action, context, sha);
      } catch (IOException e) {
        e.printStackTrace();
      }

      return null;
    }
  }


}
