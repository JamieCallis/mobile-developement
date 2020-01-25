package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button tempButton = null;
    TextView tempTextView = null;
    TextView tempTextView2 = null;
    TextView operator = null;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempTextView = findViewById(R.id.txtInput);
        tempTextView2 = findViewById(R.id.txtStoredValue);
        operator = findViewById(R.id.txtOperator);

    }


    public void onDigitClick(View view) {
        tempButton = (Button) view;
        tempTextView.append(tempButton.getText());
        counter++;
        tempButton = null;
    }




    public void onEqualsClick(View view) {



        if(!TextUtils.isEmpty(tempTextView.getText()) && !TextUtils.isEmpty(tempTextView2.getText()) ) {
            calculate(operator.getText().toString(), Double.parseDouble(tempTextView.getText().toString()), Double.parseDouble(tempTextView2.getText().toString()));
        }
    }

    public void onDecimalClick(View view) {
        tempButton = (Button) view;

        String check = tempTextView.getText().toString();

        if(TextUtils.isEmpty(check)) {
            Toast decimalToast = Toast.makeText(getApplicationContext(), "Equals has been clicked.", Toast.LENGTH_SHORT);
            decimalToast.show();
        } else {

            tempTextView.append(tempButton.getText());
            tempButton.setClickable(false);
            tempButton.setEnabled(false);
        }
    }

    public void onDeleteClick(View view) {

        String check = tempTextView.getText().toString();

        if(TextUtils.isEmpty(check)) {
            Toast deleteToast = Toast.makeText(getApplicationContext(), "No value to delete", Toast.LENGTH_SHORT);
            deleteToast.show();
        } else {
            CharSequence newSequence = check.subSequence(0, (counter - 1));
            tempTextView.setText(newSequence);
            counter--;

            String tempString = tempTextView.getText().toString();

            if(!tempString.contains(".")) {
                tempButton = findViewById(R.id.btn_decimal);
                tempButton.setClickable(true);
                tempButton.setEnabled(true);
            }
        }

    }

    public void onOperatorsClick(View view) {

        tempButton = (Button) view;
        // conversion from charSequence to String
        String operatorCheck = tempButton.getText().toString();
        String valueCheck = tempTextView.getText().toString();
        String checkStoredValue = tempTextView2.getText().toString();
        Double value1;
        Double value2 ;

        if(TextUtils.isEmpty(valueCheck) && TextUtils.isEmpty(checkStoredValue)) {
            Toast operatorsClick = Toast.makeText(getApplicationContext(), "Enter a value first.", Toast.LENGTH_SHORT);
            operatorsClick.show();

        } else if(TextUtils.isEmpty(valueCheck) && !TextUtils.isEmpty(checkStoredValue)) {
            Toast operatorsClick = Toast.makeText(getApplicationContext(), "Enter a value to perform an operation.", Toast.LENGTH_SHORT);
            operatorsClick.show();
        } else if (!TextUtils.isEmpty(valueCheck) && TextUtils.isEmpty(checkStoredValue)) {
            tempTextView2.append(tempTextView.getText());
            operator.setText(tempButton.getText());
            tempTextView.setText("");
        } else {
            value1 = Double.parseDouble(tempTextView.getText().toString());
            value2 = Double.parseDouble(tempTextView2.getText().toString());
            calculate(operatorCheck, value1, value2);
        }

        tempButton = null;

    }

    // Separate the calculate function since it's used by two methods.
    private void calculate(String operatorCheck, double value1, double value2) {
        switch(operatorCheck) {
            case "*":
                multiply(value1, value2);
                break;
            case "+":
                add(value1, value2);
                break;
            case "-":
                minus(value1, value2);
                break;
            case "/":
                devide(value1, value2);
                break;
        }
        operator.setText("");
    }


    private void multiply(double value, double value2) {
        double newValue = value * value2;
        tempTextView2.setText("");
        tempTextView.setText( Double.toString(newValue));

    }

    private void add(double value, double value2) {
        double newValue = value + value2;
        tempTextView2.setText("");
        tempTextView.setText( Double.toString(newValue));
    }

    private void minus(double value, double value2) {
        double newValue = value - value2;
        tempTextView2.setText("");
        tempTextView.setText( Double.toString(newValue));
    }

    private void devide(double value, double value2) {
        if(value != 0 ) {
            double newValue = value / value2;
            tempTextView2.setText("");
            tempTextView.setText(Double.toString(newValue));
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Attempted to divide by zero.",Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
