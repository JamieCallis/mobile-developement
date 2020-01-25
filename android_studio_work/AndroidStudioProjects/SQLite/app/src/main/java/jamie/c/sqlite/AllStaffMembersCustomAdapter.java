package jamie.c.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AllStaffMembersCustomAdapter extends BaseAdapter {

    private Context context;
    // used from database helper
    private ArrayList<StaffMemberModel> staffMemberModelArrayList;

    public AllStaffMembersCustomAdapter(Context context,
                                        ArrayList<StaffMemberModel> staffMemberModelArrayList) {

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

        if (convertView == null) {
            //We must create a new convertView

            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            viewHolder = new ViewHolder();
            viewHolder.tvFirstName = (TextView) convertView.findViewById(R.id.firstName);
            viewHolder.tvLastName = (TextView) convertView.findViewById(R.id.lastName);
            viewHolder.tvOfficeNumber = (TextView) convertView.findViewById(R.id.officeNumber);

            convertView.setTag(viewHolder);
        } else {
            //View exists so here we can do changes to the convertView,
            //such as set a text on a TextView
            //the getTag returns the viewHolder object set as a tag to the view

            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.tvFirstName.setText("First name: " +
                staffMemberModelArrayList.get(position).getFirstName());
        viewHolder.tvLastName.setText("Last name: " +
                staffMemberModelArrayList.get(position).getLastName());
        viewHolder.tvOfficeNumber.setText("Office number: " +
                staffMemberModelArrayList.get(position).getOfficeNumber());

        return convertView;
    }//getView

    private class ViewHolder {
        protected TextView tvFirstName;
        protected TextView tvLastName;
        protected TextView tvOfficeNumber;
    }//ViewHolder

}//AllStaffMembersCustomAdapter class

