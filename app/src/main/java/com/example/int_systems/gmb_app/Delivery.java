package com.example.int_systems.gmb_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class Delivery extends AppCompatActivity implements View.OnClickListener {
    private Spinner grain_typ, deport_name;
    EditText no_bags, vehicle_num;
    Button btn_apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        no_bags = (EditText) findViewById(R.id.d_bags);
        vehicle_num = (EditText) findViewById(R.id.vehicle_id);
        grain_typ = (Spinner) findViewById(R.id.grain_type);
        deport_name = (Spinner) findViewById(R.id.deport);
        btn_apply = (Button) findViewById(R.id.apply);
        btn_apply.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == btn_apply) {
            proccessReg();
        }

    }

    private void proccessReg() {
        final String deliveryDeport = deport_name.getSelectedItem().toString();
        final String grain_delivered = grain_typ.getSelectedItem().toString();
        final String bags = no_bags.getText().toString();
        final String vehicle_number = vehicle_num.getText().toString();

        //vslidationg the input fiels

        if (TextUtils.isEmpty(bags)) {
            no_bags.setError("Bags field must not be empty");
            return;
            // Toast.makeText(this,"Username must not be empty",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(vehicle_number)) {
            vehicle_num.setError("Vehicle number field must not be empty");

            return;
            // Toast.makeText(this,"Username must not be empty",Toast.LENGTH_LONG).show();
        }
        Bundle extras = getIntent().getExtras();
        String farmer_id = extras.getString("farmerID");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("deport", deliveryDeport);
        params.put("grain", grain_delivered);
        params.put("bags", bags);
        params.put("vehicle_number", vehicle_number);
        params.put("farmer_id",farmer_id);
        client.post("http://10.0.2.2/GMB/ANDROID/bookdelivery.php", params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println("++++++++Successfull++++++");
                System.out.println(responseString);

                if (responseString.equalsIgnoreCase("true")) {
                    Toast.makeText(Delivery.this,"Registration was successfull",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Delivery.this, Delivery.class);
                    Delivery.this.startActivity(intent);



                } else // If username and password does not match display a error message
                    if (responseString.equalsIgnoreCase("false"))
                        Toast.makeText(Delivery.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                    else if (responseString.equalsIgnoreCase("exception") || responseString.equalsIgnoreCase("unsuccessful")) {

                        Toast.makeText(Delivery.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                    }

            }
        });
    }
}
