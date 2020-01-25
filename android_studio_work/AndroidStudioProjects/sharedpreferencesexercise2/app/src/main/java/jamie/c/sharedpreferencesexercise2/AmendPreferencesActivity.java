package jamie.c.sharedpreferencesexercise2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

public class AmendPreferencesActivity extends AppCompatActivity {

    CheckBox autoUpdate;
    Spinner updateFreqSpinner;
    Spinner magnitudeSpinner;

    SharedPreferences prefs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amend_preferences);

        updateFreqSpinner = findViewById(R.id.spinner_update_freq);
        magnitudeSpinner = findViewById(R.id.spinner_minimum_quake_mag);
        autoUpdate = findViewById(R.id.checkBox_auto_update);

        populateSpinners();

        Context context = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        updateUIFromPreferences();
    }//onCreate

    private void populateSpinners() {
        int spinner_dd_item = android.R.layout.simple_spinner_dropdown_item;

        //Populate the update frequency spinner
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource
                (this,R.array.update_freq_options,
                        android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(spinner_dd_item);
        updateFreqSpinner.setAdapter(fAdapter);

        //Populate the minimum magnitude spinner
        ArrayAdapter<CharSequence> mAdapter;
        mAdapter = ArrayAdapter.createFromResource(this,
                R.array.magnitude_options,
                android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(spinner_dd_item);
        magnitudeSpinner.setAdapter(mAdapter);

    }//populateSpinners

    private void updateUIFromPreferences() {
        boolean autoUpChecked = prefs.getBoolean(
                PreferenceKeys.PREF_AUTO_UPDATE,
                false);
        int updateFreq = prefs.getInt(PreferenceKeys.PREF_UPDATE_FREQ, 2);
        int minMag = prefs.getInt(PreferenceKeys.PREF_MIN_MAG, 0);

        updateFreqSpinner.setSelection(updateFreq);
        magnitudeSpinner.setSelection(minMag);
        autoUpdate.setChecked(autoUpChecked);
    }//updateUIFromPreferences

    public void onBtnCancelClick( View v) {
        AmendPreferencesActivity.this.setResult(RESULT_CANCELED);
        finish();
    }//onBtnCancelClick

    public void onBtnOkClick(View v) {
        savePreferences();
        AmendPreferencesActivity.this.setResult(RESULT_OK);
        finish();
    }//onBtnOkClick

    private void savePreferences() {
        int updateIndex = updateFreqSpinner.getSelectedItemPosition();
        int minMagIndex= magnitudeSpinner.getSelectedItemPosition();
        boolean autoUpdateChecked = autoUpdate.isChecked();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(PreferenceKeys.PREF_AUTO_UPDATE, autoUpdateChecked);
        editor.putInt(PreferenceKeys.PREF_UPDATE_FREQ, updateIndex);
        editor.putInt(PreferenceKeys.PREF_MIN_MAG, minMagIndex);
        editor.commit();

    }//savePreferences

}

