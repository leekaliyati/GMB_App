package com.example.int_systems.gmb_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.int_systems.gmb_app.GMB_services.DatabaseHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    EditText fm_name,national_id,phonenumber,address,password1,password2;
    Button register;
    DatabaseHelper myDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fm_name =(EditText) findViewById(R.id.farmer_name);
        national_id =(EditText) findViewById(R.id.farmer_id);
        phonenumber =(EditText) findViewById(R.id.farmer_phone);
        address =(EditText) findViewById(R.id.farmer_address);
        password1 =(EditText) findViewById(R.id.in_password);
        password2 =(EditText) findViewById(R.id.in_password2);
        register =(Button) findViewById(R.id.btn_login);
        register.setOnClickListener(this);

        myDb = new DatabaseHelper(this);


    }

    @Override
    public void onClick(View view) {
        if (view==register){
            registerFarmer();
        }

    }

    private void registerFarmer() {

        final ProgressDialog progressDialog= new ProgressDialog(Registration.this);

        progressDialog.setTitle("Searching for a parking slot");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(true);
        progressDialog.show();


        final String name = fm_name.getText().toString();
        final String nat_id = national_id.getText().toString();
        final String phone = phonenumber.getText().toString();
        final String fm_add = address.getText().toString();
        final String pass1 = password1.getText().toString();
        final String pass2 = password2.getText().toString();

        //vslidationg the input fields

        if (TextUtils.isEmpty(name)) {
            fm_name.setError("Farmer name field must not be empty");
            progressDialog.dismiss();
            return;
            // Toast.makeText(this,"Username must not be empty",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(nat_id)) {
            national_id.setError("Identification field must not be empty");
            progressDialog.dismiss();
            return;
            // Toast.makeText(this,"Username must not be empty",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(phone)) {
            phonenumber.setError("Phone field must not be empty");
            progressDialog.dismiss();
            return;
            // Toast.makeText(this,"Username must not be empty",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(fm_add)) {
            address.setError("Address field must not be empty");
            progressDialog.dismiss();
            return;
            // Toast.makeText(this,"Username must not be empty",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(pass1)) {
            password1.setError("Password field must not be empty");
            progressDialog.dismiss();
            return;
            // Toast.makeText(this,"Username must not be empty",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(pass2)) {
            password2.setError("Confirm field must not be empty");
            progressDialog.dismiss();
            return;
            // Toast.makeText(this,"Username must not be empty",Toast.LENGTH_LONG).show();
        }

       if (!pass1.equals(pass2)){
            password2.setError("Password do not match");
           progressDialog.dismiss();
            return;
            }

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("f_name", name);
        params.put("National_id", nat_id);
        params.put("Phonenumber", phone);
        params.put("Address", fm_add);
        params.put("Password", pass1);


        client.post("http://10.0.2.2/GMB/ANDROID/register.php", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();


            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                progressDialog.dismiss();

                if (responseString.equalsIgnoreCase("true")) {


                    boolean isInserted = myDb.insertData(
                            nat_id
                    );
                    if (isInserted==true){
                        Toast.makeText(Registration.this, "Registration was successfull", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Registration.this, MainMenu.class);
                        Registration.this.startActivity(intent);
                    }



//                    Toast.makeText(Registration.this,"Registration was successfull",Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(Registration.this, MainMenu.class);
//                    Registration.this.startActivity(intent);

                } else // If username and password does not match display a error message
                    if (responseString.equalsIgnoreCase("false"))
                        Toast.makeText(Registration.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                    else if (responseString.equalsIgnoreCase("exception") || responseString.equalsIgnoreCase("unsuccessful")) {

                        Toast.makeText(Registration.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                    }

            }

        });





    }
    private void update(){




    }
}
