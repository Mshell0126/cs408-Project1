package com.example.jsu.project1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.math.*;
import android.widget.*;

public class Calculator extends AppCompatActivity {
    private float acc;
    private float reg;

    private String inputText;
    private String outputText;
    private String lastOp;
    private String lastButton;

    private boolean clrOnConcat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        clear();
        lastButton = "bClear";
        updateOutput();
    }

    private void clear(){
        setAcc(0);
        setReg(0);
        clrOnConcat = false;
        inputText = "";
        outputText = "0";
        lastOp = "";

    }

    public void onClick(View v){
        String id = (v.getResources().getResourceName(v.getId())).split("/")[1];
        if(outputText.equals("error")){clear();}
        switch (id){
            case "bAddition": case "bSubtraction": case "bMultiplication": case "bDivision":
                if(clrOnConcat){
                    lastOp = "";
                }
                if(!(lastButton.equals("bAddition") || lastButton.equals("bSubtraction") || lastButton.equals("bMultiplication") || lastButton.equals("bDivision"))){
                    performLastOp();
                    clrOnConcat = true;
                }
                lastOp = id;
                break;

            case "bSignChange":
                negate();
                setReg();
                break;
            /*
            case "bDecimalPt":
                break;
            */

            case "bPercent":
                percentage();
                setReg();
                clrOnConcat = true;
                break;

            case "bSquareRoot":
                sqrt();
                setReg();
                clrOnConcat= true;
                break;

            case "bClear":
                clear();
                break;

            case "bEquals":
                eql();
                clrOnConcat = true;
                break;

            default:
                concatenate(id.charAt(1));
                setReg();
                break;
        }

        lastButton = id;
        updateOutput();

    }

    private void performLastOp(){
        switch (lastOp){
            case "bAddition":
                add();
                break;
            case "bSubtraction":
                subtract();
                break;
            case "bMultiplication":
                multiply();
                break;
            case "bDivision":
                divide();
                break;
            default:
                if(!lastButton.equals("bEquals")){setAcc(reg);}
                break;

            }

        outputText = "" + acc;
    }


    private void concatenate(char c) {
        if (clrOnConcat){
            if (lastButton.equals("bEquals")){lastOp = "";}
            inputText = "" + c;
            clrOnConcat = false;
        }
        else if (!(c == '.' && hasDecimal(inputText))){inputText = (String) inputText + c;}
        outputText = inputText;

    }


    private void add(){
        setAcc(acc + reg);
    }

    private void subtract(){
        setAcc(acc - reg);
    }

    private void multiply(){ setAcc(acc * reg); }

    private void divide(){
        try {
            setAcc(acc / reg);
        }
        catch(Exception e) {
            updateOutput("error");
            /*
            if (e.getCause().equals("division by zero")) {
                updateOutput("undefined");
                //update screen with error. CHECK HOW TO EXAMINE ERROR TYPES!
            } else {
                updateOutput("err");

            }
            */
        }
    }

    private void negate(){
        float num = Float.parseFloat(outputText);
        num = 0 - num;
        inputText = outputText = "" + num;
        if(lastButton.equals("bEquals")){setAcc(num);}
        else{setReg();}
    }

    private void sqrt(){
        if(lastButton.equals("bEquals")){
            setReg(acc);
            setAcc(0);
        }
        reg = (float) Math.sqrt(reg);
        outputText = "" + reg;

    }

    private void percentage(){
        if(lastButton.equals("bEquals")){
            setReg(acc);
            setAcc(0);
        }
        float mltplr = 1;
        if(lastOp == "bAddition" || lastOp == "bSubtraction"){mltplr = mltplr * acc;}
        reg = mltplr * reg / 100;
        outputText = "" + reg;

    }

    private void eql(){
        clrOnConcat = true;
        performLastOp();
        outputText = "" + acc;


    }

    private void updateOutput(){
        TextView outputWindow = findViewById(R.id.output);
        if(hasDecimal(outputText)){outputWindow.setText(outputText);}

        else{outputWindow.setText("" + Integer.parseInt(outputText) + '.');}

    }

    private void updateOutput(String message){
        TextView outputWindow = findViewById(R.id.output);
        outputWindow.setText(message);
    }

    private boolean isInt(float num){
        return(num % 1 == 0);
    }

    //private boolean hasDecimal(int num){return hasDecimal("" + num);}

    //don't delete this one
    private boolean hasDecimal(String num){

        return (num.indexOf('.') > 0);
    }

    private void setReg(){
        float value = Float.parseFloat((inputText));
        setReg(value);
    }

    private void setReg(float num){
        reg = num;
    }

    private void setAcc(float num){
        acc = num;
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
