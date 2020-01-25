package jamie.c.sharedpreferencesexercise2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_AMEND_PREFERENCES = Menu.FIRST;
    private static final int MENU_REPORT_PREFERENCES = Menu.FIRST+1;
    private static final int AMEND_PREFERENCES = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }//onCreate

    public void onBtnShutdownClick (View v) {
        finish();
    }//onBtnShutdownClick

    public void onBtnAmendPreferencesClick(View v) {
        Intent intent = new Intent(this, AmendPreferencesActivity.class);
        startActivityForResult(intent, AMEND_PREFERENCES);
    }//onBtnAmendPreferencesClick

    public void onBtnReportPreferencesClick(View v) {
        Intent intent = new Intent(this, ReportPreferencesActivity.class);
        startActivity(intent);
    }//onBtnReportPreferencesClick

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_AMEND_PREFERENCES, Menu.NONE, R.string.menu_settings);
        menu.add(0, MENU_REPORT_PREFERENCES, Menu.NONE, R.string.menu_report);
        return true;
    }//onCreateOptionsMenu

    public boolean onOptionsItemSelected(MenuItem item){
        //Method returns:
        //false to allow normal menu processing to proceed
        //true to process it here.
        Intent intent;
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case MENU_AMEND_PREFERENCES:
                intent = new Intent(this, AmendPreferencesActivity.class);
                startActivityForResult(intent, AMEND_PREFERENCES);
                return true;
            case MENU_REPORT_PREFERENCES:
                intent = new Intent(this, ReportPreferencesActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }//onOptionsItemSelected



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Toast toast;
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (AMEND_PREFERENCES):
                if (resultCode == RESULT_OK)
                    toast = Toast.makeText(MainActivity.this,
                            "Preferences updated",
                            Toast.LENGTH_SHORT);
                else
                    toast = Toast.makeText(MainActivity.this,
                            "Preference update cancelled",
                            Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }//onActivityResult

}//MainActivity

