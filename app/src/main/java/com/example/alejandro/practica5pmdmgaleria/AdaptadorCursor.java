package com.example.alejandro.practica5pmdmgaleria;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class AdaptadorCursor extends CursorAdapter{
    private ImageView iv;



    public AdaptadorCursor(Context context, Cursor c) {
        super(context, c,true);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup vg) {
        LayoutInflater i = LayoutInflater.from(vg.getContext());
        View v = i.inflate(R.layout.grid_detalle, vg, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        iv=(ImageView)view.findViewById(R.id.grid_image);

        String ruta=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        File a = new File(ruta);
        Picasso.with(context).load(a).into(iv);

    }



}
