package com.example.NaengTulCook.service;

import com.example.NaengTulCook.entity.Ingredient;
import com.example.NaengTulCook.repository.IngredientRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public void saveIngredientsFromExcel(String filePath) throws IOException {
        List<Ingredient> ingredients = readExcelFile(filePath);
        ingredientRepository.saveAll(ingredients);
    }

    private List<Ingredient> readExcelFile(String filePath) throws IOException {
        List<Ingredient> ingredients = new ArrayList<>();

        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
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

    public List<Ingredient> getAllIngredientsSorted() {
        return ingredientRepository.findAllByOrderByNameAsc();  // 가나다 순으로 정렬된 전체 재료
    }

    public List<Ingredient> getIngredientsByCategorySorted(String category) {
        // category에서 공백을 제거하고, 대소문자 구분 없이 검색
        System.out.println("Category received: " + category);  // 디버깅 로그
        category = category.trim(); // 공백 제거
        System.out.println("Category received 보정: " + category);  // 디버깅 로그
        return ingredientRepository.findByCategoryIgnoreCaseOrderByNameAsc(category);
    }

    public List<Ingredient> getIngredientsByNameSorted(String name) {
        return ingredientRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name);
    }
}