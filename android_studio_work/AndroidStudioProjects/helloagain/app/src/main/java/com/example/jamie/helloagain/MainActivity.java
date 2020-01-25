package com.example.jamie.helloagain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // display a message
        Toast toast = Toast.makeText(this, "Hello", Toast.LENGTH_SHORT);
        toast.show();

        // Display a message for longer
        toast = Toast.makeText(this, "Hello Again", Toast.LENGTH_LONG);
        toast.show();
    }
}
