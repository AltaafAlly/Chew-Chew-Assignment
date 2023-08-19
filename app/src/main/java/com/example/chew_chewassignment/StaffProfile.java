package com.example.chew_chewassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class StaffProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_profile);
        TextView StaffDisplayName = (TextView) findViewById(R.id.StaffNameHere);
        StaffDisplayName.setText(GlobalVariables.name);
    }
    public void logoutbtn(View V){
        Intent login = new Intent(StaffProfile.this, StaffLoginUI.class);
        startActivity(login);
        GlobalVariables.count = 0;
        GlobalVariables.name =" ";
        finish();
    }
}