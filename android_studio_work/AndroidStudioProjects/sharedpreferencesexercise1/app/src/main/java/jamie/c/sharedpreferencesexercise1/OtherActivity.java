package jamie.c.sharedpreferencesexercise1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class OtherActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs;
        TextView textView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_activity);

        Context context = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        String savedString = prefs.getString("StringKey", "");
        int savedInteger = prefs.getInt("IntegerKey", 0);

        textView = findViewById(R.id.editTextSavedString);
        textView.setText(String.valueOf(savedString));

        textView = findViewById(R.id.editTextSavedInteger);
        textView.setText(String.valueOf(savedInteger));

    }

    public void onReturnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
