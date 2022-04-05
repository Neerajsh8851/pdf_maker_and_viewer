package com.nibodev.pdfx.pdf;

import static com.nibodev.pdfx.Constants.TAG;

import android.graphics.pdf.PdfRenderer;
import android.util.Log;

import java.util.ArrayList;

public class PdfPageManager {
    private int totalPages;
    private int currentPageIndex;
    private PdfRenderer renderer;
    private PdfPageBuilder pageBuilder;
    private int maxCacheSize = 50;

    private ArrayList<PdfPage> cachedPages;

    public PdfPageManager(
            PdfRenderer renderer,
            PdfPageBuilder pageBuilder
    ) {
        this.renderer = renderer;
        this.pageBuilder = pageBuilder;
        init();
    }


    public PdfPageManager(
            PdfRenderer renderer,
            PdfPageBuilder pageBuilder,
            int cachedPageSize
    ) {
        this.renderer = renderer;
        this.pageBuilder = pageBuilder;
        this.maxCacheSize = cachedPageSize;
        init();
    }

    private void init() {
        totalPages = renderer.getPageCount();
        currentPageIndex = 0;
        cachedPages = new ArrayList<>(maxCacheSize);
        pageBuilder = new PageCreater();
    }

    public boolean hasNext() {
        return currentPageIndex + 1  < totalPages;
    }

    public boolean hasPrevious() {
        return currentPageIndex - 1 >= 0;
    }

    public boolean isPageAvailable(int pageNumber) {
        return pageNumber >= 0 && pageNumber < totalPages;
    }

    // move the cursor to the next page
    public boolean next() {
        if (hasNext()) {
            currentPageIndex++;
            return true;
        }
        return false;
    }

    // move the cursor to the previous page
    public boolean previous() {
        if (hasPrevious()) {
            currentPageIndex--;
            return true;
        }
        return false;
    }

    // move the cursor to a arbitrary page
    public void moveTo(int pageNumber) {
        assert isPageAvailable(pageNumber) : "out of bound!";
        currentPageIndex = pageNumber;
    }

    public PdfPage getCurrentPage() {
        Log.d(TAG, "PdfPageManager: currentPageIndex = " + currentPageIndex);
        return getPage(currentPageIndex);
    }

    public PdfPage getPage(int pageNumber) {
        assert isPageAvailable(pageNumber) : "you should check page availability with isPageAvailable()";
        PdfPage pdfPage;
        pdfPage = searchCachedPage(pageNumber);
        if (pdfPage == null) {
            pdfPage = pageBuilder.buildPage(pageNumber, renderer);
            cachePage(pdfPage);
        }
        return pdfPage;
    }

    private void cachePage(PdfPage pdfPage) {
        if (cachedPages.size() > maxCacheSize) cachedPages.remove(0);
        cachedPages.add(pdfPage);
    }

    private PdfPage searchCachedPage(int pageNumber) {
        PdfPage result = null;
        for (PdfPage page : cachedPages) {
            if (page.getPageNumber() == pageNumber) {
                result = page;
                break;
            }
        }
        return result;
    }


    // close the pdfRenderer when this object gets garbage collected
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (renderer != null) {
            renderer.close();
        }
    }
}
