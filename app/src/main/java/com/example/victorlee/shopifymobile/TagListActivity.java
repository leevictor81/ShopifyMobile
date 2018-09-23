package com.example.victorlee.shopifymobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

public class TagListActivity extends AppCompatActivity {
    private static final Logger log = Logger.getLogger(TagListActivity.class.getName());

    List<String> tags;
    JSONArray jsonProductArray;
    ShopifyProductReader shopifyProductReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);

        tags = new ArrayList<>();
        jsonProductArray = new JSONArray();
        shopifyProductReader = new ShopifyProductReader();

        initializeAllTags();
        addAllTagButtons();
    }

    private void initializeAllTags() {
        try {
            Set<String> setOfTags = new TreeSet<>();
            jsonProductArray = shopifyProductReader.getProducts();

            for (int i = 0; i < jsonProductArray.length(); i++) {
                try {
                    JSONObject product = jsonProductArray.getJSONObject(i);
                    String tags = product.getString("tags");
                    addTagsFromString(tags, setOfTags);
                } catch (JSONException e) {
                    log.warning("Can't get product " + i + " in the array.");
                }
            }

            tags.addAll(setOfTags);
        } catch (Exception e) {
            log.warning("Couldn't get list of products");
        }
    }

    private void addTagsFromString(String tags, Set<String> setOfTags) {
        List<String> tagsList = Arrays.asList(tags.split(","));
        tagsList = removeSpaceForStringList(tagsList);

        for (String tag : tagsList) {
            setOfTags.add(tag);
        }
    }

    private void addAllTagButtons() {
        LinearLayout listOfTags = findViewById(R.id.list_of_tag);

        for (String tag : tags) {
            getLayoutInflater().inflate(R.layout.tag_button, listOfTags);
            Button profileButton = listOfTags.findViewWithTag("default_tag_button");
            profileButton.setTag(tag);
            profileButton.setText(tag);
        }
    }

    public void toProductList(View view) {
        Button button = (Button) view;
        String tag = button.getText().toString();
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra("tag", tag);

        intent.putParcelableArrayListExtra("listOfProducts", getListOfProductForTag(tag));

        startActivity(intent);
    }

    private ArrayList<Product> getListOfProductForTag(String tag) {
        ArrayList<Product> listOfProducts = new ArrayList<>();

        for (int i = 0; i < jsonProductArray.length(); i++) {
            try {
                JSONObject product = jsonProductArray.getJSONObject(i);

                String title = product.getString("title");
                String tags = product.getString("tags");
                List<String> tagsList = Arrays.asList(tags.split(","));
                tagsList = removeSpaceForStringList(tagsList);
                int amountOfInventory = product.getJSONArray("variants").length();
                String imageSrc = product.getJSONObject("image").getString("src");

                if (tagsList.contains(tag)) {
                    listOfProducts.add(new Product(title, tagsList, amountOfInventory, imageSrc));
                }
            } catch (JSONException e) {
                log.warning("Can't get product " + i + " in the array.");
            }
        }

        return listOfProducts;
    }

    private List<String> removeSpaceForStringList(List<String> tagsList) {
        List<String> spacelessList = new ArrayList<>();

        for(String tag : tagsList) {
            spacelessList.add(tag.replaceAll("\\s+",""));
        }

        return spacelessList;
    }
}
