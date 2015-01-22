package com.example.alejandro.practica5pmdmgaleria;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;


public class VerImagen extends Activity {
    private String ruta;
    private ImageView iv;
    private int id;
    private Button btAnterior,btSiguiente;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_imagen);

        Bundle b = getIntent().getExtras();
        ruta = b.getString("ruta");
        id = Integer.parseInt(b.getString("id"));


        iv=(ImageView)findViewById(R.id.imageView);
        btAnterior=(Button)findViewById(R.id.btAnterior);
        btSiguiente=(Button)findViewById(R.id.btSiguiente);

        File a = new File(ruta);
        Picasso.with(this).load(a).into(iv);


        btAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anterior();
            }
        });
        btSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente();
            }
        });


        // Gesture detection
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        iv.setOnTouchListener(gestureListener);

    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.v("donde","izquierda");
                    siguiente();

                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.v("donde","derecha");
                    anterior();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ver_imagen, menu);
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
    public void siguiente(){

        Log.v("id1",id+"");
        if(id==limite("ultima")){
            Log.v("estado","ultima footo");
        }else{
            id=id+1;
            String[] proyeccion= { MediaStore.Images.Media._ID,MediaStore.Images.Media.DATA};
            String where= MediaStore.Images.Media._ID + " = ?";
            String[] parametros= new String[] { id+"" };

            Cursor cur = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proyeccion, where, parametros,null);
            cur.moveToFirst();

            Log.v("numeroFotos", cur.getColumnCount() + "");
            Log.v("id2", cur.getString(0));

            ruta=cur.getString(1);

            File a = new File(ruta);
            Picasso.with(this).load(a).into(iv);
        }

    }
    public void anterior(){ Log.v("id1",id+"");
        if(id==limite("primera")){
            Log.v("estado","primeraa footo");
        }else{
            id=id-1;
            String[] proyeccion= { MediaStore.Images.Media._ID,MediaStore.Images.Media.DATA};
            String where= MediaStore.Images.Media._ID + " = ?";
            String[] parametros= new String[] { id+"" };

            Cursor cur = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proyeccion, where, parametros,null);
            cur.moveToFirst();

            Log.v("numeroFotos", cur.getColumnCount() + "");
            Log.v("id2", cur.getString(0));

            ruta=cur.getString(1);

            File a = new File(ruta);
            Picasso.with(this).load(a).into(iv);
        }


    }

    public int limite(String a){
        int num=0;
        if(a=="primera"){
            String[] proyeccion= { MediaStore.Images.Media._ID};
            Cursor cur = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proyeccion, null, null,null);
            cur.moveToFirst();
            num=Integer.parseInt(cur.getString(0));


        }else if(a=="ultima"){
            String[] proyeccion= { MediaStore.Images.Media._ID};
            Cursor cur = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proyeccion, null, null,null);
            cur.moveToLast();
            num=Integer.parseInt(cur.getString(0));
        }

        return num;
    }
}
