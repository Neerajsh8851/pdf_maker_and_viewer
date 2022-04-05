package com.nibodev.pdfx.activity;

import android.annotation.SuppressLint;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;

import com.nibodev.pdfx.R;
import com.nibodev.pdfx.pdf.PageCreater;
import com.nibodev.pdfx.pdf.PdfPageManager;
import com.nibodev.pdfx.view.PdfView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PdfViewerActivity extends AppCompatActivity implements View.OnClickListener{
    private final String FILE_NAME = "sample.pdf";

    PdfView pdfView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        pdfView = findViewById(R.id.pdf_view);
        File pdfFile = new File(getFilesDir(), FILE_NAME);
        if (!pdfFile.exists()) {
            try {
                copyToLocalCache(pdfFile, R.raw.sample);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PdfPageManager pageManager = makePdfPageManger(pdfFile);
        pdfView.setPageManager(pageManager);

        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.btn_previous).setOnClickListener(this);
    }

    private PdfPageManager makePdfPageManger(File file) {
        ParcelFileDescriptor parcelFileDescriptor;
        PdfPageManager pageManager = null;
        try {
            parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            PdfRenderer renderer = new PdfRenderer(parcelFileDescriptor);
            pageManager = new PdfPageManager(renderer, new PageCreater());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return pageManager;
    }


    // copies a raw file to storage
    void copyToLocalCache(File outputFile, @RawRes int pdfResource) throws IOException {
        if (!outputFile.exists()) {
            InputStream input = getResources().openRawResource(pdfResource);
            FileOutputStream output;
            output = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int size;

            while ((size = input.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_next) {
            pdfView.nextPage();
        }
        if (v.getId() ==R.id.btn_previous) {
            pdfView.previousPage();
        }
    }
}
