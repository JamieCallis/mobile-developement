package com.example.intent3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class OtherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_activity);
        Toast toast = Toast.makeText(OtherActivity.this,"OtherActivity", Toast.LENGTH_SHORT);
        toast.show();

        Bundle bundle = getIntent().getExtras();

        String receivedMessage = bundle.getString("Message");
        int receivedInteger = bundle.getInt("ExtraInteger");

        TextView txtMessageReceived = (TextView) findViewById(R.id.txtMessageReceived);
        txtMessageReceived.setText(receivedMessage);

        TextView txtIntegerReceived = (TextView) findViewById(R.id.txtIntegerReceived);
        txtIntegerReceived.setText(Integer.toString(receivedInteger));

    }//onCreate

    public void btnReturnOnClick(View view)
    {
        Intent ret = new Intent();
        ret.putExtra("ReturnMessage", ((Bundle)getIntent().getExtras()).getString("Message") +
                " and " +
                Integer.toString(((Bundle)getIntent().getExtras()).getInt("ExtraInteger")));
        setResult(RESULT_OK, ret);
        finish();
    }//btnReturnOnClick

}
