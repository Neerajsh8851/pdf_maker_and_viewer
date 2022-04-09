package com.nibodev.pdfx.pdf;

import android.graphics.Matrix;

public class Viewport {
    private float centerX, centerY;         // center x and y of the viewport
    private float width, height;            // width and height of the viewport

    public float getX() {
        return centerX - width * 0.5f;
    }

    public float getY() {
        return centerY - height * 0.5f;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setSize(float w, float h) {
        width = w;
        height = h;
    }

    // set the center position coordinate
    public void setPosition(float cx, float cy) {
        centerX = cx;
        centerY = cy;
    }
}
