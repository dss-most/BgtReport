package th.go.dss.topbudget.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class BgtOtherNoPcmListExcelView extends AbstractPOIExcelView {
	private static final Logger logger = LoggerFactory.getLogger(BgtOtherNoPcmListExcelView.class);
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			 Workbook workbook, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {

		Integer fiscalYear = (Integer) model.get("fiscalYear");
		
		String worksheetName = "ปี " + fiscalYear.toString();
		logger.debug(worksheetName);
		
		
		
		@SuppressWarnings("unchecked")
		List<Map <String,Object>> list = (List<Map <String,Object>>) model.get("bgtOtherNoPcmList");
		
		//create a wordsheet
		Sheet sheet = workbook.createSheet(worksheetName);
		
		for(int i = 0; i<12; i++) {
			sheet.setColumnWidth(i, 20*256);
		}
		
		DataFormat format = workbook.createDataFormat();
		
		CellStyle style = workbook.createCellStyle();
		CellStyle numberStyle = workbook.createCellStyle();
		
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

				
		List<String> columnList = new ArrayList<String>();
		columnList.add("หมายเลข BGT");  		// column 1
		columnList.add("หมายเลขงบประมาณรับ"); //column 2
		columnList.add("หมวดรายจ่าย");		// column 3
		columnList.add("หมวดค่าใช้จ่าย");		// column 4
		columnList.add("วันที่เบิกจ่าย");		// column 5
		columnList.add("ชื่อโครงการ");		// column 6
		columnList.add("ชื่อย่อโครงการ");		// column 7
		columnList.add("ชื่อเรื่องใน BGT");		// column 8
		columnList.add("งบประมาณเบิกจ่ายรวมใน BGT"); //column 9
		columnList.add("ร้านค้า");			// column 10
		columnList.add("เลขที่ฎีกา");			// column 11
		columnList.add("วันที่ฎีกา");			// column 12
		columnList.add("สถานะการเบิกจ่าย");	// column 13
		columnList.add("เบิกเกินส่งคืน");		// column 14
		
		Row firstRow = sheet.createRow(0);
		Cell firstCell = firstRow.createCell(0);
		firstCell.setCellStyle(style);
		
		firstCell.setCellValue("รายการเบิกจ่ายอื่น ไม่มีวศ. 22 ปีปงบประมาณ" + fiscalYear);
		
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
				if(i == 9 || i == 14) {
					if(rowData.get(columnName) != null) {
						cell.setCellValue(Double.parseDouble(rowData.get(columnName).toString()));
						cell.setCellStyle(numberStyle);
					}
				}  else {
					cell.setCellValue(rowData.get(columnName)!=null?rowData.get(columnName).toString():"");
					cell.setCellStyle(style);
				
				}
			}
		}
		
	}

	@Override
	protected Workbook createWorkbook() {
		// TODO Auto-generated method stub
		return new XSSFWorkbook();
	}

}
