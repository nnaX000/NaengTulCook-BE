package com.example.NaengTulCook.service;

import com.example.NaengTulCook.dto.IngredientDTO;
import com.example.NaengTulCook.entity.Ingredient;
import com.example.NaengTulCook.entity.Refrigerator;
import com.example.NaengTulCook.entity.User;
import com.example.NaengTulCook.repository.IngredientRepository;
import com.example.NaengTulCook.repository.RefrigeratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RefrigeratorService {

    @Autowired
    private RefrigeratorRepository refrigeratorRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public void saveSelectedIngredients(int userId, List<String> selectedIngredients) {
        Refrigerator refrigerator = refrigeratorRepository.findByUserId(userId);

        if (refrigerator == null) {
            refrigerator = new Refrigerator();
            User user = new User();
            user.setId(userId);
            refrigerator.setUser(user);
        }

        List<String> ingredients = refrigerator.getIngredient() != null ? refrigerator.getIngredient() : new ArrayList<>();
        List<String> condiments = refrigerator.getCondiments() != null ? refrigerator.getCondiments() : new ArrayList<>();

        for (String selectedIngredient : selectedIngredients) {
            Ingredient ingredient = ingredientRepository.findByName(selectedIngredient);
            if (ingredient != null) {
                String category = ingredient.getCategory().trim();
                if (category.equals("곡물") || category.equals("과일") || category.equals("냉동식품")
                        || category.equals("유제품") || category.equals("육류") || category.equals("채소") || category.equals("해산물")) {
                    ingredients.add(selectedIngredient);
                } else if (category.equals("조미료")) {
                    condiments.add(selectedIngredient);
                }
            }
        }

        refrigerator.setIngredient(ingredients);
        refrigerator.setCondiments(condiments);

        refrigeratorRepository.save(refrigerator);
    }

    public boolean removeIngredient(int userId, String ingredientName) {
        // 사용자에 해당하는 냉장고 정보 찾기
        Refrigerator refrigerator = refrigeratorRepository.findByUserId(userId);

        if (refrigerator == null) {
            return false;  // 해당 사용자 정보가 없으면 실패
        }

        // 기존 ingredients와 condiments 리스트 가져오기
        List<String> ingredients = refrigerator.getIngredient();
        List<String> condiments = refrigerator.getCondiments();

        // 재료가 ingredients에 있으면 삭제
        if (ingredients != null && ingredients.contains(ingredientName)) {
            ingredients.remove(ingredientName);
        }

        // 재료가 condiments에 있으면 삭제
        if (condiments != null && condiments.contains(ingredientName)) {
            condiments.remove(ingredientName);
        }

        // 삭제된 리스트를 다시 저장
        refrigerator.setIngredient(ingredients);
        refrigerator.setCondiments(condiments);

        // 변경 사항 저장
        refrigeratorRepository.save(refrigerator);

        return true;  // 삭제 성공
    }

    // 사용자 냉장고에 담은 음식 재료를 가져오기 (이름, 사진 포함)
    public List<IngredientDTO> getIngredientsByUser(int userId) {
        Refrigerator refrigerator = refrigeratorRepository.findByUserId(userId);

        if (refrigerator == null || refrigerator.getIngredient().isEmpty()) {
            return Collections.emptyList();
        }

        List<String> ingredientNames = refrigerator.getIngredient();

        List<IngredientDTO> ingredientDTOs = new ArrayList<>();
        for (String ingredientName : ingredientNames) {
            Ingredient ingredient = ingredientRepository.findByName(ingredientName);

            if (ingredient != null) {
                ingredientDTOs.add(new IngredientDTO(ingredient.getName(), ingredient.getPicture()));
            }
        }

        return ingredientDTOs;
    }

    // 사용자 냉장고에 담은 조미료 재료를 가져오기 (이름, 사진 포함)
    public List<IngredientDTO> getCondimentsByUser(int userId) {
        Refrigerator refrigerator = refrigeratorRepository.findByUserId(userId);

        if (refrigerator == null || refrigerator.getIngredient().isEmpty()) {
            return Collections.emptyList();
        }

        List<String> ingredientNames = refrigerator.getCondiments();

        List<IngredientDTO> ingredientDTOs = new ArrayList<>();
        for (String ingredientName : ingredientNames) {
            Ingredient ingredient = ingredientRepository.findByName(ingredientName);

            if (ingredient != null) {
                ingredientDTOs.add(new IngredientDTO(ingredient.getName(), ingredient.getPicture()));
            }
        }

        return ingredientDTOs;
    }
}