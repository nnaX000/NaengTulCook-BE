package com.example.NaengTulCook.service;

import com.example.NaengTulCook.entity.Ingredient;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    public List<Ingredient> readExcelFile(String filePath) throws IOException {
        List<Ingredient> ingredients = new ArrayList<>();

        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis); // .xlsx 형식의 엑셀 파일을 읽음
        Sheet sheet = workbook.getSheetAt(0);  // 첫 번째 시트 선택

        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (row.getRowNum() == 0) {
                continue;
            }

            Ingredient ingredient = new Ingredient();

            ingredient.setIngredientId((int) row.getCell(0).getNumericCellValue());
            ingredient.setCategory(row.getCell(1).getStringCellValue());
            ingredient.setName(row.getCell(2).getStringCellValue());
            ingredient.setPicture(row.getCell(3).getStringCellValue());

            ingredients.add(ingredient);
        }

        workbook.close();
        return ingredients;  
    }
}
