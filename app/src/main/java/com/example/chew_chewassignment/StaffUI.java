package com.example.chew_chewassignment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ScrollingView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class StaffUI extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();

    String name = " ";
    String order_no;

    //Variables:
    private String URLforCustomer = "https://lamp.ms.wits.ac.za/~s2424551/getorders.php";
    private String URLforChangeStatus = "https://lamp.ms.wits.ac.za/~s2424551/changestatus.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_ui);


        network();

        Intent intent = getIntent();
        if(GlobalVariables.count == 0){
            GlobalVariables.name = intent.getExtras().getString("name");
            GlobalVariables.count++;
        }

            name = GlobalVariables.name;




    }

    public void network(){
        ArrayList<String> order_id = new ArrayList<String>();
        ArrayList<String> cust_name = new ArrayList<String>();
        ArrayList<String> staff_name = new ArrayList<String>();
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<String> status = new ArrayList<String>();
        ArrayList<String> datetime = new ArrayList<String>();
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

                    LinearLayout layout = (LinearLayout)findViewById(R.id.linear_layout);

                    for (int i = 0; i < order_id.size(); i++){
                        CardView test = new CardView(StaffUI.this);
                        TextView t = new TextView(StaffUI.this);
                        t.setTextSize(15);
                        t.setTextColor(Color.BLACK);
                        Typeface typeface = ResourcesCompat.getFont(StaffUI.this, R.font.lato);
                        t.setTypeface(typeface);
                        t.setGravity(17);
                        String order = "Order Number: "+order_id.get(i) + "\n"+ "\n" +"Customer Name: "+ cust_name.get(i) + "\n"+ "\n" +"Staff Name: "+staff_name.get(i) +"\n"+ "\n" +"Restaurant Name: "+ res.get(i) +"\n"+ "\n" +"Collection Status: "+ status.get(i) + "\n"+ "\n"+"Date and Time: " +datetime.get(i);
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
                                order_no = order_id.get(finalI);
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
                data.put("staff_name", name);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void changeStatus(String order_no, String status){

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URLforChangeStatus, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                Log.d("res", response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("order_no", order_no);
                data.put("status", status);

                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.order_status,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Option1:
                changeStatus(order_no,"Pending");

        }
        switch (item.getItemId()){
            case R.id.Option2:
                changeStatus(order_no,"In Progress");

        }
        switch (item.getItemId()){
            case R.id.Option3:
                changeStatus(order_no,"Delivered");

        }

        return super.onContextItemSelected(item);
    }

    public void ToCreateOrder(View v){
        Intent newActivityIntent = new Intent(StaffUI.this, CreateOrder.class);
        startActivity(newActivityIntent);
    }
    public void Refresh(View v){
        Intent reload = new Intent(StaffUI.this, StaffUI.class);
        startActivity(reload);
    }
    public void ToStaffProfile(View v){
        Intent reload = new Intent(StaffUI.this, StaffProfile.class);
        startActivity(reload);
    }
    /*public void logoutbtn(View V){
        Intent login = new Intent(StaffUI.this, StaffLoginUI.class);
        startActivity(login);
        GlobalVariables.count = 0;
        GlobalVariables.name =" ";
        finish();
    }*/
}