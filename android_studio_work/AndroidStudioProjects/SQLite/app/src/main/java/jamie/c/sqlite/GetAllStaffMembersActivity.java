package jamie.c.sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GetAllStaffMembersActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<StaffMemberModel> staffMemberModelArrayList;
    private AllStaffMembersCustomAdapter allStaffMembersCustomAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_staff_members);

        listView = (ListView) findViewById(R.id.lv);

        databaseHelper = new DatabaseHelper(this);

        staffMemberModelArrayList = databaseHelper.getAllStaffMembers();

        allStaffMembersCustomAdapter = new
                AllStaffMembersCustomAdapter(this,staffMemberModelArrayList);
        listView.setAdapter(allStaffMembersCustomAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                Intent intent = new Intent(GetAllStaffMembersActivity.this,
                        UpdateDeleteActivity.class);

                Toast.makeText(GetAllStaffMembersActivity.this,
                        "Passing (.putExtra) staff member at " +
                                Integer.toString(position) + "\n" +
                                "which is :\n" +
                                ((StaffMemberModel)staffMemberModelArrayList.get(position)).toString(),
                        Toast.LENGTH_LONG).show();

                intent.putExtra("staff", staffMemberModelArrayList.get(position));
                startActivity(intent);
            }
        });

    }//onCreate

}//GetAllStaffMembersActivity class

