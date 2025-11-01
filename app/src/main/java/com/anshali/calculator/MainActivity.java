package com.anshali.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class MainActivity extends AppCompatActivity {
    private MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonAdd, buttonSub, buttonMul, buttonDiv, buttonDec, buttonAC, buttonPer, buttonSign, buttonDel, buttonEqual;
    private TextView inputNum;
    private Stack<String> tokens = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            inputNum.setAutoSizeTextTypeUniformWithConfiguration(
                    18,
                    60,
                    1,
                    TypedValue.COMPLEX_UNIT_SP
            );
        } */

        initViews();

        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.button_click);

        @SuppressLint("ClickableViewAccessibility") View.OnTouchListener buttonTouchListener = (v, event) -> {
          if(event.getAction() == MotionEvent.ACTION_DOWN) {
              v.startAnimation(scaleAnim);
          }
          return false;
        };

        MaterialButton[] numberButtons = {button0, button1, button2, button3, button4, button5, button6, button7, button8, button9};
        MaterialButton[] operatorButtons = {buttonAdd, buttonSub, buttonMul, buttonDiv};

        for(int i=0; i<numberButtons.length; i++) {
            numberButtons[i].setOnTouchListener(buttonTouchListener);
        }

        for(int i=0; i<operatorButtons.length; i++) {
            operatorButtons[i].setOnTouchListener(buttonTouchListener);
        }

        buttonEqual.setOnTouchListener(buttonTouchListener);
        buttonDel.setOnTouchListener(buttonTouchListener);
        buttonAC.setOnTouchListener(buttonTouchListener);
        buttonPer.setOnTouchListener(buttonTouchListener);
        buttonSign.setOnTouchListener(buttonTouchListener);
        buttonDec.setOnTouchListener(buttonTouchListener);

        for(int i=0; i<numberButtons.length; i++) {
            int finalI = i;
            numberButtons[i].setOnClickListener(v -> {
                if(tokens.isEmpty() || isOperator(tokens.peek())) {
                    tokens.push(String.valueOf(finalI));
                }
                else {
                    String curr = tokens.pop();
                    curr = curr + finalI;
                    tokens.push(curr);
                }

                inputNum.setText(String.join(" ", tokens));
            });
        }

        buttonDec.setOnClickListener(v -> {
            if(tokens.isEmpty()) {
                tokens.push("0.");
            }
            else {
                String lastNum = tokens.peek();

                if(isNumber(lastNum)) {
                    if (!lastNum.contains(".")) {
                        tokens.pop();
                        tokens.push(lastNum + ".");
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Already has decimal point", Toast.LENGTH_SHORT).show();
                        tokens.push(lastNum);
                    }
                }
                else if(isOperator(lastNum)) {
                    tokens.push("0.");
                }
            }

            inputNum.setText(String.join(" ", tokens));
        });

        for(int i=0; i<operatorButtons.length; i++) {
            int finalI = i;
            String op;

            if(finalI == 0) op = "+";
            else if(finalI == 1) op = "-";
            else if(finalI == 2) op = "x";
            else op = "÷";

            String finalOp = op;

            operatorButtons[i].setOnClickListener(v -> {
                if(tokens.isEmpty()) {
                    tokens.push("0");
                    tokens.push(finalOp);
                }
                else {
                    String lastToken = tokens.peek();

                    if (isNumber(lastToken) || lastToken.endsWith(".")) {
                        tokens.push(finalOp);
                    }
                    else if(isOperator(lastToken)) {
                        tokens.pop();
                        tokens.push(finalOp);
                    }
                }

                inputNum.setText(String.join(" ", tokens));
            });
        }

        buttonSign.setOnClickListener(v -> {
            if(tokens.isEmpty()) return;

            if(isNumber(tokens.peek())) {
                String lastNum = tokens.pop();

                if(lastNum.startsWith("-")) {
                    lastNum = lastNum.substring(1);
                } else {
                    lastNum = "-" + lastNum;
                }

                tokens.push(lastNum);
            }

            else if(isOperator(tokens.peek())) {
                String lastOp = tokens.pop();

                if(!tokens.isEmpty()) {
                    if(tokens.peek().equals("0")) {
                        tokens.push(lastOp);
                        tokens.push("-0");
                    }
                    else if(isNumber(tokens.peek())) {
                        String lastNum = tokens.peek();
                        tokens.push(lastOp);

                        if(lastNum.startsWith("-")) tokens.push(lastNum.substring(1));
                        else tokens.push("-"+lastNum);
                    }
                }

            }

            inputNum.setText(String.join(" ", tokens));
        });

        buttonPer.setOnClickListener(v -> {
            if(!tokens.isEmpty()) {

                if(isNumber(tokens.peek())) {
                     double lastNum = Double.parseDouble(tokens.pop());
                     lastNum = lastNum/100.0;
                     tokens.push(String.valueOf(lastNum));
                }
                else if(isOperator(tokens.peek())) {
                    String lastOp = tokens.pop();

                    if(!tokens.isEmpty() && isNumber(tokens.peek())) {
                        Double lastNum = Double.parseDouble(tokens.peek());
                        lastNum = lastNum/100.0;
                        tokens.push(lastOp);
                        tokens.push(String.valueOf(lastNum));
                    }
                }
            }

            inputNum.setText(String.join(" ", tokens));
        });

        buttonAC.setOnClickListener(v -> {
            tokens.clear();
            inputNum.setText("");
            inputNum.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
        });

        buttonDel.setOnClickListener(v -> {
            if(!tokens.isEmpty()) {
                String lastToken = tokens.pop();
                if(lastToken.length() > 1) {
                    tokens.push(lastToken.substring(0, lastToken.length()-1));
                }
            }

            inputNum.setText(String.join(" ", tokens));
        });

        buttonEqual.setOnClickListener(v -> {
            try {
                List<String> postfix = infixToPostfix(tokens);
                double result = evaluatePostfix(postfix);

                if(result == Math.floor(result)) {
                    inputNum.setText(String.valueOf((long) result));
                    tokens.clear();
                    tokens.push(String.valueOf((long) result));
                } else {
                    inputNum.setText(String.valueOf(result));
                    tokens.clear();
                    tokens.push(String.valueOf(result));
                }

            } catch (Exception e) {
                inputNum.setText("Error");
            }
        });

    }

    private int precedence(String op) {
        switch (op) {
            case "+": case "-": return 1;
            case "x": case "÷": return 2;
            default: return 0;
        }
    }

    private List<String> infixToPostfix(List<String> tokens) {
        Stack<String> stack = new Stack<>();
        List<String> postfix = new ArrayList<>();

        for(String token : tokens) {
            if(isNumber(token)) postfix.add(token);

            else if(isOperator(token)) {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(token)) {
                    postfix.add(stack.pop());
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) postfix.add(stack.pop());
        return postfix;
    }

    private double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for(String token : postfix) {
            if(isNumber(token)) stack.push(Double.parseDouble(token));

            else if(isOperator(token)) {
                double b = stack.pop();
                double a = stack.pop();

                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "x": stack.push(a * b); break;
                    case "÷": stack.push(a/b); break;
                }
            }
        }

        return stack.pop();
    }

    public boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("x") || token.equals("÷");
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
        // return token.matches("-?\\d+(\\.\\d+)?");   //for simple calculations
    }

    public void initViews() {

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAC = findViewById(R.id.buttonAC);
        buttonDec = findViewById(R.id.buttonDot);
        buttonDel = findViewById(R.id.buttonDelete);
        buttonDiv = findViewById(R.id.buttonDivide);
        buttonEqual = findViewById(R.id.buttonEquals);
        buttonMul = findViewById(R.id.buttonMultiply);
        buttonPer = findViewById(R.id.buttonPercent);
        buttonSign = findViewById(R.id.buttonSign);
        buttonSub = findViewById(R.id.buttonSubtract);

        inputNum = findViewById(R.id.editTextNumber);
    }
}