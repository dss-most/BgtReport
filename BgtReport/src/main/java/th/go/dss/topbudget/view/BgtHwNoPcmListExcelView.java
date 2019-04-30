package th.go.dss.topbudget.view;

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
public class BgtHwNoPcmListExcelView extends AbstractPOIExcelView {
	private static final Logger logger = LoggerFactory.getLogger(BgtHwNoPcmListExcelView.class);
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			 Workbook workbook, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		// TODO Auto-generated method stub
		
		Integer fiscalYear = (Integer) model.get("fiscalYear");
		
		String worksheetName = "ปี " + fiscalYear.toString();
		logger.debug(worksheetName);
		
		
		
		@SuppressWarnings("unchecked")
		List<Map <String,Object>> list = (List<Map <String,Object>>) model.get("bgtHwNoPcmList");
		
		Sheet sheet = workbook.createSheet(worksheetName);
		
		for(int i = 0; i<18; i++) {
			sheet.setColumnWidth(i, 20*256);
		}
		
		DataFormat format = workbook.createDataFormat();
		
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
		columnList.add("หมายเลข PCM");		//column 1
		columnList.add("หมายเลข BGT");		//column 2
		columnList.add("หมายเลขงบประมาณรับ"); //column 3
		columnList.add("หมวดรายจ่าย");		//column 4
		columnList.add("หมวดค่าใช้จ่าย");		//column 5
		columnList.add("วันที่เบิกจ่าย");		//column 6
		columnList.add("ชื่อโครงการ");		//column 7
		columnList.add("ชื่อย่อโครงการ");		//column 8
		columnList.add("ชื่อเรื่องใน BGT");		//column 9
		columnList.add("งบประมาณเบิกจ่ายรวมใน BGT");		//column 10
		columnList.add("ชื่อรายการของที่จัดซื้อจัดจ้าง");		//column 11
		columnList.add("ราคาต่อหน่วย");		//column 12
		columnList.add("จำนวน");		//column 13
		columnList.add("หน่วยนับ");		//column 14
		columnList.add("ราคาที่สืบได้รวมต่อรายการ");		//column 15
		columnList.add("ร้านค้า");		//column 16
		columnList.add("เลขที่ฎีกา");		//column 17
		columnList.add("วันที่ฎีกา");		//column 18
		columnList.add("สถานะการเบิกจ่าย");		//column 19
		columnList.add("เบิกเกินส่งคืน");		// column 20
		
		Row firstRow = sheet.createRow(0);
		Cell firstCell = firstRow.createCell(0);
		firstCell.setCellStyle(style);
		firstCell.setCellValue("รายการเบิกจ่ายครุภัณฑ์ไม่มีวศ. 22 ปีงบประมาณ" + fiscalYear);
		
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
				
				if(i == 10 || i == 12 || i == 15 || i==20) {
					if(rowData.get(columnName) != null) {
						cell.setCellValue(Double.parseDouble(rowData.get(columnName).toString()));
						cell.setCellStyle(numberStyle);
					}					
				} else if (i == 13) {
					if(rowData.get(columnName) != null) {
						cell.setCellValue(Double.parseDouble(rowData.get(columnName).toString()));
						cell.setCellStyle(digitStyle);
					}
				} else {
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

}
