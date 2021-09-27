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

public class ProviderExcel {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Provider> listProviders;
	
	 private void writeHeaderLine() {
		 sheet = workbook.createSheet("Fournisseurs");
		 XSSFRow row = sheet.createRow(0);
		 
		 CellStyle style = workbook.createCellStyle();
		 XSSFFont font = workbook.createFont();
		 font.setBold(true);
		 font.setFontHeight(16);
		 style.setFont(font);
		 createCell(row,0,"nom",style);
		 createCell(row,1,"prénom",style);
		 createCell(row,2,"email",style);
		 createCell(row,3,"numéro de téléphone 1",style);
		 createCell(row,4,"numéro de téléphone 2",style);
		 createCell(row,5,"téléphone fixe",style);
		 createCell(row,6,"TVA",style);
		 createCell(row,7,"organisation",style);
		 createCell(row,8,"activé",style);
		 createCell(row,9,"site web",style);
		 createCell(row,10,"matricule fiscal",style);
		 
		
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
			for(Provider art: listProviders) {
				Row row = sheet.createRow(rowCount++);
				int columnCount =0;
				createCell(row, columnCount++, art.getFirstName(), style);
				createCell(row, columnCount++, art.getLastName(), style);
				createCell(row, columnCount++, art.getEmail(), style);
				createCell(row, columnCount++, art.getMobile1(), style);
				createCell(row, columnCount++, art.getMobile2(), style);
				createCell(row, columnCount++, art.getFixe(), style);
				createCell(row, columnCount++, String.valueOf(art.getTva()), style);
				createCell(row, columnCount++, art.getProviderCompany().getName(), style);
				
				createCell(row, columnCount++, art.isActive(), style);
				createCell(row, columnCount++, art.getSiteWeb(), style);
				createCell(row, columnCount++, art.getMatriculeFiscal(), style);
			
				
			}
		}
	public void export(HttpServletResponse response) throws IOException{
		writeHeaderLine();
		writeDataLines();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		
		outputStream.close();
		
	}
	 
	
	public ProviderExcel(List<Provider> listProviders) {
		
		this.listProviders = listProviders;
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
	public List<Provider> getListProviders() {
		return listProviders;
	}
	public void setListProviders(List<Provider> listProviders) {
		this.listProviders = listProviders;
	}
	


}
