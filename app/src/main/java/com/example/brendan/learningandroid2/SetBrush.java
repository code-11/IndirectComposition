package com.example.brendan.learningandroid2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by brendan on 12/29/2015.
 */
public class SetBrush extends Brush {

    private float newY;
    private float newX;

    public SetBrush(int angle){
        double radAngle=Math.toRadians(angle);
        float y=(float) angleToY(radAngle);
        float x=yToX(y);
        newY=x;
        newX=y;
        dealWithJump(angle);
        Log.d("Angle Info:", "Angle: "+angle+" X:"+newX+" Y: "+newY);
    }
    private double angleToY(double angle){
        double tangle=Math.tan(angle);
        double denom= Math.sqrt(Math.pow(tangle,2)+1);
        return tangle/denom;
    }
    private float yToX(float y){
        return (float) Math.sqrt(1-Math.pow(y,2));
    }

    //Didn't think the math fully through.
    //Going from the selector to the canvas space is a coordinate transformation
    //For future work: its a 90 degree rotation and a change
    //  from cartesian coordinates to screen coordinates (y starts to increase as you go down)
    //rather than think about it and fix the math, this is a hack to get the right behavior.
    public void dealWithJump(int angle){
        if (angle<180){
            if (newX<0) {
                newX *= -1;
            }
        }else{
            if (newX>0){
                newX*=-1;
            }
        }
        if (angle<90 || angle>270){
            if (newY>0){
                newY*=-1;
                Log.d("Angle Corrected",""+newY);
            }
        }
    }

    @Override
    public void onTouch(VectorField theField, Canvas vectorCanvas, Canvas paintCanvas, Paint drawPaint, float x, float y, float strength, Paint color) {
        ArrayList<VectorNode> within = theField.findAllWithin(x,y,200);
//        Log.d("", Integer.toString(within.size()));
        for(VectorNode vector : within){
            vector.unDraw(vectorCanvas);
            vector.setXMag(newX);
            vector.setYMag(newY);
            vector.draw(vectorCanvas);

        }

//        theField.draw(vectorCanvas, drawPaint);
    }

    @Override
    public void update(VectorField theField, Canvas drawCanvas) {
    }
}
