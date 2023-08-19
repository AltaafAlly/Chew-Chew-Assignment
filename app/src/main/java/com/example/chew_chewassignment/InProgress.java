package com.example.chew_chewassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InProgress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_progress);
    }
    public void ToCustomerUI(View v){
        Intent reload = new Intent(InProgress.this, CustomerUI.class);
        startActivity(reload);
    }
}