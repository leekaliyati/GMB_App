package com.example.int_systems.gmb_app;

import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.int_systems.gmb_app.GMB_services.DatabaseHelper;

public class MainMenu extends AppCompatActivity {
    DatabaseHelper myDb;
    String farmerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        myDb = new DatabaseHelper(this);

        CardView first = (CardView) findViewById(R.id.first);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenu.this, Delivery.class);
                farmerID=theDriver();
                intent.putExtra("farmerID",farmerID);
                startActivity(intent);


            }
        });

        CardView second = (CardView) findViewById(R.id.second);
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, Payments.class);
                startActivity(intent);


            }
        });

        CardView third = (CardView) findViewById(R.id.third);
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, ContractFarming.class);
                startActivity(intent);


            }
        });

        CardView fourth = (CardView) findViewById(R.id.fourth);
        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, FarmerNotifications.class);
                farmerID=theDriver();
                intent.putExtra("farmerID",farmerID);
                startActivity(intent);


            }
        });


    }


    public String theDriver(){
        Cursor res = myDb.getDriverID();
        if (res.moveToFirst()) {
            farmerID = res.getString(1);
        }
        return farmerID;

    }
}
