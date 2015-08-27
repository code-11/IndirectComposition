package com.example.brendan.learningandroid2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by brendan on 8/23/2015.
 */
public class VectorField {
    private ArrayList<VectorNode> allVectors = new ArrayList<VectorNode>();

    public VectorField(float width,float height,float density){
        for(float i=0;i<width;i+=density){
            for(float j=0;j<height;j+=density){
                allVectors.add(new VectorNode(i,j,1,1));
            }
        }
    }

    public VectorNode findClosest(float xPos,float yPos){
        if (allVectors.isEmpty()) {
            return null;
        }else{
            int index=0;
            float leastDis=0;
            VectorNode leastDisNode=new VectorNode(0,0,0,0);
            for (VectorNode vector : allVectors) {
                float curDis=Math.abs(xPos-vector.getXBase())+Math.abs(yPos-vector.getYBase());
                if(index==0){
                    leastDis=curDis;
                    leastDisNode=vector;
                }else{
                    if(curDis<leastDis){
                        leastDis=curDis;
                        leastDisNode=vector;
                    }
                }
                index+=1;
            }
            return leastDisNode;
        }

    }

    public void draw (Canvas drawCanvas,Paint drawPaint){
        for (VectorNode vector : allVectors){
            vector.draw(drawCanvas,drawPaint);
        }
    }
}
