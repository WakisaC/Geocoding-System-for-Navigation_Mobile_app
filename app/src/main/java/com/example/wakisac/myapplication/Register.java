package com.example.wakisac.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    //create database object
    DatabaseHelper myDb;
    Button startButton;
    private ProgressBar pgsBar;
    AlertDialog.Builder builder;
   // String server_url = "https://192.168.43.67:3001/api/report_fault"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //execute user registration
        //////// create an SQLITE database object/////////
        myDb = new DatabaseHelper(this);
        startButton=(Button)findViewById(R.id.save);
        builder = new AlertDialog.Builder(Register.this);
        pgsBar = (ProgressBar) findViewById(R.id.pBar);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                results();

            }
        });


    }

    public void gotoMain(View view){
        mainActivity();
    }

    public void mainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void results(){
        EditText username =(EditText) findViewById(R.id.name);
        EditText phoneInput =(EditText) findViewById(R.id.phone);
        final String name = username.getText().toString();
        String userPhone = phoneInput.getText().toString();

        //validate user input
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter your name!!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(userPhone)){
            Toast.makeText(this, "Enter your phone number!!", Toast.LENGTH_SHORT).show();

        }
        else {
            final int phone = Integer.parseInt(userPhone);
       //convert the string for phone to integer

            // add data to the database in phone
           // add(name,phone);

            final ProgressDialog dialog = new ProgressDialog(this); // this = YourActivity
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("");
            dialog.setMessage("Creating user...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    addData(name,phone);
                    dialog.dismiss();
                }
            }, 5000);

        }
    }

    //add name and phone number to  database
    public void addData(final String name,final int phone){
        boolean isInserted = myDb.insertData(name,phone);
        if (isInserted =true){
            builder.setTitle(" User Created Successfully");
            builder.setMessage(" Name :  "+ name +"\n Phone : "+ phone);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ///// go to general assessment///////
                    mainActivity();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else {
            builder.setTitle("User Creation");
            builder.setMessage("Response : Sorry Error occured!! Try again " );
            builder.setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

}
