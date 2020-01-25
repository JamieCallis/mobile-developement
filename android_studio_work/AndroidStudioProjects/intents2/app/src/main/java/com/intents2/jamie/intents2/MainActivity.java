package com.intents2.jamie.intents2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void btnSendOnClick(View view) {
        Intent intent = new Intent(this, OtherActivity.class);
        Bundle bundle = new Bundle();

        EditText txt = (EditText) findViewById(R.id.txtMessageToSend);
        bundle.putString("Message", txt.getText().toString());
        bundle.putInt("ExtraInteger", 5);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Toast toast = Toast.makeText(MainActivity.this, "MainActivity", Toast.LENGTH_SHORT);
        toast.show();
        TextView textView = (TextView) findViewById(R.id.txtActivityStatus);
        textView.setText("I'm back");
    }
}
