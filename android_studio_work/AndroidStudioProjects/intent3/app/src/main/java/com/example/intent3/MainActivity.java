package com.example.intent3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_MESSAGE_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }//onCreate

    public void btnSendOnClick(View view)
    {
        Intent intent = new Intent(this, OtherActivity.class);
        Bundle bundle = new Bundle();
        EditText txt = (EditText) findViewById(R.id.txtMessageToSend);
        bundle.putString("Message", txt.getText().toString());
        bundle.putInt("ExtraInteger", 5);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_MESSAGE_CODE);
    }//btnSendOnClick

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        TextView textView = (TextView) findViewById(R.id.txtActivityStatus);
        textView.setText("I'm back");
        Toast toast = Toast.makeText(MainActivity.this,"MainActivity",Toast.LENGTH_SHORT);
        toast.show();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case (REQUEST_MESSAGE_CODE):
                    String Message = data.getStringExtra("ReturnMessage");
                    textView.setText(Message);
                    break;
            }
        }
        else
            textView.setText("Result_OK error");
    }//onActivityResult


}
