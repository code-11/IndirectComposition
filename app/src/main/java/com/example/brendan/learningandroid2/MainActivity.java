package com.example.brendan.learningandroid2;

//import android.content.pm.ActivityInfo;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawView;
    private ImageButton currPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = (DrawingView)findViewById(R.id.drawing);

        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

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
    //    public boolean onTouchEvent(MotionEvent event) {
//        // MotionEvent object holds X-Y values
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            String text = "You click at x = " + event.getX() + " and y = " + event.getY();
//
//            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
//        }
//
//        return super.onTouchEvent(event);
//    }
    public void paintClicked(View view){
        if(view!=currPaint){
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawView.setColor(color);

            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton)view;
        }
    }

    public void setClick(View view){
        final SeekArc arc = new SeekArc(this);


        new AlertDialog.Builder(this)
                .setTitle("Select Brush Size")
                .setView(arc)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int val = (int) arc.getTouchAngle();
                        drawView.setBrush(new SetBrush(val));
                        Toast savedToast = Toast.makeText(getApplicationContext(),
                                Integer.toString(val), Toast.LENGTH_SHORT);
                        savedToast.show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }


    public void sizeClick(View view){
        final SeekBar bar = new SeekBar(this);


        new AlertDialog.Builder(this)
                .setTitle("Select Brush Size")
                .setView(bar)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int val = bar.getProgress();
                        drawView.setSize(val);
                        Log.d("Size Bar",""+val);
                        Toast savedToast = Toast.makeText(getApplicationContext(),
                                "Size Changed", Toast.LENGTH_SHORT);
                        savedToast.show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }

    public String handleIO(){
        final EditText txtUrl = new EditText(this);
        txtUrl.setHint("Untitled");
        String toReturn="";

        new AlertDialog.Builder(this)
                .setTitle("Input Title")
                .setView(txtUrl)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String url = txtUrl.getText().toString();
                        String imgSaved=save2(url);
                        if (imgSaved!=null) {
                            Toast savedToast = Toast.makeText(getApplicationContext(),
                                    "Drawing saved to "+imgSaved, Toast.LENGTH_SHORT);
                            savedToast.show();
                        } else {
                            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                    "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                            unsavedToast.show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
        return "";
    }

    public String save(){
        String imgSaved = MediaStore.Images.Media.insertImage(
                getContentResolver(), drawView.getCanvasBitmap(),"test.png", "drawing");
        return imgSaved;
    }
    public String save2(String path){
        try {
            String fileName = Environment.getExternalStorageDirectory() +"/"+ path +".png";
            OutputStream stream = new FileOutputStream(fileName);
            drawView.getCanvasBitmap().compress(Bitmap.CompressFormat.PNG, 80, stream);
            stream.close();
            return fileName;
        }catch(IOException e){
            return null;
        }
    }

    public void saveClick(View view){
        Log.d("", "Save Clicked");
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing");
        saveDialog.setMessage("Save drawing to device Gallery?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                handleIO();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        saveDialog.show();
    }
    public void floatClick(View view){
        drawView.setBrush(new FloatBrush());
        Log.d("", "Float Clicked");
    }
    public void newClick(View view){
        AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
        newDialog.setTitle("New drawing");
        newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
        newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                drawView.startNew();
                dialog.dismiss();
            }
        });
        newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        newDialog.show();
//        drawView.startNew();
    }
    public void inClick(View view){
        drawView.setBrush(new InBrush());
        Log.d("","In Clicked");
    }
    public void outClick(View view){
        drawView.setBrush(new OutBrush());
        Log.d("","Out Clicked");
    }
}