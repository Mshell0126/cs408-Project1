package com.example.jsu.project1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

public class Calculator extends AppCompatActivity {

    private float acc;
    private float reg;
    private String outputText;
    private String lastOp;

    boolean stateTerminal; // for determining if the text view should concatenate input or replace text with input.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        lastOp = "";
        clear();
    }

    public void onClick(Button b){
        String id = (b.getResources().getResourceName(b.getId())).split("/")[1];
        //insert code

        updateView();

    }

    private void concatenate(String input){
        //
    }

    private void add(){
        acc = acc + reg;
    }

    private void divide(){
        try {
            acc = reg / acc;
        }
        catch(Exception e) {
            if (e.getCause().equals("division by zero")) {
                outputText = "undefined";
                //update screen with error. CHECK HOW TO EXAMINE ERROR TYPES!
            } else {
                outputText = "err";
            }
        }
    }

    private void sqrt(){

    }

    private void percentage(){

    }

    private void equals(){
        //perform last operation with acc and reg (call method)
        updateView();
    }

    private void clear(){
        acc = 0;
        reg = 0;
        outputText = "0";


    }

    private void updateView(){
        TextView outputWindow = (TextView) findViewById(R.id.output);
        outputWindow.setText(outputText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
