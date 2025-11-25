package com.astro.util;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

@Service
public class PdfGeneratorService {

    public byte[] generatePdfFromHtml(String htmlContent) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.useFont(() -> {
                        try {
                            return new FileInputStream("src/main/resources/fonts/NotoSansDevanagari-VariableFont_wdth,wght.ttf");
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    "Noto Sans Devanagari");
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
