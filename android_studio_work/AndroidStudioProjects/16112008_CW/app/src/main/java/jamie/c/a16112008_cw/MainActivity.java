package jamie.c.a16112008_cw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Expense> receipts;
    private ReceiptTrackerCustomerAdapter ReceiptInterface;
    private ReceiptTrackerDbHelper receiptDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.receiptListView);

        receiptDbHelper = new ReceiptTrackerDbHelper(this);

        receipts = receiptDbHelper.getAllReceipts();

        ReceiptInterface = new ReceiptTrackerCustomerAdapter(this, receipts);

        listView.setAdapter(ReceiptInterface);

    }


    public void onActionMenuClick(View view) {
        // create an intent to send the user to add a new cameraFunctionality
        Intent intent = new Intent(this, AddReceiptActivity.class);
        startActivity(intent);
    }

    public void onDeleteClick(View view, int id) {
        if(receiptDbHelper.deleteReceipt(id)) {
            // delete was successful.
            receipts = receiptDbHelper.getAllReceipts();
            ReceiptInterface = new ReceiptTrackerCustomerAdapter(this, receipts);
            listView.setAdapter(ReceiptInterface);
        } else {
            Toast toast = Toast.makeText(this, "Delete was unsucessful try again", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void onUpdateClick(View view, int id) {
        Intent intent = new Intent(this, UpdateReceiptActivity.class);
        intent.putExtra("id", id);
        this.startActivity(intent);
    }

    public void onPaidClick(View view, int id) {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // zero based, need to add = 1 to get the real month.
        month = month + 1;

        String dateString = day + "/" + month  + "/" + year;

        if(receiptDbHelper.updatePaid(dateString, 1, id) != -1) {
            receipts = receiptDbHelper.getAllReceipts();
            ReceiptInterface = new ReceiptTrackerCustomerAdapter(this, receipts);
            listView.setAdapter(ReceiptInterface);
        } else {
            Toast toast = Toast.makeText(this, "Paid update was unsucessful try again", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
