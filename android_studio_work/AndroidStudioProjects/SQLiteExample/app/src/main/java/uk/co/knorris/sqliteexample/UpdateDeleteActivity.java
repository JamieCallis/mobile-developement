package uk.co.knorris.sqliteexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDeleteActivity extends AppCompatActivity {

    private StaffMemberModel staffMemberModel;
    private EditText etFirstName, etLastName, etOfficeNumber;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        Intent intent = getIntent();
        staffMemberModel = (StaffMemberModel) intent.getSerializableExtra("staff");

        databaseHelper = new DatabaseHelper(this);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etOfficeNumber = (EditText) findViewById(R.id.etOfficeNumber);

        etFirstName.setText(staffMemberModel.getFirstName());
        etLastName.setText(staffMemberModel.getLastName());
        etOfficeNumber.setText(staffMemberModel.getOfficeNumber());

    }//onCreate

    public void btnUpdateOnClick ( View view ) {
        databaseHelper.updateStaffMember(
                staffMemberModel.getId(),
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etOfficeNumber.getText().toString());

        Toast.makeText(UpdateDeleteActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(UpdateDeleteActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }//btnUpdateOnClick

    public void btnDeleteOnClick ( View view ) {
        databaseHelper.deleteStaffMember(staffMemberModel.getId());
        Toast.makeText(UpdateDeleteActivity.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdateDeleteActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }//btnDeleteOnClick

}//UpdateDeleteActivity class

