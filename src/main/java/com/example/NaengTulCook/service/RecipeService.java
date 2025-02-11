package com.example.NaengTulCook.service;

import com.example.NaengTulCook.entity.Recipe;
import com.example.NaengTulCook.repository.RecipeRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public void saveRecipesFromExcel(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();

        rowIterator.next();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Recipe recipe = new Recipe();

            recipe.setForwardName(row.getCell(1).getStringCellValue());
            recipe.setName(row.getCell(2).getStringCellValue());
            recipe.setEssentialIngredient(getListFromCell(row.getCell(3)));
            recipe.setOptionalIngredient(getListFromCell(row.getCell(4)));
            recipe.setSeasoning(getListFromCell(row.getCell(5)));
            recipe.setTool(getListFromCell(row.getCell(6)));
            recipe.setRecipeCook(getCellValue(row.getCell(7)));
            recipe.setRecipeSeasoning(getCellValue(row.getCell(8)));
            recipe.setRecipePlating(getCellValue(row.getCell(9)));
            recipe.setLevel((int) row.getCell(10).getNumericCellValue());
            recipe.setTime((int) row.getCell(11).getNumericCellValue());
            recipe.setCatchphrase(row.getCell(12).getStringCellValue());
            recipe.setPicture(row.getCell(13).getStringCellValue());

            recipeRepository.save(recipe);
        }

        workbook.close();
    }

    // 셀 데이터를 리스트로 변환하는 메서드
    private List<String> getListFromCell(Cell cell) {
        if (cell == null || cell.getStringCellValue().isEmpty()) {
            return Collections.emptyList();
        }

        String cellValue = cell.getStringCellValue().trim();

        if (cellValue.startsWith("[") && cellValue.endsWith("]")) {
            cellValue = cellValue.substring(1, cellValue.length() - 1);
        }

        return Arrays.asList(cellValue.split(",\\s*"));
    }

    private List<String> getCellValue(Cell cell) {
        if (cell == null || cell.getStringCellValue().isEmpty()) {
            return Collections.emptyList();
        }

        String cellValue = cell.getStringCellValue().trim();

        if (cellValue.startsWith("[") && cellValue.endsWith("]")) {
            cellValue = cellValue.substring(1, cellValue.length() - 1); // 대괄호 제거
        }

        return Arrays.asList(cellValue);
    }
}