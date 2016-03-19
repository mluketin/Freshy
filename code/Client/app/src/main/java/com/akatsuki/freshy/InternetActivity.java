package com.akatsuki.freshy;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class InternetActivity extends PreferenceActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.internet);
  }
}
