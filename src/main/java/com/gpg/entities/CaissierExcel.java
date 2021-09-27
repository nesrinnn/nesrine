package com.gpg.entities;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




public class CaissierExcel {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Caissier> listCaissiers;
	
	 private void writeHeaderLine() {
		 sheet = workbook.createSheet("Caissiers");
		 XSSFRow row = sheet.createRow(0);
		 
		 CellStyle style = workbook.createCellStyle();
		 XSSFFont font = workbook.createFont();
		 font.setBold(true);
		 font.setFontHeight(16);
		 style.setFont(font);
		 createCell(row,0,"FirstName",style);
		 createCell(row,1,"LastName",style);
		 createCell(row,2,"email",style);
		 createCell(row,3,"mobile1",style);
		 createCell(row,4,"mobile2",style);
		 
		 createCell(row,5,"poste",style);
		 createCell(row,6,"active",style);
	
		 
		 
	 }
	 private void createCell(Row row , int columnCount, Object value, CellStyle style)
	 {
		 sheet.autoSizeColumn(columnCount);
		 Cell cell = row.createCell(columnCount);
		 if(value instanceof Integer) {
			 cell.setCellValue((Integer) value);
		 }
		 else if(value instanceof Boolean) {
			 cell.setCellValue((Boolean) value);
		 }
		 else {
			 cell.setCellValue((String) value);
		 }
		 cell.setCellStyle(style);
		 
	 }
	public void  writeDataLines(){
		int rowCount =1;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
			for(Caissier art: listCaissiers) {
				Row row = sheet.createRow(rowCount++);
				int columnCount =0;
				createCell(row, columnCount++, art.getFirstName(), style);
				createCell(row, columnCount++, art.getLastName(), style);
				createCell(row, columnCount++, art.getEmail(), style);
				createCell(row, columnCount++, art.getMobile1(), style);
				createCell(row, columnCount++, art.getMobile2(), style);
				createCell(row, columnCount++, art.getPoste(), style);
				createCell(row, columnCount++, art.isActive(), style);
				
			}
		}
	public void export(HttpServletResponse response) throws IOException{
		writeHeaderLine();
		writeDataLines();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		
		outputStream.close();
		
	}
	 
	
	public CaissierExcel(List<Caissier> listCaissiers) {
		
		this.listCaissiers = listCaissiers;
		workbook = new XSSFWorkbook();
	}
	public XSSFWorkbook getWorkbook() {
		return workbook;
	}
	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}
	public XSSFSheet getSheet() {
		return sheet;
	}
	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}
	public List<Caissier> getListCaissiers() {
		return listCaissiers;
	}
	public void setListCaissiers(List<Caissier> listCaissiers) {
		this.listCaissiers = listCaissiers;
	}

}
