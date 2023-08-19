package com.example.chew_chewassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginSignUpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up_screen);
    }
    public void ToCusUI(View v){
        Intent newActivityIntent = new Intent(LoginSignUpScreen.this, MainActivity.class);
        startActivity(newActivityIntent);
    }
    public void ToRegUI(View v){
        Intent newActivityIntent = new Intent(LoginSignUpScreen.this, Registration.class);
        startActivity(newActivityIntent);
    }
    public void ToStaffUI(View V){
        Intent newActivityIntent = new Intent(LoginSignUpScreen.this, StaffLoginUI.class);
        startActivity(newActivityIntent);
    }
}