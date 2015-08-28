package com.example.brendan.learningandroid2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

/**
 * Created by brendan on 8/26/2015.
 */
public class PaintFloat {
    private float accelX;
    private float accelY;

    private float velX;
    private float velY;

    private float posX;
    private float posY;

    private float mass;

    private Paint color;
    private Path path;

    public PaintFloat(float posX,float posY,float mass,Paint color){
        this.accelX=0;
        this.accelY=0;
        this.velX=0;
        this.velY=0;

        this.posX=posX;
        this.posY=posY;
        this.mass=mass;
        this.color=color;

        this.path= new Path();
        this.path.moveTo(posX, posY);

    }

    public void draw(Canvas drawCanvas){
//        drawCanvas.drawCircle(this.posX, this.posY, 1, color);
        path.lineTo(posX, posY);
        path.close();
        drawCanvas.drawPath(path, color);
//        path.reset();
        path.moveTo(posX, posY);

    }

    public float getAccelX() {
        return accelX;
    }

    public void setAccelX(float accelX) {
        this.accelX = accelX;
    }

    public float getAccelY() {
        return accelY;
    }

    public void setAccelY(float accelY) {
        this.accelY = accelY;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public Paint getColor() {
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }

    public void calcAccel(VectorNode nearest){
        float nearForceX=nearest.getXMag();
        float nearForceY=nearest.getYMag();

        accelX=nearForceX/mass;
        accelY=nearForceY/mass;

    }

    public void calcMovement(VectorNode nearest){
        calcAccel(nearest);

        velX+=accelX;
        velY+=accelY;

        posX+=velX;
        posY+=velY;
    }

    public boolean stopAtBounds(float h,float w){
        return (posX>w) || (posY>h) || (posX<0) || (posY<0);
    }

}
