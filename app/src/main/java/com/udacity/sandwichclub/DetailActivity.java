package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static TextView originTextView;
    private static TextView descriptionTextView;
    private static TextView ingredientsTextView;
    private static TextView alsoKnownAsTextView;
    private String TAG = DetailActivity.this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        originTextView = (TextView) findViewById(R.id.origin_tv);
        descriptionTextView = (TextView) findViewById(R.id.description_tv);
        ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        alsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin.length() > 0) {
            originTextView.setText(sandwich.getPlaceOfOrigin());
        }
        //Log.v(TAG, "place of origin: " + sandwich.getPlaceOfOrigin());

        String description = sandwich.getDescription();
        if (description.length() > 0) {
            descriptionTextView.setText(sandwich.getDescription());
        }
        //Log.v(TAG, "description: " + sandwich.getDescription());

        List<String> ingredients = sandwich.getIngredients();
        StringBuilder sb = new StringBuilder();
        for (String ingredient : ingredients) {
            sb.append(ingredient + ", ");
        }
        String ingredientsString = sb.toString();
        if (ingredientsString.length() > 0) {
            ingredientsTextView.setText(ingredientsString);
        }
        //Log.v(TAG, "ingredients: " + ingredientsString);

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        StringBuilder sb2 = new StringBuilder();
        for (String alsoKnownAsItem : alsoKnownAsList) {
            sb2.append(alsoKnownAsItem + ", ");
        }
        String alsoKnownAsString = sb2.toString();
        if (alsoKnownAsString.length() > 0) {
            alsoKnownAsTextView.setText(alsoKnownAsString);
        }
        //Log.v(TAG, "also known as: " + alsoKnownAsString);

    }
}
