package com.example.int_systems.gmb_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.int_systems.gmb_app.GMB_services.Mynotifications;
import com.example.int_systems.gmb_app.GMB_services.notificationAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FarmerNotifications extends AppCompatActivity {
    List<Mynotifications> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_notifications);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        String farmerID = extras.getString("farmerID");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("farmerID", farmerID);
        client.post("http://10.0.2.2/GMB/ANDROID/getNotification.php", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONArray array = new JSONArray(responseString);
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);

                        //adding the product to product list
                        productList.add(new Mynotifications(
                                product.getString("Title"),
                                product.getString("description")

                        ));
                    }
                    //creating adapter object and setting it to recyclerview
                    notificationAdapter adapter = new notificationAdapter(FarmerNotifications.this, productList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        }
    }

