package com.example.brendan.learningandroid2;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Brush {
    public abstract void onTouch(float x,float y,float strength, Paint color);
    public abstract void update(VectorField theField,Canvas drawCanvas);
}
