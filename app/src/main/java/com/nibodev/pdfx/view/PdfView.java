package com.nibodev.pdfx.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.nibodev.pdfx.pdf.Camera;
import com.nibodev.pdfx.pdf.PdfPage;
import com.nibodev.pdfx.pdf.PdfPageManager;

import java.util.Vector;


public class PdfView extends View {

    private PdfPageManager pageManager;
    private final Paint paint = new Paint();
    private final Vector<PdfPage> pages = new Vector<>();
    private final Camera camera;

    public PdfView(Context context) {
        super(context);
    }

    public PdfView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null)
            Log.d("Attributes: ", attrs.toString());
    }

    public PdfView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PdfView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setPageManager(PdfPageManager pageManager) {
        this.pageManager = pageManager;
    }

    {
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();
    }

    public void nextPage() {
        pageManager.next();
        // this view should be drawn again
        invalidate();
    }


    public void previousPage() {
        pageManager.previous();
        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap page = pageManager.getCurrentPage().getPageBitmap();

        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();
        int viewWidht = getWidth();
        int viewHeight = getHeight();

        System.out.println("pageWidth: " + pageWidth + " pageHeight: " + pageHeight + "\n viewWidth: " + viewWidht + "viewHeight: " + viewHeight);

        camera.setWidth(page.getWidth());
        camera.setHeight(page.getHeight());
        camera.setPosition(page.getWidth() * 0.5f, page.getHeight() * 0.5f);

        System.out.println("Camera: topLeft: " + "( " + camera.getX() + ", " + camera.getY() + " )" );

        float r = camera.getHeight() / camera.getWidth();
        camera.getViewport().setSize(getWidth(), r * getWidth());
        camera.getViewport().setPosition(getWidth() / 2f, getHeight()/2f);
        canvas.drawBitmap(page, camera.getMatrix(), paint);
        super.draw(canvas);
    }
}
