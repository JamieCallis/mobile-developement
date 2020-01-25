package jamie.c.counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public int instanceCounter = 0;
    public static int staticCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            Log.i ( "My Activity",
                    "No savedInstanceState object available");
        else
        {
            Log.i ( "My Activity", bundleToString(savedInstanceState));
        }

    }//onCreate

    @Override
    protected void onStart() {
        super.onStart();
        Toast toast = Toast.makeText(MainActivity.this,
                "onStart:\n" + getCounterDetails(),
                Toast.LENGTH_SHORT);
        toast.show();
    }//onStart

    @Override
    protected void onResume() {
        super.onResume();
        Toast toast = Toast.makeText(MainActivity.this,
                "onResume:\n" + getCounterDetails(),
                Toast.LENGTH_SHORT);
        toast.show();
        updateTxtCounters();
    }//onResume

    @Override
    protected void onPause() {
        super.onPause();
        Toast toast = Toast.makeText(MainActivity.this,
                "onPause:\n" + getCounterDetails(),
                Toast.LENGTH_SHORT);
        toast.show();
    }//onPause

    @Override
    protected void onStop() {
        super.onStop();
        Toast toast = Toast.makeText(MainActivity.this,
                "onStop:\n" + getCounterDetails(),
                Toast.LENGTH_SHORT);
        toast.show();
    }//onStop

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast toast = Toast.makeText(MainActivity.this,
                "onRestart:\n" + getCounterDetails(),
                Toast.LENGTH_SHORT);
        toast.show();
    }//onRestart

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast toast = Toast.makeText(MainActivity.this,
                "onDestroy:\n" + getCounterDetails(),
                Toast.LENGTH_SHORT);
        toast.show();
    }//onDestroy

    public void btnIncrementOnClick(View view)
    {
        staticCounter++;
        instanceCounter++;
        updateTxtCounters();
    }//btnIncrementOnClick

    public void btnDecrementOnClick(View view) {
        staticCounter--;
        instanceCounter--;
        updateTxtCounters();
    }//btnDecrementOnClick

    public void btnExitOnClick(View view) {
        finish();
    }//btnExitOnClick

    public void updateTxtCounters() {
        TextView txtStaticCounter = (TextView)
                findViewById(R.id.txtStaticCounter);
        txtStaticCounter.setText(Integer.toString(staticCounter));
        TextView txtInstanceCounter = (TextView)
                findViewById(R.id.txtInstanceCounter);
        txtInstanceCounter.setText(Integer.toString(instanceCounter));
    }//updateTxtCounter

    public String getCounterDetails()
    {
        return "staticCounter: " + Integer.toString(staticCounter) +
                "\ninstanceCounter: " + Integer.toString(instanceCounter);
    }//getCounterDetails

    public String bundleToString(Bundle bundle) {
        String string = bundle.describeContents() + "\n" +
                "Bundle keySet size: " +
                bundle.keySet().size() + "\n";
        string += "Bundle contains: {\n";
        for (String key : bundle.keySet())
            string += "  " + key + " => " + bundle.get(key) + ";\n";
        string += "}//Bundle\n";
        return string;
    }//bundleToString

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {
        Toast toast = Toast.makeText(MainActivity.this,
                "MainActivity - onSaveInstanceState\n" +
                        getCounterDetails(),
                Toast.LENGTH_LONG);
        toast.show();
        savedInstanceState.putInt( "instanceCounter", instanceCounter);
        super.onSaveInstanceState(savedInstanceState);
    }//onSaveInstanceState

    @Override
    public void onRestoreInstanceState (Bundle savedInstanceState) {
        //only called if there is a state to restore
        super.onRestoreInstanceState(savedInstanceState);
        Toast toast = Toast.makeText(MainActivity.this,
                "MainActivity - onRestoreInstanceState\n" +
                        getCounterDetails(),
                Toast.LENGTH_LONG);
        toast.show();
        instanceCounter = savedInstanceState.getInt ( "instanceCounter");
        toast = Toast.makeText(MainActivity.this,
                "MainActivity - onRestoreInstanceState\n" +
                        "instanceCounter assigned " +
                        Integer.toString(instanceCounter) + "\n",
                Toast.LENGTH_LONG);
        toast.show();
    }//onRestoreInstanceState

}//MainActivity

