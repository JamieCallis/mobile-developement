package jamie.c.a16112008_cw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ReceiptTrackerCustomerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Expense> Receipts;

    public ReceiptTrackerCustomerAdapter(Context context, ArrayList<Expense> Receipts) {
        this.context = context;
        this.Receipts = Receipts;
    } // Constructor

    @Override
    public int getCount() { return Receipts.size(); }

    @Override
    public Object getItem(int position) { return Receipts.get(position); }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // must create a new convertView

            LayoutInflater inflater = (LayoutInflater)
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.lv_receipt, null, true);

            viewHolder = new ViewHolder();
            viewHolder.tvDateAdded = convertView.findViewById(R.id.TxtDateAddedValue);
            viewHolder.tvDateIncurred = convertView.findViewById(R.id.txtDateIncurredValue);
            viewHolder.tvDescription = convertView.findViewById(R.id.txtDescription);
            viewHolder.tvDatePaid = convertView.findViewById(R.id.txtDatePaidValue);
            viewHolder.tvTotalValue = convertView.findViewById(R.id.txtTotalValue);
            viewHolder.ivReceiptImage = convertView.findViewById(R.id.ivReceiptImage);
            viewHolder.btnDelete = convertView.findViewById(R.id.btnDelete);
            viewHolder.btnUpdate = convertView.findViewById(R.id.btnUpdate);
            viewHolder.btnPaid = convertView.findViewById(R.id.btnPaid);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvDateAdded.setText(Receipts.get(position).getDateAdded());
        viewHolder.tvDateIncurred.setText(Receipts.get(position).getDateIssued());
        viewHolder.tvDescription.setText(Receipts.get(position).getDescription());
        viewHolder.tvDatePaid.setText(Receipts.get(position).getDatePaid());
        viewHolder.tvTotalValue.setText("£" + Double.toString(Receipts.get(position).getTotalamount()));

        if(Receipts.get(position).getImage() != null) {
            viewHolder.ivReceiptImage.setImageBitmap(convertByteArrayToBitmap(Receipts.get(position).getImage()));
        }

        viewHolder.btnPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // need to build database functionality for this.
                if(context instanceof MainActivity) {

                    ((MainActivity)context).onPaidClick(v, Receipts.get(position).getID());
                }
            }
        });

        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof MainActivity) {
                    ((MainActivity)context).onUpdateClick(v, Receipts.get(position).getID());
                }
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    ((MainActivity)context).onDeleteClick(v, Receipts.get(position).getID());
                }
            }
        });


        return convertView;
    }

    public Bitmap convertByteArrayToBitmap(byte[] receiptImage) {
        ByteArrayInputStream imageStream = new ByteArrayInputStream(receiptImage);
        Bitmap image = BitmapFactory.decodeStream(imageStream);
        return image;
    }

    private class ViewHolder {
        protected TextView tvDateAdded;
        protected TextView tvDateIncurred;
        protected TextView tvDescription;
        protected TextView tvDatePaid;
        protected TextView tvTotalValue;
        protected ImageView ivReceiptImage;
        protected Button btnPaid;
        protected Button btnUpdate;
        protected ImageButton btnDelete;
    }
}
