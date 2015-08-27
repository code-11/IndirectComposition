package com.example.brendan.learningandroid2;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by brendan on 8/23/2015.
 */
public class VectorNode {
    public float xbase;
    public float ybase;
    public float xMag;
    public float yMag;
    public float magnitude;

    public VectorNode(float xbase,float ybase, float xMag, float yMag){
        this.xbase=xbase;
        this.ybase=ybase;

        this.xMag=xMag;
        this.yMag=yMag;
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

    public void draw(Canvas drawCanvas,Paint drawPaint){
        drawCanvas.drawCircle(this.xbase, this.ybase, 1, drawPaint);
    }

}
