package com.nibodev.pdfx.pdf;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;

public class PageCreater implements PdfPageBuilder {
    @Override
    public PdfPage buildPage(int pageNumber, PdfRenderer renderer) {
        PdfRenderer.Page page = renderer.openPage(pageNumber);
        Bitmap pageBitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
        page.render(pageBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        page.close();
        return new PdfPage(pageNumber, pageBitmap);
    }
}
