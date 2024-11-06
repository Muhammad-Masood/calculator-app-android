package com.example.calculatorjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b0 = findViewById(R.id.button_0);
        Button b1 = findViewById(R.id.button_1);
        Button b2 = findViewById(R.id.button_2);
        Button b3 = findViewById(R.id.button_3);
        Button b4 = findViewById(R.id.button_4);
        Button b5 = findViewById(R.id.button_5);
        Button b6 = findViewById(R.id.button_6);
        Button b7 = findViewById(R.id.button_7);
        Button b8 = findViewById(R.id.button_8);
        Button b9 = findViewById(R.id.button_9);
        Button bAc = findViewById(R.id.button_ac);
        Button bAdd = findViewById(R.id.button_add);
        Button bSub = findViewById(R.id.button_subtract);
        Button bMul = findViewById(R.id.button_multiply);
        Button bDiv = findViewById(R.id.button_divide);
        Button bEq = findViewById(R.id.button_equals);
        Button bc = findViewById(R.id.button_c);
        Button bDecimal = findViewById(R.id.button_decimal);
        display = findViewById(R.id.display);

        // Set onClickListeners for number buttons
        setNumberButtonListener(b0);
        setNumberButtonListener(b1);
        setNumberButtonListener(b2);
        setNumberButtonListener(b3);
        setNumberButtonListener(b4);
        setNumberButtonListener(b5);
        setNumberButtonListener(b6);
        setNumberButtonListener(b7);
        setNumberButtonListener(b8);
        setNumberButtonListener(b9);

        bAdd.setOnClickListener(v -> appendValueIntoDisplay(v));
        bSub.setOnClickListener(v -> appendValueIntoDisplay(v));
        bMul.setOnClickListener(v -> appendValueIntoDisplay(v));
        bDiv.setOnClickListener(v -> appendValueIntoDisplay(v));
        bc.setOnClickListener(v -> clearLastVal());

        bAc.setOnClickListener(v -> display.setText(""));

        bEq.setOnClickListener(v -> solveEquation());

        bDecimal.setOnClickListener(v -> appendValueIntoDisplay(v));
    }

    private void setNumberButtonListener(Button button) {
        button.setOnClickListener(v -> appendValueIntoDisplay(v));
    }

    // Append value into the display
    private void appendValueIntoDisplay(View v) {
        CharSequence buttonText = ((Button) v).getText();

        // Check if it's the decimal button
        if (".".equals(buttonText.toString())) {
            // Only append decimal if there is no decimal already in the current input
            String displayText = display.getText().toString();
            if (!displayText.contains(".")) {
                display.append(buttonText);
            }
            return;
        }

        // Avoid appending multiple 'AC' or invalid input
        if ("AC".equals(buttonText.toString())) {
            display.setText("");
            return;
        }

        display.append(buttonText);
    }

    private void clearLastVal(){
        String displayText = display.getText().toString();
        int displayTextLength = displayText.length();
        if(displayTextLength > 0) {
            String filteredText = displayText.substring(0, displayTextLength - 1);
            display.setText(filteredText);
        }
    }

    private void solveEquation() {
        String equation = display.getText().toString();
        Log.d("Equation", "Equation to solve: " + equation);

        if (!equation.isEmpty()) {
            try {
                // Create an Expression object using Exp4j
                Expression expression = new ExpressionBuilder(equation)
                        .build();

                // Evaluate the result
                double result = expression.evaluate();

                // Display the result
                if (!equation.contains("/") && result == (int) result) {
                    display.setText(String.valueOf((int) result));
                } else {
                    display.setText(String.valueOf(result));
                }
            } catch (Exception e) {
                display.setText("Error");
            }
        }
    }
}
