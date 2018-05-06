package com.example.int_systems.gmb_app;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.int_systems.gmb_app.GMB_services.DatabaseHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText loginID,loginPassword;
    Button signin;
    TextView Newuser;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginID = (EditText) findViewById(R.id.in_username);
        loginPassword =(EditText) findViewById(R.id.in_password);

        Newuser =(TextView) findViewById(R.id.newUser);
        Newuser.setOnClickListener(this);
        signin =(Button) findViewById(R.id.btn_login);
        signin.setOnClickListener(this);

        //myDb = new DatabaseHelper(this);
    }


    @Override
    public void onClick(View view) {
        if (view == signin){
            proccessLogin();
        }
        if (view==Newuser){
            Intent i = new Intent(MainActivity.this, Registration.class);
            MainActivity.this.startActivity(i);
        }
    }

    public void proccessLogin() {
        final String userID = loginID.getText().toString();
        final String password = loginPassword.getText().toString();

        if (TextUtils.isEmpty(userID)) {
            loginID.setError("Identification field must not be empty");
            return;
            // Toast.makeText(this,"Username must not be empty",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)){
            loginPassword.setError("Password field must not be empty");
        }
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userID", userID);
        params.put("password", password);
        client.post("http://10.0.2.2/GMB/ANDROID/loginProccessor.php", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("++++++++++system failed+++++++++++");

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                if (responseString.equalsIgnoreCase("true")) {
                    Toast.makeText(MainActivity.this,"Registration was successfull",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    MainActivity.this.startActivity(intent);



                } else // If username and password does not match display a error message
                    if (responseString.equalsIgnoreCase("false"))
                        Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                    else if (responseString.equalsIgnoreCase("exception") || responseString.equalsIgnoreCase("unsuccessful")) {

                        Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                    }

            }

        });
        }

}
