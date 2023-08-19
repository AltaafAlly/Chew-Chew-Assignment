package com.example.chew_chewassignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import render.animations.Attention;
import render.animations.Render;

public class CustomerUI extends AppCompatActivity {


    String username = " ";
    String position;
    String name = "";

    //Variables:
    private String URLforCustomer = "https://lamp.ms.wits.ac.za/~s2424551/getcustorders.php";
    private String URLforName = "https://lamp.ms.wits.ac.za/~s2424551/getname.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_ui);



        GetNameNetwork();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                network();
            }
        }, 500);

        Intent intent = getIntent();
        if(GlobalVariables.count_cust == 0){
            GlobalVariables.name_cust = intent.getExtras().getString("user_name");
            GlobalVariables.count_cust++;
        }

            username = GlobalVariables.name_cust;







    }

    public void network(){
        ArrayList<String> order_id = new ArrayList<String>();
        ArrayList<String> cust_name = new ArrayList<String>();
        ArrayList<String> staff_name = new ArrayList<String>();
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<String> status = new ArrayList<String>();
        ArrayList<String> datetime = new ArrayList<String>();
        ArrayList<String> info = new ArrayList<String>();
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URLforCustomer, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                Log.d("res", response);
                try {
                    JSONArray arr = new JSONArray(response);

                    for(int i = 0; i < arr.length();i++){
                        JSONObject object = arr.getJSONObject(i);
                        order_id.add(object.getString("order_id"));
                        cust_name.add(object.getString("cust_name"));
                        staff_name.add(object.getString("staff_name"));
                        res.add(object.getString("res"));
                        status.add(object.getString("status"));
                        datetime.add(object.getString("datetime"));
                    }

                    LinearLayout layout = (LinearLayout)findViewById(R.id.linear_layout_cust);

                    for (int i = 0; i < order_id.size(); i++){
                        CardView test = new CardView(CustomerUI.this);
                        TextView t = new TextView(CustomerUI.this);
                        t.setTextSize(15);
                        t.setTextColor(Color.BLACK);
                        Typeface typeface = ResourcesCompat.getFont(CustomerUI.this, R.font.lato);
                        t.setTypeface(typeface);
                        t.setGravity(17);
                        String order = "Hold to view more information"+"\n"+ "\n" +"Order Number: "+order_id.get(i)  +"\n"+ "\n" +"Restaurant Name: "+ res.get(i);
                        //"Order Number: "+order_id.get(i) + "\n"+ "\n" +"Customer Name: "+ cust_name.get(i) + "\n"+ "\n" +"Staff Name: "+staff_name.get(i) +"\n"+ "\n" +"Restaurant Name: " +"\n"+ "\n" +"Collection Status: "+ status.get(i) + "\n"+ "\n"+"Date and Time: " +datetime.get(i);
                        t.setText(order);

                        //test.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                        LinearLayout.LayoutParams L = new LinearLayout.LayoutParams(600,600);
                        L.setMargins(200,0,0,0);
                        test.setLayoutParams(L);
                        test.setPadding(500, 1000, 500,1000);
                        test.setUseCompatPadding(true);
                        test.setMinimumHeight(300);
                        test.setMinimumWidth(5000);
                        test.setRadius(16);
                        //test.setForegroundGravity(17);
                        test.addView(t);
                        registerForContextMenu(test);

                        int finalI = i;

                        test.setOnLongClickListener(new View.OnLongClickListener() {

                            @Override
                            public boolean onLongClick(View view) {
                                Render render = new Render(CustomerUI.this);
                                render.setAnimation(Attention.Pulse(test));
                                render.start();
                                Handler handle = new Handler();
                                handle.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        info.add(order_id.get(finalI));
                                        info.add(cust_name.get(finalI));
                                        info.add(staff_name.get(finalI));
                                        info.add(res.get(finalI));
                                        info.add(status.get(finalI));
                                        info.add(datetime.get(finalI));
                                        if(status.get(finalI).equals("Pending")){
                                            Intent intent = new Intent(CustomerUI.this, Pending.class);
                                            intent.putExtra("info",info);
                                            startActivity(intent);
                                            finish();
                                        }

                                        if(status.get(finalI).equals("In Progress")){
                                            Intent intent = new Intent(CustomerUI.this, Progress.class);
                                            intent.putExtra("info",info);
                                            startActivity(intent);
                                            finish();
                                        }

                                        if(status.get(finalI).equals("Delivered")){
                                            Intent intent = new Intent(CustomerUI.this, Delivered.class);
                                            intent.putExtra("info",info);
                                            startActivity(intent);
                                            finish();
                                        }
                                        System.out.println(position);

                                    }


                            },1000);
                                return false;
                            }
                        });

                        layout.addView(test);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("cust_name", name);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void GetNameNetwork() {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLforName, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("res", response);
                    name = response;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.print("This ain't working");
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("name", username);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }
    public void logoutbtn(View V){
        Intent login = new Intent(CustomerUI.this, MainActivity.class);
        startActivity(login);
        GlobalVariables.count_cust = 0;
        GlobalVariables.name_cust =" ";
        finish();
    }
    public void ToCustomerProfile(View v){
        Intent reload = new Intent(CustomerUI.this, CustomerProfile.class);
        startActivity(reload);
    }
    public void Refresh(View v){
        Intent reload = new Intent(CustomerUI.this, CustomerUI.class);
        startActivity(reload);
    }
    }


