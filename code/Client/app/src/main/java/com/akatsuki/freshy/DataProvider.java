package com.akatsuki.freshy;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {

  public static List<ActionBig> getData(){
    final List<ActionBig> lista = new ArrayList<>();
    final List<String> lista1 = new ArrayList<>();

    lista.add(new ActionBig(null, "http://www.unizg.fer.hr", lista1, "My URL",false, false, false, true, false, false));
    lista.add(new ActionBig(null,"http://www.fer2.net", lista1, "My URL",true, false, true, true, false, false));
    lista.add(new ActionBig(null,"http://www.bug.hr", lista1, "My URL",false, true, false, false, false, false));
    lista.add(new ActionBig(null, "http://www.index.hr", lista1, "My URL",true, false, true, false, false, false));

    return lista;
  }

}
