package com.example.brendan.learningandroid2;

/**
 * Created by brendan on 8/23/2015.
 */
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class DrawingView extends View {

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    private Thread thread;

//    private PaintFloat paintFloat;

    private VectorField theField;

    private Brush theBrush;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);

        theField = new VectorField(w,h,100);
        theField.draw(drawCanvas, drawPaint);

        Log.d("","Size is "+ Integer.toString(w)+","+Integer.toString(h));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                theBrush.onTouch(touchX, touchY, 1, drawPaint);

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





    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }
}
