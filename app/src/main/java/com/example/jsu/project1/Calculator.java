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
        updateOutput("" + Float.parseFloat("-25."));
    }

    private void clear(){
        acc = 0;
        reg = 0;
        outputText = "0";
        lastOp = "bAddition";

    }

    public void onClick(View v){
        String id = (v.getResources().getResourceName(v.getId())).split("/")[1];
        switch (id){
            case "bAddition": case "bSubtraction": case "bMultiplication": case "bDivision":
                if(!(lastButton.equals("bAddition") || lastButton.equals("bSubtraction") || lastButton.equals("bMultiplication") || lastButton.equals("bDivision"))){
                    setReg(getDisplayValue(inputText));
                    performLastOp();
                }
                lastOp = id;
                clrRegOnCnctnt = true;
                break;

            case "bSignChange":
                negate();
                break;
            /*
            case "bDecimalPt":
                break;
            */

            case "bPercent":
                percentage();
                clrRegOnCnctnt
                break;

            case "bSquareRoot":
                sqrt();
                break;

            case "bClear":
                clear();
                break;

            case "bEquals":
                equals();
                break;

            default:
                lastButton = id;
                concatenate(id.charAt(1));
                updateOutput(inputText);
                break;
        }


        lastOp = "something";
        updateOutput();

    }

    private void getEntryType(){

    }

    private void performLastOp(){
        if (lastOp.equals("")){
            setAcc(reg);
        }
        else{
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
        }
        outputText = "" + acc;
        updateOutput();
    }


    private void concatenate(char c) {
        if (clrRegOnCnctnt){
            inputText = "" + c;
            clrRegOnCnctnt = false;
        }
        if (!(c == '.' && hasDecimal(inputText))){inputText = inputText + c;}
        outputText = inputText;
        updateOutput();
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

    }

    private void percentage(){
        float mltplr = 1;
        if(lastOp == "bAddition" || lastOp == "bSubtraction"){mltplr = mltplr * acc;}
        reg = 

    }

    private void equals(){
        //perform last operation with acc and reg (call method)
        performLastOp();
        //updateOutput();
        clrRegOnCnctnt = true;
    }

    private void updateOutput(){
        TextView outputWindow = findViewById(R.id.output);
        if(hasDecimal(outputText)){outputWindow.setText(outputText);}
        else{outputWindow.setText(outputText+ '.');}

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
        /*
        if(input.charAt(0) == '-'){
            input = input.substring(1);
            return 0 - Float.parseFloat(input);
        }
        */
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
