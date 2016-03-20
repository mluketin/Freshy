package com.akatsuki.freshy.menu;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.akatsuki.freshy.R;

public class HelpActivity extends PreferenceActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.help);
  }
}
