package com.example.diceroller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static int noRoll = 0;
    public static boolean text=false;
    public static final String diceValue = "diceVal";
    public static final String pastValue = "pastVal";
    public static final String sidesArrayStr = "sidesArrayStr";
    public static boolean rollTwo = false;
    public static ArrayList<String> listItems = new ArrayList<String>();
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedpreferences = getSharedPreferences("values", Context.MODE_PRIVATE);

        String val = sharedpreferences.getString(diceValue,"");
        String pastVal = sharedpreferences.getString(pastValue,"");
        String arrayStr = sharedpreferences.getString(sidesArrayStr,"2@4@6@8@10");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Switch switch1 = findViewById(R.id.switch1);
        Switch switch2 = findViewById(R.id.switch2);
        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch2.isChecked()){
                    rollTwo=true;
                }
                else{
                    rollTwo=false;
                }
            }
        });
        EditText sideET = findViewById(R.id.sidesET);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch1.isChecked()){
                    text=true;
                }
                else {
                    text=false;
                }
            }
        });

        //Spinner
        String[] araySides = arrayStr.split("@");
        for (int i =0;i<araySides.length;i++){
            listItems.add(araySides[i]);
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinnerDice);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        TextView pastValuesTW = findViewById(R.id.pastValuesTW);

        pastValuesTW.setText(pastVal);
        //for TextView
        TextView rollTW = findViewById(R.id.rollTW);
        rollTW.setText(val);
        //for Button
        Button rollButton = findViewById(R.id.rollbutton);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                int _random = (int) ((Math.random() * Integer.parseInt(spinner.getSelectedItem().toString()) + 1));
                if (noRoll >= 5) {
                    noRoll = 0;
                    pastValuesTW.setText("");
                }
                noRoll += 1;

                if(rollTwo){
                    if(text){
                        if(!listItems.contains(""+sideET.getText().toString())){
                            listItems.add(0,sideET.getText().toString());
                            adapter.notifyDataSetChanged();
                            spinner.setAdapter(adapter);

                        }
                        String sideText = sideET.getText().toString();
                        if (sideText!="") {
                            try{
                                _random = (int) ((Math.random() * Integer.parseInt(sideText) + 1));
                                int _random2 = (int) ((Math.random() * Integer.parseInt(sideText) + 1));
                                rollTW.setText(_random+", " + _random2);
                                pastValuesTW.setText(pastValuesTW.getText().toString()+_random+", "+_random2+", ");
                            }
                            catch (Exception e ){
                                System.out.println(e);
                                _random = 0;
                                sideET.setError("Enter Number");
                                sideET.requestFocus();
                            }
                        }
                        else {
                            _random = 0;
                            sideET.setError("Enter Number");
                            sideET.requestFocus();
                        }
                    }
                    else {

                        _random = (int) ((Math.random() * Integer.parseInt(spinner.getSelectedItem().toString()) + 1));
                        int _random2 = (int) ((Math.random() * Integer.parseInt(spinner.getSelectedItem().toString()) + 1));
                        rollTW.setText(_random+", " + _random2);
                        pastValuesTW.setText(pastValuesTW.getText().toString()+_random+", "+_random2+", ");

                    }
                }
                else
                {
                    if(text){
                        if(!listItems.contains(""+sideET.getText().toString())){

                            listItems.add(0,sideET.getText().toString());
                            adapter.notifyDataSetChanged();
                            spinner.setAdapter(adapter);

                        }
                        String sideText = sideET.getText().toString();
                        if (sideText!="") {
                            try{
                                _random = (int) ((Math.random() * Integer.parseInt(sideText) + 1));
                                rollTW.setText(_random+", " );
                                pastValuesTW.setText(pastValuesTW.getText().toString()+_random+", ");
                            }
                            catch (Exception e ){
                                System.out.println(e);
                                _random = 0;
                                sideET.setError("Enter Number");
                                sideET.requestFocus();
                            }
                        }
                        else {
                            _random = 0;
                            sideET.setError("Enter Number");
                            sideET.requestFocus();
                        }
                    }
                    else {
                        _random = (int) ((Math.random() * Integer.parseInt(spinner.getSelectedItem().toString()) + 1));
                        rollTW.setText(""+_random);
                        pastValuesTW.setText(pastValuesTW.getText().toString()+_random+", ");
                    }


                }

            }
        });






    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onPause(){
        super.onPause();
        TextView rollTW = findViewById(R.id.rollTW);
        TextView pastValuesTW = findViewById(R.id.pastValuesTW);
        sharedpreferences = getSharedPreferences("values", Context.MODE_PRIVATE);

        String arrayToStr = String.join("@", listItems);;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(sidesArrayStr,arrayToStr);
        editor.putString(pastValue, (String) pastValuesTW.getText());
        editor.putString(diceValue, (String) rollTW.getText());
        editor.commit();


    }

}