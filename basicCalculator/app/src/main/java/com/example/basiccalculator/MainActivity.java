package com.example.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;//library for calculate math Operations
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.input);
        display.setShowSoftInputOnFocus(false);//disable the default keyboard of the pop-up window so that we can use the application keyboard
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getString(R.string.display).equals(display.getText().toString())){//check if the text from the resource file (strings.xml) matches the input.//
                    display.setText("");
                }
            }
        });
    }
    //---Display---//
    //Update display text according wherever the cursor position is
    private void updateText(String strToAdd){//add strings from keyboard
        String oldStr = display.getText().toString();//get old string
        int cursorPos = display.getSelectionStart();//get cursor position
        String leftStr = oldStr.substring(0 , cursorPos);//place older string at left
        String rightStr = oldStr.substring(cursorPos);//place new string at right
        if(getString(R.string.display).equals(display.getText().toString())){//check inputText string if is different from default-->(0), if is true  then
            display.setText(strToAdd);//update input text with user string
            display.setSelection(cursorPos + 1);//init cursor at right
        }else{//else if we have already character to the screen
            display.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));//display all strings
            display.setSelection(cursorPos + 1);//init cursor at right
        }
    }
    //---Number Buttons---//
    public void zeroBTN(View view){
        updateText("0");
    }
    public void oneBTN(View view){
        updateText("1");
    }
    public void twoBTN(View view){
        updateText("2");
    }
    public void threeBTN(View view){
        updateText("3");
    }
    public void fourBTN(View view){
        updateText("4");
    }
    public void fiveBTN(View view){
        updateText("5");
    }
    public void sixBTN(View view){
        updateText("6");
    }
    public void sevenBTN(View view){
        updateText("7");
    }
    public void eightBTN(View view){
        updateText("8");
    }
    public void nineBTN(View view){
        updateText("9");
    }
    public void clearBTN(View view){
        display.setText("");
    }
    //---math expression buttons---//
    public void parenthesisBTN(View view){
        int cursorPos = display.getSelectionStart();
        int openPar = 0;
        int closedPar = 0;
        int textLen = display.getText().length();

        for(int i = 0; i< cursorPos; i++){
            if(display.getText().toString().substring(i, i+1).equals("(")){openPar +=1;}
            if(display.getText().toString().substring(i, i+1).equals(")")){closedPar +=1;}
        }
        if (openPar == closedPar || display.getText().toString().substring(textLen-1, textLen).equals("(")){
            updateText("(");
            display.setSelection(cursorPos + 1);
        }else if (closedPar < openPar && !display.getText().toString().substring(textLen-1, textLen).equals(")")){
            updateText(")");
            display.setSelection(cursorPos + 1);
        }
    }
    public void exponentBTN(View view){
        updateText("^");
    }
    public void divideBTN(View view){
        updateText("÷");
    }
    public void multiplyBTN(View view){
        updateText("×");
    }
    public void subtractBTN(View view){
        updateText("-");
    }
    public void addBTN(View view){
        updateText("+");
    }
    public void equalsBTN(View view){
        String userExp = display.getText().toString();//get math Expression from keyboard
        /*if the mathematical expression typed by the user is one of the following,
        then return the corresponding character for the calculation*/
        userExp = userExp.replaceAll("÷","/");
        userExp = userExp.replaceAll("×","*");
        Expression exp = new Expression(userExp);//Create expression object for mxparser library
        String result = String.valueOf(exp.calculate());//return calculation result
        display.setText(result);//display result
        display.setSelection(result.length());//init cursor
    }
    public void pointBTN(View view){updateText(".");}
    public void plusMinusBTN(View view){updateText("-");}
    //--Backspace Button--//
    public void backspaceBTN(View view){
        int cursorPos = display.getSelectionStart();//get the beginning of the string
        int textLen = display.getText().length();//get the length of the string

        if(cursorPos != 0 && textLen !=0){//check position cursor and string length is not equal to zero
            SpannableStringBuilder selection = (SpannableStringBuilder) display.getText();
            selection.replace(cursorPos - 1, cursorPos, "");//replace left string
            display.setText(selection);//display the new selection string
            display.setSelection(cursorPos - 1);//places the cursor at the position where one of the characters in the string was deleted
        }
    }
}