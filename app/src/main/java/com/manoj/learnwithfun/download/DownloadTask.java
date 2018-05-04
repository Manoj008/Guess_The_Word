package com.manoj.learnwithfun.download;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by MANOJ on 24-Mar-18.
 */

public class DownloadTask extends AsyncTask<String, Void, List<Bitmap>> {
    Context context;
    int count;

    SharedPreferences sharedPreferences;
    public DownloadTask(Context c,int count) {
        this.context = c;
        this.count=count;
    }

    @Override
    protected List<Bitmap> doInBackground(String... urls) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HttpsURLConnection connection = null;
        List<Bitmap> bitmaps = new ArrayList<>();

        sharedPreferences = context.getSharedPreferences("mypref", MODE_PRIVATE);


        for (int i = 0; i < urls.length; i++) {
            try {
                URL url = new URL(urls[i]);
                URL currentURL = url;
                connection = (HttpsURLConnection) currentURL.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                Bitmap bm = BitmapFactory.decodeStream(inputStream);
                ContextWrapper cw = new ContextWrapper(context);
                File file = cw.getDir("imgDir", MODE_PRIVATE);
                File name = new File(file, "img"+count+i+".png");
                try {
                    FileOutputStream fos = new FileOutputStream(name);
                    bm.compress(Bitmap.CompressFormat.PNG, 80, fos);
                    fos.flush();
                    fos.close();
                    Log.e("printss", String.valueOf(name.getAbsolutePath()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }

        }
        return bitmaps;
    }

    @Override
    protected void onPostExecute(List<Bitmap> result) {

        sharedPreferences.edit().putInt("count",count).apply();

    }

}
