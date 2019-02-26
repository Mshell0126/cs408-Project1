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

    private String inputText;
    private String outputText;
    private String lastOp;
    private String lastButton;

    private boolean clrRegOnCnctnt;
    //(deprecated by equalsPressed? private boolean stateTerminal; // for determining if the text view should concatenate input or replace text with input.

/*
    private enum Op{
        ADD, SUBTRACT, MULTIPLY, DIVIDE;
    }
*/

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
        clrRegOnCnctnt = false;
        clear();
        lastButton = "bClear";
        updateOutput();
    }

    private void clear(){
        acc = 0;
        reg = 0;
        clrRegOnCnctnt = true;
        inputText = "0";
        outputText = "0";
        lastOp = "bAddition";

    }

    public void onClick(View v){
        String id = (v.getResources().getResourceName(v.getId())).split("/")[1];
        switch (id){
            case "bAddition": case "bSubtraction": case "bMultiplication": case "bDivision":
                if(clrRegOnCnctnt){
                    lastOp = "";
                    setReg(0);
                }
                if(!(lastButton.equals("bAddition") || lastButton.equals("bSubtraction") || lastButton.equals("bMultiplication") || lastButton.equals("bDivision"))){
                    setReg(getDisplayValue(inputText));
                    outputText = "" + reg;
                    performLastOp();
                    clrRegOnCnctnt = true;
                }
                lastOp = id;
                break;

            case "bSignChange":
                negate();
                updateOutput();
                break;
            /*
            case "bDecimalPt":
                break;
            */

            case "bPercent":
                setReg(getDisplayValue(inputText));
                percentage();
                clrRegOnCnctnt = true;
                break;

            case "bSquareRoot":
                setReg(getDisplayValue(inputText));
                sqrt();
                clrRegOnCnctnt = true;
                break;

            case "bClear":
                clear();
                updateOutput();
                break;

            case "bEquals":
                equals();
                clrRegOnCnctnt = true;
                updateOutput();
                break;

            default:
                lastButton = id;
                concatenate(id.charAt(1));
                updateOutput();
                break;
        }

        updateOutput();

    }

    private void performLastOp(){
        setReg(getDisplayValue(inputText));
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
                break;
            }

        outputText = "" + acc;
        updateOutput();
    }


    private void concatenate(char c) {
        if (clrRegOnCnctnt){
            inputText = "" + c;
            clrRegOnCnctnt = false;
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

    private void multiply(){
        setAcc(acc * reg);

    }

    private void divide(){
        try {
            setAcc(acc / reg);
        }
        catch(Exception e) {
            if (e.getCause().equals("division by zero")) {
                updateOutput("undefined");
                //update screen with error. CHECK HOW TO EXAMINE ERROR TYPES!
            } else {
                updateOutput("err");
            }
        }
    }

    private void negate(){
        if (inputText.charAt(0) == '-') {inputText = inputText.substring(1);}
        else{inputText = "-" + inputText;}
    }

    private void sqrt(){
        reg = (float) Math.sqrt(reg);
        outputText = "" + reg;

    }

    private void percentage(){
        float mltplr = 1;
        if(lastOp == "bAddition" || lastOp == "bSubtraction"){mltplr = mltplr * acc;}
        reg = mltplr * reg / 100;
        outputText = "" + reg;

    }

    private void equals(){
        //perform last operation with acc and reg (call method)
        performLastOp();
        outputText = "" + acc;
        //updateOutput();

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

    private boolean hasDecimal(int num){
        return hasDecimal("" + num);
    }

    //don't delete this one
    private boolean hasDecimal(String num){

        return (num.indexOf('.') > 0);
    }

    private float getDisplayValue(String input){
        return Float.parseFloat(input);
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
