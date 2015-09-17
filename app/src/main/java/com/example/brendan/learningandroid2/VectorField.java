package com.example.brendan.learningandroid2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by brendan on 8/23/2015.
 */
public class VectorField {
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    private ArrayList<VectorNode> allVectors = new ArrayList<VectorNode>();

    public VectorField(int width,int height,float density){
        this.width=width;
        this.height=height;
        for(float i=0;i<width;i+=density){
            for(float j=0;j<height;j+=density){
                allVectors.add(new VectorNode(i,j,1,1));
            }
        }
    }

    private double euclidian (float x1,float y1,float x2,float y2 ){
        return Math.sqrt(Math.pow(y2-y1,2)+Math.pow(x2-x1,2));
    }

    public ArrayList findAllWithin(float xPos, float yPos, float radius){
        ArrayList within= new ArrayList();
        for (VectorNode vector : allVectors){
            if (euclidian(vector.getXBase(),vector.getYBase(),xPos,yPos)<radius){
                within.add(vector);
            }
        }
        return within;
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

    public void draw (Canvas vectorCanvas,Paint drawPaint){
        for (VectorNode vector : allVectors){
            vector.draw(vectorCanvas);
        }
    }
}
