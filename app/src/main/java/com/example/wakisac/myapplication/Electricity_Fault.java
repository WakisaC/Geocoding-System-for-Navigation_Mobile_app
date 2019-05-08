package com.example.wakisac.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Electricity_Fault extends AppCompatActivity {

    EditText  fault_description, meterNum;
    String district,d_area;
    private Spinner spinner,spinner2;
    Button button;
    AlertDialog.Builder builder;
    String server_url = "http://192.168.43.222/connect.php";
    String server_url2 = "http://192.168.43.161:3003/report";

    DatabaseHelper myDb;

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

        myDb = new DatabaseHelper(this);
        fault_description = (EditText)findViewById(R.id.fault);
        meterNum = (EditText)findViewById(R.id.meterNumber);

        builder = new AlertDialog.Builder(Electricity_Fault.this);
        button  = (Button)findViewById(R.id.reportButton);


        // implement the spiner here
        //get the spinner from the xml.
        spinner = ( Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
//create a list of items for the spinner.
        String[] districts = new String[]{"Balaka", "Blantyre", "Chikwawa","Chiradzulu","Chitipa", "Dedza",
                "Dowa", "Karonga","Kasungu","Likoma", "Lilongwe","Machinga","Mangochi",
                "Mchinji","Mulanje","Mwanza","Mzimba","Neno","Nkhata Bay","Nkhotakota","Nsanje",
                "Nsanje", "Ntchisi","Phalombe","Rumphi","Salima","Thyolo","Zomba"};

//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, districts);

//set the spinners adapter to the previously created one.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Balaka")){
                    display_balaka();


                }else if (parent.getItemAtPosition(position).equals("Blantyre")){
                    display_blantyre();
                    //String item = parent.getItemAtPosition(position).toString();


                }else if (parent.getItemAtPosition(position).equals("Zomba")){
                    display_zomba();
                    //String item = parent.getItemAtPosition(position).toString();


                }else{
                    String item = parent.getItemAtPosition(position).toString();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


          button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                final String fault  = fault_description.getText().toString();
                final String meter = meterNum.getText().toString();

                //get name from sqlite database
                final String name = myDb.getName();
                final long phone = myDb.getPhone();
                //convert phone to string
                final String userPhone = String.valueOf(phone);


                // check and validate user input
                if (TextUtils.isEmpty(meter)){
                    Toast.makeText(Electricity_Fault.this, "Enter meter number please!!", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(fault)){
                    Toast.makeText(Electricity_Fault.this, "Enter fault details please!!", Toast.LENGTH_SHORT).show();

                }
                else {

                    // Show user to ensure intended details to be submitted
                    builder.setTitle("Proceed to report From :");
                    builder.setMessage("Location : " + spinner.getSelectedItem().toString()+ " "+ spinner2.getSelectedItem().toString());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            builder.setTitle("RESPONSE");
                                            builder.setMessage("Fault Report Successful!!");
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int which) {
                                                    mainActivity();

                                                }
                                            });

                                            AlertDialog alertDialog = builder.create();
                                            alertDialog.show();
                                        }
                                    }
                                    , new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Electricity_Fault.this, "Error....", Toast.LENGTH_SHORT).show();
                                    error.printStackTrace();

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String,String>params = new HashMap<String, String>();

                                    params.put("name",name);
                                    params.put("phone",userPhone);
                                    params.put("fault_description",fault);
                                    params.put("meterNum",meter);
                                    params.put("spinner",spinner.getSelectedItem().toString());
                                    params.put("spinner2",spinner2.getSelectedItem().toString());

                                    return params;
                                }
                            };
                            Mysingleton.getmInstance(Electricity_Fault.this).addToRequestque(stringRequest);

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }


            }
        });



    }


    public void mainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    // functions defining areas in districts

    public void display_blantyre(){
        //create areas for bt
        String[] zombaAreas = new String[]{"Limbe","Naperi","Namiwawa","Makata","Sunny Side",};
        ArrayAdapter<String> zomba = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, zombaAreas);
        zomba.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(zomba);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Limbe")){

                }else {
                    String item = parent.getItemAtPosition(position).toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void display_balaka(){
        String tex;
        //create areas for balaka
        String[] balakaAreas = new String[]{"Chingeni"};
        ArrayAdapter<String> zomba = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, balakaAreas);
        zomba.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(zomba);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Chingeni")){

                }else {
                    String item = parent.getItemAtPosition(position).toString();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    public void display_zomba(){
        String tex;
        //create areas for zomba
        String[] zombaAreas = new String[]{"Chancellor College","Matawale","Nandolo","Old Naisi","Sadzi",};
        ArrayAdapter<String> zomba = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, zombaAreas);
        zomba.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(zomba);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Chancellor College")){

                }else {
                    String item = parent.getItemAtPosition(position).toString();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
}