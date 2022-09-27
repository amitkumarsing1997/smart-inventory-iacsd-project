package com.iacsd.demo.rest;

import com.iacsd.demo.service.BarcodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("barcode")
public class BarcodeGeneratorApi {

	@Autowired
	private BarcodeGenerator barcodeGenerator;

	@GetMapping("generate/{pCode}/{quantity}")
	public ResponseEntity<Resource>  generateBarcode(@PathVariable String pCode, @PathVariable int quantity) throws IOException {
		File file= barcodeGenerator.generateBarCode(pCode, quantity);
		Path path = Paths.get(file.getAbsolutePath());
	    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

	    return ResponseEntity.ok()
	            .contentLength(file.length())
	            .contentType(MediaType.parseMediaType("application/pdf"))
	            .body(resource);
	}
}
