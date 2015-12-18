package com.example.brendan.learningandroid2;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Brush {
    private int brushSize;
    public void setBrushSize(int newBrushSize){
        brushSize=newBrushSize;
    }
    public void onMotionEvent(){};
    public abstract void onTouch(VectorField theField,Canvas vectorCanvas,Canvas drawCanvas, Paint drawPaint, float x,float y,float strength, Paint color);
    public abstract void update(VectorField theField,Canvas drawCanvas);
}
