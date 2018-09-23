package com.example.victorlee.shopifymobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

public class ProductListActivity extends AppCompatActivity {
    private static final Logger log = Logger.getLogger(ProductListActivity.class.getName());

    String tag;
    List<Product> listOfProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        tag = getIntent().getStringExtra("tag");
        listOfProducts = getIntent().getParcelableArrayListExtra("listOfProducts");

        setTagText();
        addAllProductViews();
    }

    private void setTagText() {
        TextView tagTitle = findViewById(R.id.tag_title);
        tagTitle.setText(tag);
    }

    private void addAllProductViews() {
        LinearLayout listOfTags = findViewById(R.id.product_list);

        for (Product product : listOfProducts) {
            getLayoutInflater().inflate(R.layout.product_box, listOfTags);
            LinearLayout productBox = listOfTags.findViewWithTag("empty_product_box");
            ImageView productImage = productBox.findViewById(R.id.product_image);
            TextView productTitle = productBox.findViewById(R.id.product_title);
            TextView productCount = productBox.findViewById(R.id.product_count);

            Picasso.get().load(product.getImageSrc()).into(productImage);
            productBox.setTag(product.getTitle());
            productTitle.setText(product.getTitle());
            productCount.setText("Count: " + product.getAmountOfInventory());
        }
    }
}
