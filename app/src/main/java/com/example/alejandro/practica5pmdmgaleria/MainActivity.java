package com.example.alejandro.practica5pmdmgaleria;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    private AdaptadorCursor ad;
    private int VERIMAGEN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        String selection=MediaStore.Video.Media.DATA +" like?";
        String[] selectionArgs=new String[]{"%DCIM%"};
        Cursor cur = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,parameters, selection, selectionArgs, MediaStore.Video.Media.DATE_TAKEN + " DESC");*/

        Cursor cur = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null, null,null, null);
        ad = new AdaptadorCursor(this, cur);
        final GridView gv = (GridView) findViewById(R.id.grid);
        gv.setAdapter(ad);
        registerForContextMenu(gv);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getApplicationContext(), VerImagen.class);
                Bundle b = new Bundle();
                Cursor c=(Cursor)gv.getItemAtPosition(i);
                b.putString("ruta", c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA)));
                b.putString("id", c.getString(c.getColumnIndex(MediaStore.Images.Media._ID)));
                it.putExtras(b);
                startActivityForResult(it, VERIMAGEN);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
