package com.example.alexander.node;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by W11B on 29.10.2017.
 */

public class Const {
    public static final String LOG = "NOTE_Msg";

    public static Bitmap getBitMap(String path) throws FileNotFoundException {

        FileInputStream fis = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(fis);

        Bitmap img = BitmapFactory.decodeStream(bis);
        return img;
    }
}
