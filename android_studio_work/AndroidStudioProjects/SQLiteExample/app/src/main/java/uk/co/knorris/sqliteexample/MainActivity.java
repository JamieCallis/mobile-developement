package uk.co.knorris.sqliteexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnStore;
    private Button btnGetAll;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText  etOfficeNumber;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etOfficeNumber = (EditText) findViewById(R.id.etOfficeNumber);

    }//onCreate

    public void  btnStoreOnClick(View view) {
        databaseHelper.addStaffMember(etFirstName.getText().toString(),
                                      etLastName.getText().toString(),
                                      etOfficeNumber.getText().toString());
        etFirstName.setText("");
        etLastName.setText("");
        etOfficeNumber.setText("");
        Toast.makeText(MainActivity.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();
    }//btnStoreOnClick

    public void  btnGetAllOnClick(View view) {
            Intent intent = new Intent(this, GetAllStaffMembersActivity.class);
            startActivity(intent);
    }//btnGetAllOnClick

}//MainActivity
