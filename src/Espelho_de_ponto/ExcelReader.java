package Espelho_de_ponto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	public String[][] ReadExcel(String path, Integer sheetIndex) {
		String[][] Table = new String[99][99];
		try {
			//creating an object to read excel file
			int line = 0, column = 0, lineFiltered = 0;
            File excelFile = new File(path);
            FileInputStream fis = new FileInputStream(excelFile);   
            Workbook workbook = new XSSFWorkbook(fis);
            
            Sheet sheet = workbook.getSheetAt(sheetIndex);            
            
            for (Row row: sheet) {
            	line += 1;
            	column = 0;
            	if (line > 10) { 
            		lineFiltered += 1;
	            	if(lineFiltered == 95)
	            		break;
	            	for (Cell cell : row) {
	            		column += 1;
	            		if (cell.getCellType().toString().equals("STRING")) {
	                       Table[lineFiltered][column] = cell.getStringCellValue();
	                    } else if (cell.getCellType().toString().equals("NUMERIC")) {
	                        System.out.print(cell.getNumericCellValue() + "\t");
	                    }
	            	}
            	}
            }
            
            workbook.close();
            fis.close();
            
        } catch (IOException e) {            e.printStackTrace();
        }
		return Table;
	}
	
	
	public String[] ReadSheet(String path) {
		String[] sheetNames = new String[99];
		try {
            File excelFile = new File(path);
            FileInputStream fis = new FileInputStream(excelFile);
            Workbook workbook = new XSSFWorkbook(fis);
            
            //creating reader sheets to add to Jcombobox
    		
            for (int i=0; i<workbook.getNumberOfSheets(); i++) {
                sheetNames[i] = ( workbook.getSheetName(i) );
            }
            
            workbook.close();
            fis.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		return sheetNames;
	}
}