package com.example.chew_chewassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;


public class Registration extends AppCompatActivity {
    EditText CUST_NAME, CUST_EMAIL, CUST_USERNAME, CUST_PASS;
    String name, user_name, email, password;
    String finalResult ;
    String HttpURL = "https://lamp.ms.wits.ac.za/~s2424551/register.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        CUST_NAME = (EditText)findViewById(R.id.fullname);
        CUST_EMAIL = (EditText)findViewById(R.id.email);
        CUST_USERNAME = (EditText)findViewById(R.id.username);
        CUST_PASS = (EditText)findViewById(R.id.password);
    }
    /*
    public void ToLogin(View v){
        Intent newActivityIntent = new Intent(Registration.this, MainActivity.class);
        startActivity(newActivityIntent);
    }
    */

    public boolean CheckEditTextIsEmptyOrNot(){

        name = CUST_NAME.getText().toString();
        user_name = CUST_USERNAME.getText().toString();
        email = CUST_EMAIL.getText().toString();
        password = CUST_PASS.getText().toString();


        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(user_name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {

            return  false;

        }
        else {

            return true ;
        }

    }

    public void userReg(View v){
        if(CheckEditTextIsEmptyOrNot() == false){
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }
        else{
            name = CUST_NAME.getText().toString();
            user_name = CUST_USERNAME.getText().toString();
            email = CUST_EMAIL.getText().toString();
            password = CUST_PASS.getText().toString();
            /*
            String method="register";
            BackgroundTask backgroundTask=new BackgroundTask (this);
            backgroundTask.execute (method, name, user_name,email , password);
            finish();
             */
            UserRegisterFunction(name,user_name,email, password);
        }


    }
    public void UserRegisterFunction(final String name, final String user_name, final String email, final String password){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Registration.this,"Loading Data",null,true,true);

                /*Intent newActivityIntent = new Intent(Registration.this, MainActivity.class);
                startActivity(newActivityIntent);*/

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(Registration.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("name",params[0]);

                hashMap.put("user_name",params[1]);

                hashMap.put("email",params[2]);

                hashMap.put("password",params[3]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(name,user_name,email,password);
    }
    public void ToMainMenu(View v ){
        Intent newActivityIntent = new Intent(Registration.this, LoginSignUpScreen.class);
        startActivity(newActivityIntent);
    }
    public void ToLogin(View v ){
        Intent newActivityIntent = new Intent(Registration.this, MainActivity.class);
        startActivity(newActivityIntent);
    }

}

