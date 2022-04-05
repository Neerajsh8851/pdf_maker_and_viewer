package com.nibodev.pdfx.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.pdf.PdfRenderer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.nibodev.pdfx.pdf.PdfPage;
import com.nibodev.pdfx.pdf.PdfPageManager;


public class PdfView extends View {

    private PdfPageManager pageManager;

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


    public void nextPage() {
        pageManager.next();
        // this view should be drawn again
        invalidate();
    }


    public void previousPage() {
        pageManager.previous();
        invalidate();
    }


    private final Paint paint = new Paint();

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        paint.setAntiAlias(true);
        PdfPage page = pageManager.getCurrentPage();

        float scale = (float) getWidth() / page.getPageBitmap().getWidth();
        float translateY = (getHeight() - scale * page.getPageBitmap().getHeight()) / 2;

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        matrix.postTranslate(0, translateY);
        canvas.drawBitmap(page.getPageBitmap(), matrix, paint);
    }
}
