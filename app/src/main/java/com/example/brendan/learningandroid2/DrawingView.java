package com.example.brendan.learningandroid2;

/**
 * Created by brendan on 8/23/2015.
 */
import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.graphics.PorterDuff;
import android.view.Display;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DrawingView extends View {

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    private Canvas vectorCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    private Bitmap vectorBitmap;

    private int viewWidth;
    private int viewHeight;


    private Thread thread;

//    private PaintFloat paintFloat;

    private VectorField theField;

    private Brush theBrush;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    public void setBrush(Brush newBrush){
        theBrush=newBrush;
    }

    public Bitmap getCanvasBitmap(){
        return canvasBitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);

        vectorBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        vectorCanvas = new Canvas(vectorBitmap);

        theField = new VectorField(w,h,100);
        theField.draw(vectorCanvas, drawPaint);

        viewWidth = w;
        viewHeight = h;

        Log.d("","Size is "+ Integer.toString(w)+","+Integer.toString(h));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawBitmap(vectorBitmap, 0, 0, canvasPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();


        theBrush.onMotionEvent();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                theBrush.onTouch(theField, vectorCanvas, drawCanvas,drawPaint, touchX, touchY, 1, drawPaint);
                break;
//            case MotionEvent.ACTION_MOVE:
//                drawPath.lineTo(touchX, touchY);
//                break;
            case MotionEvent.ACTION_UP:
//                drawCanvas.drawPath(drawPath, drawPaint);
//                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);

        theBrush=new FloatBrush();

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    theBrush.update(theField,drawCanvas);

                    //Perhaps a bit of black magic to update screen
                    findViewById(R.id.drawing).postInvalidate();
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                        Log.d("", e.toString());
                    }
                }
            }
        }).start();
    }


    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        vectorCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        theField = new VectorField(viewWidth,viewHeight,100);
        theField.draw(vectorCanvas, drawPaint);
        invalidate();
    }


    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public void setSize(int newSize){
        theBrush.onBrushResize(newSize);
        drawPaint.setStrokeWidth(theBrush.getBrushSize());

        //theBrush.setBrushSize(newSize);
    }
}
