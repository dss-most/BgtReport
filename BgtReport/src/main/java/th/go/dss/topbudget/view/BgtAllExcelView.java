package th.go.dss.topbudget.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.common.usermodel.Fill;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.engine.jdbc.ColumnNameCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BgtAllExcelView extends AbstractPOIExcelView {
	private static final Logger logger = LoggerFactory.getLogger(BgtAllExcelView.class);
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			 Workbook workbook, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		// TODO Auto-generated method stub
		
		Integer fiscalYear = (Integer) model.get("fiscalYear");
		
		String worksheetName = "ปี " + fiscalYear.toString();
		logger.debug(worksheetName);
		
		
		
		@SuppressWarnings("unchecked")
		List<Map <String,Object>> list = (List<Map <String,Object>>) model.get("bgtAllList");
		
		//create a wordsheet
		Sheet sheet = workbook.createSheet(worksheetName);
		DataFormat format = workbook.createDataFormat();
		
		for(int i = 0; i<18; i++) {
			sheet.setColumnWidth(i, 20*256);
		}
		
		CellStyle style = workbook.createCellStyle();
		CellStyle numberStyle = workbook.createCellStyle();
		CellStyle digitStyle = workbook.createCellStyle();

		
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short)8);
		font.setFontName("Tahoma");
		style.setFont(font);

		CellStyle headerStyle = workbook.createCellStyle();
		Font boldFont = workbook.createFont();
		boldFont.setFontHeightInPoints((short)8);
		boldFont.setFontName("Tahoma");
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerStyle.setFont(boldFont);

		numberStyle.setFont(font);
		numberStyle.setDataFormat(format.getFormat("#,##0.00"));
		
		digitStyle.setFont(font);
				
		List<String> columnList = new ArrayList<String>();
		columnList.add("หมายเลข BGT");		// column 1
		columnList.add("เลขงบประมาณรับ");	// column 2
		columnList.add("หมายเลข PCM");		// column 3
		columnList.add("หมวดรายจ่าย");		// column 4
		columnList.add("วันที่เบิกจ่าย");		// column 5
		columnList.add("ชื่อโครงการ");		// column 6
		columnList.add("ชื่อย่อโครงการ");		// column 7
		columnList.add("ชื่อเรื่องใน BGT");		// column 8
		columnList.add("งบประมาณเบิกจ่ายรวมใน BGT");	// column 9
		columnList.add("รายละเอียด");		// column 10
		columnList.add("ร้านค้า");		// column 11
		columnList.add("รายละเอียดใบเสร็จรับเงิน");		// column 12
		columnList.add("เลขที่ฎีกา");		// column 13
		columnList.add("วันที่ฎีกา");		// column 14
		columnList.add("เลข GF_DOC");		// column 15
		columnList.add("วันที่ GF_DOC");		// column 16
		columnList.add("ยกเลิก");		// column 17
		columnList.add("เบิกเกินส่งคืน");		// column 18
		columnList.add("หมายเหตุ");		// column 19
		
		
		Row firstRow = sheet.createRow(0);
		Cell firstCell = firstRow.createCell(0);
		firstCell.setCellStyle(style);
		firstCell.setCellValue("รายการเบิกจ่ายตาม BGT ทั้งหมดของปีงบประมาณ " + fiscalYear);
		
		Row header = sheet.createRow(1);
		if(list.size() > 0) {
			int i=0;
			for(String columnName : columnList) {
				Cell cell = header.createCell(i++);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(columnName);
				CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);	
				
			}
		}
		 
		int rowNum = 2;
		for (Map<String, Object> rowData : list) {
			//create the row data
			Row row = sheet.createRow(rowNum++);
			int i = 0;
			for(String columnName : columnList) {
				Cell cell = row.createCell(i++);
				
				if(i == 9 || i == 18) {
					if(rowData.get(columnName) != null) {
						cell.setCellValue(Double.parseDouble(rowData.get(columnName).toString()));
						cell.setCellStyle(numberStyle);
					}					
				} else if (i == 10 || i == 12) {
					if(rowData.get(columnName) != null) {
						// now we have to take out <li></li>
						List<String> strs = stringInListTag(rowData.get(columnName).toString());
						String cellString = "";
						int j = 1;
						for(String s : strs ) {
							cellString += j++ + ". " + s + "  ";
						}
						
						cell.setCellValue(cellString);
						cell.setCellStyle(style);
					}
				}  else {
					cell.setCellStyle(style);
					cell.setCellValue(rowData.get(columnName)!=null?rowData.get(columnName).toString():"");
				}
				
			}
		}
		
	}

	@Override
	protected Workbook createWorkbook() {
		// TODO Auto-generated method stub
		return new XSSFWorkbook();
	}
	
	private List<String> stringInListTag(String s) {
		if(s == null ) return null;
		Pattern p = Pattern.compile("<li>(.*?)</li>");
		Matcher m = p.matcher(s);

		List<String> list = new ArrayList<String>();
		while(m.find()) {
			String match = m.group(1);
			// now remove <br/>
			match = match.replaceAll("<br/>", " -- ");
			
			list.add(match);
		}
		
		return list;
	}

}
