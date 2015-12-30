package com.example.brendan.learningandroid2;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Brush {
    private int brushSize=20;
    public float lerp(float val1,float val2,float prog){
        return val1 + (val2 - val1) * prog;
    }
    public void setBrushSize(int newBrushSize){
        brushSize=newBrushSize;
    }
    public int getBrushSize(){return brushSize;}
    public abstract void onBrushResize(int newSize);
    public void onMotionEvent(){};
    public abstract void onTouch(VectorField theField,Canvas vectorCanvas,Canvas drawCanvas, Paint drawPaint, float x,float y,float strength, Paint color);
    public abstract void update(VectorField theField,Canvas drawCanvas);
}
