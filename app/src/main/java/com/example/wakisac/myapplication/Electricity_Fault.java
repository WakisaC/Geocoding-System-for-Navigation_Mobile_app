package com.example.wakisac.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Electricity_Fault extends AppCompatActivity {

    private Spinner spinner,spinner2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity__fault);
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

        //get the spinner from the xml.
        spinner = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
//create a list of items for the spinner.
        String[] items = new String[]{"Balaka", "Blantyre", "Chikwawa","Chiradzulu","Chitipa", "Dedza",
                                        "Dowa", "Karonga","Kasungu","Likoma", "Lilongwe","Machinga","Mangochi",
                                        "Mchinji","Mulanje","Mwanza","Mzimba","Neno","Nkhata Bay","Nkhotakota","Nsanje",
                                        "Nsanje", "Ntchisi","Phalombe","Rumphi","Salima","Thyolo","Zomba"};

//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("District")){
                    //do nothing
                }else {
                    String item = parent.getItemAtPosition(position).toString();

                    //show selected spinner item
                    Toast.makeText(Electricity_Fault.this, "SElected" + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        }

}
