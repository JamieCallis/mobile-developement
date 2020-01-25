package jamie.c.sharedpreferencesexercise1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSaveClcik(View view) {

        Context context = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        String stringToSave = "";
        int integerToSave = 0;

        textView = findViewById(R.id.editTextString);
        stringToSave = textView.getText().toString();

        try
        {
            textView = findViewById(R.id.editTextInteger);
            integerToSave = Integer.parseInt(textView.getText().toString());
        }
        catch (Exception e)
        {
            Toast toast = Toast.makeText(this, "Integer field does not represent an integer", Toast.LENGTH_LONG);

            toast.show();
            textView.setSelected(true);
            textView.setText("");
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("StringKey", stringToSave);
        editor.putInt("IntegerKey", integerToSave);
        editor.apply();
    }

    public void switchActivity(View view) {
        Intent intent = new Intent(this, OtherActivity.class);
        startActivity(intent);
    }
}
