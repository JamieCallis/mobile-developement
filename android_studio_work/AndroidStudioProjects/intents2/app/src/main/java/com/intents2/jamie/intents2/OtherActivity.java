package com.intents2.jamie.intents2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceStatus) {
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.other_activitiy);

        Toast toast = Toast.makeText(OtherActivity.this, "OtherActivbity", Toast.LENGTH_SHORT);
        toast.show();

        Bundle bundle = getIntent().getExtras();
        String receivedMessage = bundle.getString("Message");
        int receivedInteger = bundle.getInt("ExtraInteger");

        TextView txtMessageReceived = (TextView) findViewById(R.id.txtMessageReceived);
        txtMessageReceived.setText(receivedMessage);

        TextView txtIntegerReceived = (TextView) findViewById(R.id.txtIntegerReceived);
        txtIntegerReceived.setText(Integer.toString(receivedInteger));
    }

    public void btnReturnOnClick(View view) {
        finish();
    }
}


