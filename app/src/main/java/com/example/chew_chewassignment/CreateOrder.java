package com.example.chew_chewassignment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.time.*;


@RequiresApi(api = Build.VERSION_CODES.O)
public class CreateOrder extends AppCompatActivity {
    EditText Cust_name, Staff_name;
    String Staff_Name, Cust_Name, Restuarant, Status;
    Spinner Restuarant_Spinner,Status_Spinner;
    LocalTime time = LocalTime.now();
    String  myString= time.toString();
    String OrderTime = myString.substring(0,myString.length()-7);


    /*

    */
    String finalResult ;
    String HttpURL = "https://lamp.ms.wits.ac.za/~s2424551/createorder.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParseOrder httpParseOrder = new HttpParseOrder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(time);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        Staff_name = (EditText)findViewById(R.id.STAFF_NAME);
        Cust_name = (EditText)findViewById(R.id.CUST_NAME);

        Status_Spinner = findViewById(R.id.spinner4);
        Restuarant_Spinner = findViewById(R.id.spinner3);

        String[] items = new String[]{"McDonalds", "Mochachos", "Burger King","Pizza Hut"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        Restuarant_Spinner.setAdapter(adapter);




        String[] items2 = new String[]{"Pending", "In Progress", "Delivered"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);

        Status_Spinner.setAdapter(adapter2);
    }
    public boolean CheckEditTextIsEmptyOrNot(){
        Cust_Name = Cust_name.getText().toString();
        Staff_Name = Staff_name.getText().toString();




        if(TextUtils.isEmpty(Staff_Name) || TextUtils.isEmpty(Cust_Name) )
        {

            return  false;

        }
        else {

            return true ;
        }

    }

    public void Create_Order(View v){
        if(CheckEditTextIsEmptyOrNot() == false){
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }
        else {
            Staff_Name = Staff_name.getText().toString();
            Cust_Name = Cust_name.getText().toString();
            Restuarant = Restuarant_Spinner.getSelectedItem().toString();
            Status = Status_Spinner.getSelectedItem().toString();
            CreateOrderBackground(Staff_Name, Cust_Name, Restuarant, Status,OrderTime);

            Intent newActivityIntent = new Intent(CreateOrder.this, StaffUI.class);
            startActivity(newActivityIntent);
            //Intent reload = new Intent(StaffUI.this, StaffUI.class);
            //startActivity(reload);

        }
    }

    public void CreateOrderBackground(final String Cust_Name, final String Staff_Name, final String Restuarant, final String Status, final String Time){

        class CreateOrderClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(CreateOrder.this,"Loading Data",null,true,true);



            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(CreateOrder.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("cust_name",params[0]);

                hashMap.put("staff_name",params[1]);

                hashMap.put("res",params[2]);

                hashMap.put("status",params[3]);

                hashMap.put("time",params[4]);





                finalResult = httpParseOrder.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        CreateOrderClass n = new CreateOrderClass();

        n.execute(Staff_Name,Cust_Name,Restuarant,Status,Time);
    }




}