package com.example.victorlee.shopifymobile;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Victor Lee on 9/20/2018.
 */

public class ShopifyProductReader {
    private final static String SHOPIFY_API_URL = "https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";

    private final static OkHttpClient client = new OkHttpClient();
    private JSONArray listOfProducts;

    public JSONArray getProducts() throws Exception {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    ResponseBody responseBody = httpResponse(SHOPIFY_API_URL);
                    String jsonData = responseBody.string();
                    listOfProducts = new JSONObject(jsonData).getJSONArray("products");
                } catch (Exception ignored) {}
            }
        });

        thread.start();
        thread.join();
        return listOfProducts;
    }

    private ResponseBody httpResponse(String url) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body();
    }
}
