package com.nibodev.pdfx.pdf;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;

public interface PdfPageBuilder {
    PdfPage buildPage(int pageNumber, PdfRenderer renderer);
}
