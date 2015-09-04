package com.example.brendan.learningandroid2;

import android.graphics.Canvas;
import android.graphics.Paint;

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
    public void onTouch(float x, float y, float strength, Paint color) {
        paintFloat = new PaintFloat(x,y,strength,color);
    }
}
