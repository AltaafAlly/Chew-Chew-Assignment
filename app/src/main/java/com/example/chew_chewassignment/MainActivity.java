package com.example.chew_chewassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import render.animations.Attention;
import render.animations.Render;

public class MainActivity extends AppCompatActivity {
    EditText ET_NAME,ET_PASS;

    private String username, password;
    private String URLforCustomer = "https://lamp.ms.wits.ac.za/~s2424551/logincustomer.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ET_NAME = (EditText)findViewById(R.id.username);
        ET_PASS = (EditText)findViewById(R.id.password);
    }
    public void ToRegister(View v){
        Intent newActivityIntent = new Intent(MainActivity.this, Registration.class);
        startActivity(newActivityIntent);
    }
    public void ToMainMenu(View v){
        Intent newActivityIntent = new Intent(MainActivity.this, LoginSignUpScreen.class);
        startActivity(newActivityIntent);
    }
    public void ToStaffLogin(View v){
        Intent newActivityIntent = new Intent(MainActivity.this, StaffLoginUI.class);
        startActivity(newActivityIntent);
    }
    public void CustomerUI(View view) {
        username = ET_NAME.getText().toString();
        password = ET_PASS.getText().toString();
        if(!username.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLforCustomer, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("res", response);
                    if (response.equals("success")) {
                        Intent intent = new Intent(MainActivity.this, CustomerUI.class);
                        intent.putExtra("user_name",username);
                        startActivity(intent);
                        finish();
                    } else if (response.equals("failure")) {
                        TextInputLayout t = findViewById(R.id.textInputLayoutUsername);
                        Render render = new Render(MainActivity.this);
                        render.setAnimation(Attention.Shake(t));
                        render.start();
                        TextInputLayout b = findViewById(R.id.textInputLayoutPassword);
                        Render r = new Render(MainActivity.this);
                        r.setAnimation(Attention.Shake(b));
                        r.start();
                        Toast.makeText(MainActivity.this, "Invalid Login Username/Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("user_name", username);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }
    }


}