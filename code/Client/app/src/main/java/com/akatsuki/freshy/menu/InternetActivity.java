package com.akatsuki.freshy.menu;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.akatsuki.freshy.R;

public class InternetActivity extends PreferenceActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.internet);
  }
}