package com.example.NaengTulCook.service;

import com.example.NaengTulCook.dto.ChatGPTResponse;
import com.example.NaengTulCook.entity.Cook;
import com.example.NaengTulCook.entity.Recipe;
import com.example.NaengTulCook.entity.User;
import com.example.NaengTulCook.repository.CookRepository;
import com.example.NaengTulCook.repository.RecipeRepository;
import com.example.NaengTulCook.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CookService {

    @Autowired
    private CookRepository cookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Transactional
    public Cook saveCookedRecipe(int userId, String excludedIngredients, List<String> ingredients, String time, String difficulty, String taste, String diet, String calorieRange, String recipeName) {

        String prompt = buildPrompt(recipeName, ingredients , time, difficulty, taste, diet, calorieRange);

        User user = userRepository.findById(userId).get();
        Recipe recipe = recipeRepository.findByName(recipeName);

        Cook cook = new Cook();
        String newRecipe = openAIService.getRecipeFromOpenAI(prompt);
        ChatGPTResponse chatGPTResponse = openAIService.convertToChatGPTResponse(newRecipe);

        buildResponseFormat(chatGPTResponse,cook);

        cook.setUser(user);
        cook.setRecipe(recipe);
        cook.setFilter1Except(excludedIngredients);
        cook.setFilter2Time(time);
        cook.setFilter3Level(difficulty);
        cook.setFilter4Flavor(taste);
        cook.setFilter5Diet(diet);
        cook.setFilter6Calorie(calorieRange);

        cookRepository.save(cook);

        return cook;
    }

    private String buildPrompt(String recipeName, List<String> ingredients, String time, String difficulty, String taste, String diet, String calorieRange) {
        StringBuilder prompt = new StringBuilder("다음 조건에 맞춰 레시피를 변형해 주세요:\n");

        if (recipeName != null && !recipeName.isEmpty()) {
            prompt.append("요리 이름: ").append(recipeName).append("\n");
        }

        prompt.append("다음 재료로만 음식을 만들어야 합니다.: ").append(ingredients).append("\n");


        if (!time.isEmpty()) {
            prompt.append("요리 시간은 ").append(time).append("이어야 합니다.\n");
        }

        if (!difficulty.isEmpty()) {
            prompt.append("요리 난이도는 ").append(difficulty).append("이어야 합니다.\n");
        }

        if (!taste.isEmpty()) {
            prompt.append("요리의 맛은 ").append(taste).append("이어야 합니다.\n");
        }

        if (!diet.isEmpty()) {
            prompt.append("레시피는 ").append(diet).append(" 다이어트 옵션에 맞춰야 합니다.\n");
        }

        if (!calorieRange.isEmpty()) {
            prompt.append("칼로리는 ").append(calorieRange).append(" 범위여야 합니다.\n");
        }

        prompt.append("\n다음 형식으로 레시피를 반환해주세요 (쉼표로 구분된 문장으로 작성):\n");
        prompt.append("새로운 조리법: 첫 번째 조리법, 두 번째 조리법\n");
        prompt.append("새로운 간 맞추기: 첫 번째 간 맞추기, 두 번째 간 맞추기\n");
        prompt.append("새로운 플레이팅: 첫 번째 플레이팅, 두 번째 플레이팅\n");
        prompt.append("각 조리법, 간 맞추기, 플레이팅은 5문장 이내여야 합니다\n");
        prompt.append("각 항목에는 넘버링을 합니다.\n");
        prompt.append("각 항목은 '다'로 끝나는 평서문으로 끝나도록 해주세요.\n");

        return prompt.toString();
    }


    private void buildResponseFormat(ChatGPTResponse response, Cook cook) {

        List<ChatGPTResponse.Choice> lines = response.getChoices();

        for (ChatGPTResponse.Choice choice : lines) {
            String content = choice.getMessage().getContent();

            String newCook = extractSection(content, "새로운 조리법:");
            String newSeasoning = extractSection(content, "새로운 간 맞추기:");
            String newPlating = extractSection(content, "새로운 플레이팅:");


            cook.setNewCook(newCook);
            cook.setNewSeasoning(newSeasoning);
            cook.setNewPlating(newPlating);
        }
    }

    private String extractSection(String response, String sectionTitle) {
        int startIndex = response.indexOf(sectionTitle);
        if (startIndex == -1) {
            return "";
        }

        int endIndex = response.indexOf("새로운", startIndex + sectionTitle.length());
        if (endIndex == -1) {
            endIndex = response.length();
        }

        String section = response.substring(startIndex + sectionTitle.length(), endIndex).trim();
        return section.replaceAll("\n", ", ").trim();
    }
}
