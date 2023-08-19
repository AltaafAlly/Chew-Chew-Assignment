package com.example.chew_chewassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Delivered extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered);
        ArrayList<String> info = new ArrayList<String>();
        //Getting stuff from previous activity
        Intent intent = getIntent();
        info = intent.getExtras().getStringArrayList("info");

        TextView t = new TextView(Delivered.this);
        t.setTextSize(15);
        t.setTextColor(Color.BLACK);
        Typeface typeface = ResourcesCompat.getFont(Delivered.this, R.font.lato);
        t.setTypeface(typeface);
        t.setGravity(17);
        String orderInfo = "Order Number: "+info.get(0) + "\n"+ "\n" +"Customer Name: "+ info.get(1) + "\n"+ "\n" +"Staff Name: "+info.get(2) +"\n"+ "\n" +"Restaurant Name: "+info.get(3) +"\n"+ "\n" +"Collection Status: "+ info.get(4) + "\n"+ "\n"+"Date and Time: " +info.get(5);
        t.setText(orderInfo);
        CardView order = findViewById(R.id.orderDetails);
        order.addView(t);
    }
    public void ToCustomerUI(View v){
        Intent reload = new Intent(Delivered.this, CustomerUI.class);
        startActivity(reload);
    }
}