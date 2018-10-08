package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");

            String mainName = name.getString("mainName");

            JSONArray alsoKnownNamesJsonArray = name.getJSONArray("alsoKnownAs");

            List<String> alsoKnownNames = new ArrayList<>();
            ;
            if (alsoKnownNamesJsonArray != null) {
                for (int i = 0; i < alsoKnownNamesJsonArray.length(); i++) {
                    alsoKnownNames.add(alsoKnownNamesJsonArray.get(i).toString());
                }
            }

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");

            String description = jsonObject.getString("description");

            String imageUrl = jsonObject.getString("image");

            JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");

            List<String> ingredients = new ArrayList<>();
            if (ingredientsArray != null) {
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    ingredients.add(ingredientsArray.get(i).toString());
                }
            }

            return new Sandwich(mainName, alsoKnownNames, placeOfOrigin, description, imageUrl, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Sandwich();
    }
}
