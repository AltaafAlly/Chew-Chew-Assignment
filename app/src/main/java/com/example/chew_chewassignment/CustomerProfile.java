package com.example.chew_chewassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CustomerProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        TextView CustNameDisplay = (TextView) findViewById(R.id.CustNameHere);
        CustNameDisplay.setText(GlobalVariables.name_cust);
    }
    public void logoutbtn(View V){
        Intent login = new Intent(CustomerProfile.this, MainActivity.class);
        startActivity(login);
        GlobalVariables.count_cust = 0;
        GlobalVariables.name_cust =" ";
        finish();
    }
}