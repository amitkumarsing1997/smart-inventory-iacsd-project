package com.iacsd.demo.util;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.iacsd.demo.exception.GenericException;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import java.io.ByteArrayOutputStream;

public class PDFUtil {

    private static Document htmlToDoc(String html) {
        Document document = Jsoup.parse(html, "UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document;
    }


    public static byte[] htmlToPdf(String html) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PdfRendererBuilder builder = new PdfRendererBuilder();
            String baseUri = PDFUtil.class.getClassLoader().getResource("images/").toURI() .toString(); //path for images
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(htmlToDoc(html)), baseUri);
            builder.run();
            os.close();
            return os.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GenericException("PDF error");
        }
    }
}
