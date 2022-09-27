package com.iacsd.demo.service.Impl;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.iacsd.demo.service.BarcodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class ItextBarcodeGenerator implements BarcodeGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(ItextBarcodeGenerator.class);

	public File generateBarCode(String productCode, int quantity) {
		File file = null;
		try {
			file = File.createTempFile("barcode", ".pdf");
			manipulatePdf(file.getAbsolutePath(), quantity, productCode);
		} catch (Exception e) {
			LOG.error("Error whiel generating barcodes for pcode :  {}, quantity : ", productCode, quantity, e);
		}
		return file;
	}

	private void manipulatePdf(String dest, int quantity, String pCode) throws Exception {
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
		Document doc = new Document(pdfDoc);
		Table table = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
		for (int i = 0; i < quantity; i++) {
			table.addCell(createBarcode(pCode, pdfDoc));
		}
		doc.add(table);
		doc.close();
	} 

	public static Cell createBarcode(String code, PdfDocument pdfDoc) {
		Barcode128 barcode=new Barcode128(pdfDoc);
		barcode.setCodeType(Barcode128.CODE128);
		barcode.setCode(code);
		Cell cell = new Cell().add(new Image(barcode.createFormXObject(null, null, pdfDoc)));
		cell.setPaddingTop(10);
		cell.setPaddingRight(10);
		cell.setPaddingBottom(10);
		cell.setPaddingLeft(10);
		return cell;
	}

}
