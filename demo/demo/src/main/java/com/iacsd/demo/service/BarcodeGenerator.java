package com.iacsd.demo.service;

import java.io.File;

public interface BarcodeGenerator {
  
	File generateBarCode(String productCode, int quantity);
	
}
