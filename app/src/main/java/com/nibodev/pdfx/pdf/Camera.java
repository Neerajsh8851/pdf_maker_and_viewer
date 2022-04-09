package com.nibodev.pdfx.pdf;

import android.graphics.Matrix;

public class Camera {
    private float centerX, centerY;         // center x and y of the camera
    private float width, height;            // width and height of the camera
    private Viewport viewport;              // Viewport where the contents of camera will be shown
    private final Matrix matrix;            // Camera is nothing but a matrix transformation


    public Camera() {
        matrix = new Matrix();
        viewport = new Viewport();
    }


    @Override
    public String toString() {
        return "Camera{" +
                "centerX=" + centerX +
                ", centerY=" + centerY +
                ", width=" + width +
                ", height=" + height +
                ", viewport=" + viewport +
                ", matrix=" + matrix +
                '}';
    }

    /*** Getter and Setters ***/
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

    public void setPosition(float cx, float cy ) {
        centerX = cx;
        centerY = cy;
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

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }


    public Matrix getMatrix() {
        assert viewport != null: "this camera don't have a viewport";
        assert width > 0 && height > 0: "this camera width and height is not defined";
        assert viewport.getWidth() > 0 && viewport.getHeight() > 0: "Viewport assigned to this camera is not compatible";

        // reset the matrix to an identity matrix
        matrix.reset();

        // world to camera space
        matrix.postTranslate(-getCenterX(), -getCenterY());

        // Scale the camera to match the size of viewport
        float scaleX = viewport.getWidth() / getWidth();
        float scaleY = viewport.getHeight() / getHeight();
        matrix.postScale(scaleX, scaleY);

        // Translate the camera to the position of viewport
        matrix.postTranslate(viewport.getCenterX(), viewport.getCenterY());
        return matrix;
    }
}
