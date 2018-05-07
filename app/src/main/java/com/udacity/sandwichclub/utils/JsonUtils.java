package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        // define constant strings
        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";

        // extract JSON data and build Sandwich from JSON data
        Sandwich sandwich = null;
        try {
            JSONObject event = new JSONObject(json);
            JSONObject name = event.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);
            JSONArray alsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }

            String placeOfOrigin = event.getString(PLACE_OF_ORIGIN);

            String description = event.getString(DESCRIPTION);

            String image = event.getString(IMAGE);

            JSONArray ingredients = event.getJSONArray(INGREDIENTS);
            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.getString(i));
            }
            sandwich = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
