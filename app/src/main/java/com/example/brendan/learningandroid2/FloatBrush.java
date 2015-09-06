package com.example.brendan.learningandroid2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by brendan on 9/4/2015.
 */
public class FloatBrush extends Brush{
    private PaintFloat paintFloat;

    @Override
    public void update(VectorField theField, Canvas drawCanvas) {
        if (paintFloat != null) {

            VectorNode nearest = theField.findClosest(paintFloat.getPosX(), paintFloat.getPosY());
            paintFloat.calcMovement(nearest);
            paintFloat.draw(drawCanvas);

            if (paintFloat.stopAtBounds(theField.getWidth(),theField.getHeight())){
                paintFloat=null;
            }
        }
    }

    @Override
    public void onTouch(VectorField theField, Canvas drawCanvas, Paint drawPaint,float x, float y, float strength, Paint color) {
        paintFloat = new PaintFloat(x,y,strength,color);
    }
}
