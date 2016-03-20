package com.akatsuki.freshy.util;

import android.content.Context;
import android.util.Log;

import com.akatsuki.freshy.database.SQLiteAdapter;
import com.akatsuki.freshy.model.ActionBig;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {

  public static List<ActionBig> getData(){
    final List<ActionBig> lista = new ArrayList<>();

    lista.add(new ActionBig("http://www.unizg.fer.hr", "", "My URL",false, false, false, true, false, false));
    lista.add(new ActionBig("http://www.fer2.net", "", "My URL",true, false, true, true, false, false));
    lista.add(new ActionBig("http://www.bug.hr", "", "My URL",false, true, false, false, false, false));
    lista.add(new ActionBig("http://www.index.hr", "", "My URL",true, false, true, false, false, false));

    return lista;
  }

  public static List<ActionBig> getData(Context context) {
    SQLiteAdapter adapter = new SQLiteAdapter(context);
    adapter.open();
    List<ActionBig> lista = adapter.getExistingData();
    adapter.close();
    return lista;
  }

  public static boolean update(ActionBig action, Context ctx) {
    SQLiteAdapter adapter = new SQLiteAdapter(ctx);
    adapter.open();
    boolean flag = adapter.create(action);
    Log.i("CREATE", String.valueOf(flag));
    adapter.close();
    return flag;
  }

  public static void delete(ActionBig action, Context ctx) {
    SQLiteAdapter adapter = new SQLiteAdapter(ctx);
    adapter.open();
    boolean flag = adapter.delete(action);
    adapter.close();
  }

  public static void resume(ActionBig action, Context context, String sha) {
    SQLiteAdapter adapter = new SQLiteAdapter(context);
    adapter.open();
    boolean flag = adapter.resume(action, sha);
    adapter.close();
  }

  public static void pause(ActionBig action, Context context) {
    SQLiteAdapter adapter = new SQLiteAdapter(context);
    adapter.open();
    boolean flag = adapter.pause(action);
    adapter.close();
  }
}
