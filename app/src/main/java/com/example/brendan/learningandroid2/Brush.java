package com.example.brendan.learningandroid2;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Brush {
    public void onMotionEvent(){};
    public abstract void onTouch(VectorField theField,Canvas drawCanvas, Paint drawPaint, float x,float y,float strength, Paint color);
    public abstract void update(VectorField theField,Canvas drawCanvas);
}
