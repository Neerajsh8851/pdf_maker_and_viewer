package com.nibodev.pdfx.pdf;

import android.graphics.Bitmap;

public class PdfPage {
    private int pageNumber;
    private Bitmap pageBitmap;

    public PdfPage(int pageNumber, Bitmap pageBitmap) {
        this.pageNumber = pageNumber;
        this.pageBitmap = pageBitmap;
    }

    public Bitmap getPageBitmap() {
        return pageBitmap;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
