package com.td.fr.unice.polytech.ghmandroid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;

import java.io.InputStream;

public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Resources r;

    public ImageLoader(CollapsingToolbarLayout collapsingToolbarLayout, Resources r) {
        this.collapsingToolbarLayout = collapsingToolbarLayout;
        this.r = r;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urldisplay = strings[0];
        Bitmap finalImage = null;
        try {
            InputStream inputStream = new java.net.URL(urldisplay).openStream();
            finalImage = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return finalImage;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        collapsingToolbarLayout.setBackground(new BitmapDrawable(r, bitmap));
    }



}