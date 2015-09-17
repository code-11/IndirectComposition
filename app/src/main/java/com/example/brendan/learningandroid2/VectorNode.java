package com.example.brendan.learningandroid2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Color;
import android.util.Log;


/**
 * Created by brendan on 8/23/2015.
 */
public class VectorNode {
    public float xbase;
    public float ybase;
    public float xMag;
    public float yMag;
    public float magnitude;
    public float drawMajorAxis;
    public float drawMinorAxis;

    public VectorNode(float xbase,float ybase, float xMag, float yMag){
        this.xbase=xbase;
        this.ybase=ybase;

        this.xMag=xMag;
        this.yMag=yMag;

        this.drawMajorAxis=25;
        this.drawMinorAxis=20;
    }

    public float getXBase(){
        return this.xbase;
    }
    public float getYBase(){
        return this.ybase;
    }
    public float getXMag(){
        return this.xMag;
    }
    public float getYMag() {return this.yMag;}
    public void setXMag(float newMagX){
        this.xMag=newMagX;
    }
    public void setYMag(float newMagY){
        this.yMag=newMagY;
    }

    public float[] getUnit(){
        float norm=xMag+yMag;
        return new float[] {xMag/norm,yMag/norm};
    }

    public void drawHelp(Canvas vectorCanvas, Paint aPaint){
        //drawTriangle(drawCanvas, drawPaint);
        vectorCanvas.drawLine(xbase, ybase, xbase + (xMag * drawMajorAxis), ybase + (yMag * drawMajorAxis), aPaint);

        float[] unit=getUnit();
        float newXMag=unit[0];
        float newYMag=unit[1];
        vectorCanvas.drawLine(xbase, ybase, xbase - (newYMag * drawMinorAxis), ybase + (newXMag * drawMinorAxis), aPaint);
    }

    public void draw(Canvas vectorCanvas){
        Paint black = new Paint();
        black.setColor(Color.BLACK);
        black.setStrokeWidth(1);
        drawHelp(vectorCanvas,black);

//        drawCanvas.drawLine((xbase+(xMag*10)),(ybase+(yMag*10)), (xbase + (xMag * 10)) + (yMag * 10), (ybase + (yMag * 10)) + (xMag * 10), drawPaint);
        //drawCanvas.drawCircle(this.xbase, this.ybase, 1, drawPaint);
    }

    public void unDraw(Canvas vectorCanvas) {
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        white.setStrokeWidth(4);
//        drawHelp(vectorCanvas,white);

        white.setStyle(Paint.Style.FILL);
        float aproxMag=+Math.abs(xMag)+ Math.abs(yMag);
        vectorCanvas.drawCircle(xbase, ybase, aproxMag*drawMajorAxis, white);
//        Log.d("","This is running: ");
////        draw(vectorCanvas,white);
    }

    private void drawTriangle(Canvas drawCanvas, Paint drawPaint){
        float lineEndX = xbase+xMag;
        float lineEndY = ybase+yMag;

        //drawPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        drawPaint.setStrokeWidth(1);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(lineEndX, lineEndY);
        path.lineTo(lineEndX + 10, lineEndY);
        path.lineTo(lineEndX, lineEndY + 20);
        path.lineTo(lineEndX - 10, lineEndY);
        path.lineTo(lineEndX, lineEndY);
        path.close();

        drawCanvas.drawPath(path, drawPaint);
    }

}
