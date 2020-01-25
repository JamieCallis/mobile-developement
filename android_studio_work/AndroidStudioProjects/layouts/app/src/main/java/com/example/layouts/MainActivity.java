package com.example.layouts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);

        int count = 0;

        for (int i=0; i < 30; i++)
        {
            TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.table_row, null, false);

            TextView operator = (TextView) tableRow.findViewById(R.id.rw_operator);
            final TextView value = (TextView) tableRow.findViewById(R.id.rw_value);
            final TextView total = (TextView) tableRow.findViewById(R.id.rw_total);

            operator.setText("*");
            value.setText(Integer.toString(i));

            count +=1;
            total.setText(Integer.toString(count));

            table.addView((tableRow));

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    value.setText("Click");
                    total.setText("Click");
                }
            });
        }
    }
}
