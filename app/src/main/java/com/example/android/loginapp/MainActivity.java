package com.example.android.loginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name, email, password;
    Button signup, login;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        signup = (Button)findViewById(R.id.signup);
        login = (Button)findViewById(R.id.login);

        signup.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //signUp button clicked.
        if(view==signup) {
            submitDetails();
        }

        //login button clicked.
        else if (view==login) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void submitDetails() {

        final String username = name.getText().toString();
        final String useremail = email.getText().toString();
        final String userpassword = password.getText().toString();
        if(username.equals("")|useremail.equals("")|userpassword.equals(""))
        {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
        else
        {

            //Showing progress dialog at user registration time.
            progress=ProgressDialog.show (this,"Sending","Please wait", true );
            progress.show();

            // Creating string request with post method.
            String signupUrl="http://alcab.mywebcommunity.org/signup.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, signupUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {

                            // Hiding the progress dialog after all task complete.
                            progress.dismiss();

                            Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_SHORT).show();

                                // Showing Echo Response Message Coming From Server.
                                Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progress.dismiss();

                            // Showing error message if something goes wrong.
                            Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() {

                    // Creating Map String Params.
                    Map<String, String> params = new HashMap<String, String>();

                    // Adding All values to Params.
                    // The firs argument should be same sa your MySQL database table columns.
                    params.put("name", username);
                    params.put("email", useremail);
                    params.put("password", userpassword);

                    return params;
                }

            };

            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
    }

}


