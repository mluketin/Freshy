package com.akatsuki.freshy.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHandler {

    public static void saveObject(Object obj, File file) {

        FileOutputStream fOut = null;
        ObjectOutputStream oOut = null;

        try {
            fOut = new FileOutputStream(file);
            oOut = new ObjectOutputStream(fOut);
            oOut.writeObject(obj);
        } catch (Exception e) {
            try {
                oOut.flush();
                oOut.close();
                fOut.close();
            } catch (Exception E) {
                Log.e("Method: saveObject;", e.toString());
            }
        }
    }

    public static Object readObject(File file) {

        FileInputStream fIn = null;
        ObjectInputStream oIn = null;

        try {
            fIn = new FileInputStream(file);
            oIn = new ObjectInputStream(fIn);
            return oIn.readObject();

        } catch (Exception e) {
            try {
                oIn.close();
                fIn.close();
            } catch (Exception e2) {
            }
        }
        return null;
    }
}
