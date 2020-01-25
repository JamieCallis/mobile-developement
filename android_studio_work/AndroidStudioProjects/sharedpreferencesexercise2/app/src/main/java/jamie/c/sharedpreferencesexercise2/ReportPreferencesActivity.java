package jamie.c.sharedpreferencesexercise2;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.TextView;

public class ReportPreferencesActivity extends AppCompatActivity {

    SharedPreferences prefs;
    TextView tv;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_preferences);

        context = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean autoUpChecked = prefs.getBoolean(PreferenceKeys.PREF_AUTO_UPDATE,
                false);
        int updateFreq = prefs.getInt(PreferenceKeys.PREF_UPDATE_FREQ, 2);
        int minMag = prefs.getInt(PreferenceKeys.PREF_MIN_MAG, 0);

        String [] updateFrequencyOptions = getResources().getStringArray(
                R.array.update_freq_options);
        String [] updateFrequencyValues = getResources().getStringArray(
                R.array.update_freq_values);
        String [] magnitudeOptions = getResources().getStringArray(
                R.array.magnitude_options);
        String [] magnitudeValues = getResources().getStringArray(
                R.array.magnitude_values);

        tv = findViewById(R.id.txtAutoUpdate);
        tv.setText(String.valueOf(autoUpChecked));

        tv = findViewById(R.id.txtRefreshFrequency);
        tv.setText(Integer.toString(updateFreq) + " => " +
                updateFrequencyOptions[updateFreq] + " => " +
                updateFrequencyValues[updateFreq]);

        tv = findViewById(R.id.txtMinimumQuakeMagnitude);
        tv.setText(Integer.toString(minMag)+ " => " +
                magnitudeOptions[minMag] + " => " +
                magnitudeValues[minMag]);

    }//onCreate

    public void onBtnOkClick( View v) {
        finish();
    }//onBtnCancelClick
}

