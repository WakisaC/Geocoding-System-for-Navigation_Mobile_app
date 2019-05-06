package com.example.wakisac.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Launcher extends AppCompatActivity {
    DatabaseHelper mydb;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mydb =new DatabaseHelper(this);
        button=(Button)findViewById(R.id.startButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_user();
            }
        });
    }
        public void mainActivity(){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        public void register(){
            Intent intent = new Intent(this,Register.class);
            startActivity(intent);
        }

        //method that checks if users exist in database

    public void check_user(){

       boolean existence = mydb.user_exists();

       if (existence==true)
          mainActivity();
       else {
           register();
       }
    }


}
