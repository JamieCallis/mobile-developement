package uk.co.knorris.sqliteexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllStaffMembersCustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<StaffMemberModel> staffMemberModelArrayList;

    public AllStaffMembersCustomAdapter(Context context, ArrayList<StaffMemberModel> staffMemberModelArrayList) {

        this.context = context;
        this.staffMemberModelArrayList = staffMemberModelArrayList;
    }//AllStaffMembersCustomAdapter constructor

    @Override
    public int getCount() {
        return staffMemberModelArrayList.size();
    }//getCount

    @Override
    public Object getItem(int position) {
        return staffMemberModelArrayList.get(position);
    }//getItem

    @Override
    public long getItemId(int position) {
        return 0;
    }//getItemId

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //https://developer.android.com/training/improving-layouts/smooth-scrolling#java
        ViewHolder viewHolder;

        String s;
        s = "CustomAdapter.getView - position is " + Integer.toString(position) + "\n";
        if (convertView == null)
            s += "convertView: null";
        else
            s += "convertView class: " + convertView.getClass().getName();
        report(s);

        if (convertView == null) {
            //We must create a new convertView

            report("Making/inflating new convertView from lv_item");

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            report("Setting associated ViewHolder");

            viewHolder = new ViewHolder();
            viewHolder.tvFirstName = (TextView) convertView.findViewById(R.id.firstName);
            viewHolder.tvLastName = (TextView) convertView.findViewById(R.id.lastName);
            viewHolder.tvOfficeNumber = (TextView) convertView.findViewById(R.id.officeNumber);

            report("convertView.setTag with holder's 3 TextViews:\n" +
                      "TextView tvFirstnName: " + viewHolder.tvFirstName.getText() + "\n" +
                      "TextView tvLastnName: " + viewHolder.tvLastName.getText() + "\n" +
                      "TextView tvOfficeNumber: " + viewHolder.tvOfficeNumber.getText());

            convertView.setTag(viewHolder);
        } else {
            //View exists so here we can do changes to the convertView, such as set a text on a TextView
            // the getTag returns the viewHolder object set as a tag to the view

            report("convertView exists, recycling");

            viewHolder = (ViewHolder) convertView.getTag();

            report("Setting associated ViewHolder using (ViewHolder)convertView.getTag():\n" +
                      "TextView tvFirstName: " + viewHolder.tvFirstName.getText() + "\n" +
                      "TextView tvLastName: " + viewHolder.tvLastName.getText() + "\n" +
                      "TextView tvOfficeNumber: " + viewHolder.tvOfficeNumber.getText());
        }

        report("Setting holder");
        viewHolder.tvFirstName.setText("First name: " + staffMemberModelArrayList.get(position).getFirstName());
        viewHolder.tvLastName.setText("Last name: " + staffMemberModelArrayList.get(position).getLastName());
        viewHolder.tvOfficeNumber.setText("Office number: " + staffMemberModelArrayList.get(position).getOfficeNumber());

        report("Finally, about to return currentView\nholder now contains:\n" +
                "TextView tvFirstnName: " + viewHolder.tvFirstName.getText() + "\n" +
                "TextView tvLastnName: " + viewHolder.tvLastName.getText() + "\n" +
                "TextView tvOfficeNumber: " + viewHolder.tvOfficeNumber.getText());

        report( "returning currentView");
        return convertView;
    }//getView

    public void report(String s) {
        Toast.makeText(this.context, s, Toast.LENGTH_SHORT);
        Log.i("SQLite example", s);
    }//report

    private class ViewHolder {
        protected TextView tvFirstName;
        protected TextView tvLastName;
        protected TextView tvOfficeNumber;
    }//ViewHolder

}//AllStaffMembersCustomAdapter class
