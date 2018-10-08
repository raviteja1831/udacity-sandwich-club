package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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


    TextView mPlaceOfOriginTextView;
    TextView mAlsoKnownAsTextView;
    TextView mDescriptionTextView;
    TextView mIngredientsTextView;

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
        mPlaceOfOriginTextView = findViewById(R.id.origin_tv);
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);

        if(!sandwich.getPlaceOfOrigin().isEmpty()) {
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            mPlaceOfOriginTextView.setText(R.string.defaultPlaceOfOrigin);
        }

        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnownAsTextView.setText(populateStringBuilderFromList(sandwich.getAlsoKnownAs()));
        } else {
            // Thought of doing this way but realized that would leave inconsistent user-experience finding a piece of info for one food item but not other so decided to leave some text than taking it out
//            TextView mAlsoKnownAsLabel = findViewById(R.id.also_known_label);
//            mAlsoKnownAsLabel.setVisibility(View.INVISIBLE);

            mAlsoKnownAsTextView.setText(R.string.defaultAlsoKnownText);
        }


        mDescriptionTextView.setText(sandwich.getDescription());

        mIngredientsTextView.setText(populateStringBuilderFromList(sandwich.getIngredients()));

    }

    //moved out as I violated DRY principle repeating same loop to build custom string

    @NonNull
    private String populateStringBuilderFromList(List<String> items) {
        StringBuilder builder = new StringBuilder();
        for (String item : items) {
            builder.append(item).append("\n");
        }
        return builder.toString();
    }
}
