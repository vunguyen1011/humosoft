package com.example.Humosoft.Office;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.example.Humosoft.DTO.Response.SalaryResponse;

import org.apache.poi.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ExcelExporter {
    private static final Logger logger = LoggerFactory.getLogger(ExcelExporter.class);
    
    public void exportSalaryList(HttpServletResponse response, List<SalaryResponse> salaryList) throws IOException {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Salaries");

            // Tạo hàng tiêu đề
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Full Name", "Basic Salary", "Bonuses", "Deductions", "Net Salary", "Month", "Year"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Ghi dữ liệu từ danh sách SalaryResponse
            int rowNum = 1;
            for (SalaryResponse salary : salaryList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(salary.getId());
                row.createCell(1).setCellValue(salary.getFullname());
                row.createCell(2).setCellValue(salary.getBasicSalary());
                row.createCell(3).setCellValue(salary.getBonuses());
                row.createCell(4).setCellValue(salary.getDeductions());
                row.createCell(5).setCellValue(salary.getNetSalary());
                row.createCell(6).setCellValue(salary.getMonth());
                row.createCell(7).setCellValue(salary.getYear());
            }

            // Gửi file Excel về client
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=salaries.xlsx");

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath = "D:/salaries_" + timestamp + ".xlsx";
            File file = new File(filePath);
            OutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();


            logger.info("File 'salaries.xlsx' was successfully exported.");
        } catch (Exception e) {
            logger.error("Error occurred while exporting the file: ", e);
            throw new IOException("Error exporting Excel file", e);
        }
    }
}
