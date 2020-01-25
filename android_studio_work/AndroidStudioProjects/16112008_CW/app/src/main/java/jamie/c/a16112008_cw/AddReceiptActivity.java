package jamie.c.a16112008_cw;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

 /*
        SAVE BUTTON
        TODO: Disable button by default
        TODO: Enabled once requirements have been met
            - requirements
            TODO: Check that the amount has been inputted, then enable the button
4.-/
        PHOTO
        TODO: Check for hardware, and permissions
        TODO: Create a dialog box to ask the user if they want to select an image or take an image with the cameraFunctionality.
        TODO: If cameraFunctionality selected bring up cameraFunctionality view
            - store the image in gallery and in the image box.

        DATE ADDED
        TODO: Set to current date by default.



        BUGS
        TODO: When you try to add to the amount when VAT is checked, it doesn't calculate the VAT.
        TODO: When the amount has 1 value inside and you remove the application crashes.

     */

public class AddReceiptActivity extends Activity {

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ReceiptTrackerDbHelper mDbHelper;
    private Expense expnese;
    private TextView txtTotalAmount;
    private EditText txtAmount;
    private CheckBox chkVAT;
    private String temp;
    private double OrigionalTotalValue = 0;
    private double TotalVat = 0;
    private int VAT = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageview = null;
    static final int PICK_IMAGE_REQUEST = 2;
    TextView dateAdded;
    TextView dateIncurred;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.add_new_receipt_activity);

        expnese = new Expense();
        mDbHelper = new ReceiptTrackerDbHelper(this);
        txtAmount = findViewById(R.id.txtAmountValue);
        chkVAT = findViewById(R.id.chkVAT);
        txtTotalAmount = findViewById(R.id.txtTotalAmount);
        dateAdded = findViewById(R.id.txtChangeDateAdded);
        dateIncurred = findViewById(R.id.txtChangeDateIssued);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        month = month + 1;

        String date = day + "/" + month + "/" + year;
        dateAdded.setText(date);
        dateIncurred.setText(date);

        txtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()) {
                    txtTotalAmount.setText("£0");
                } else {
                    if(VAT == 1) {
                        temp = s.toString();
                        OrigionalTotalValue = Double.parseDouble(temp);
                        TotalVat = OrigionalTotalValue + OrigionalTotalValue * 0.2;
                        txtTotalAmount.setText("£" + Double.toString(TotalVat));
                    } else {
                        temp = s.toString();
                        OrigionalTotalValue = Double.parseDouble(temp);
                        expnese.setAmount(OrigionalTotalValue);
                        txtTotalAmount.setText("£" + s.toString());
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        chkVAT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    TotalVat = OrigionalTotalValue + OrigionalTotalValue * 0.2;
                    txtTotalAmount.setText("£" + Double.toString(TotalVat));
                    VAT = 1;
                } else {
                    txtTotalAmount.setText("£" + Double.toString(OrigionalTotalValue));
                    VAT = 0;
                }
                expnese.setVAT(VAT);
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // inputs are starting from a zero index.
                month = month + 1;
                String date = month + "/" + day + "/" +year;
                mDisplayDate.setText(date);
            }
        };
    }

    public void onHomeClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onPhotoClick(View view) {
        // create a dialog box

        if(checkCameraHardware(this)) {
            if(checkCameraPermission()) {
               photoDialog();
            }
            else {
                // device doesn't have camera permissions enabled.
                // should disable camera functionality.
            }
        } else {
            // device doesn't have hardware.
        }
    }


    public void onSaveClick(View view) {
        // Gather all the required information needed for a new row.
        collectExpenseData();

        // Insert a new row into the database.
        mDbHelper.addReceipt(this.expnese);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onDateClick(View view) {

        mDisplayDate = (TextView) view;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                mDateSetListener,
                year,
                month,
                day);
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // stops the user from selecting a date in the future.
        dialog.show();
    }

    private void collectExpenseData() {
        // Collects the date the receipt was added.
        mDisplayDate = findViewById(R.id.txtChangeDateAdded);
        expnese.setDateAdded(mDisplayDate.getText().toString());

        // Collects the date the receipt was incurred.
        mDisplayDate = findViewById(R.id.txtChangeDateIssued);
        expnese.setDateIssued(mDisplayDate.getText().toString());

        // Collects the amount for the receipt
        expnese.setAmount(OrigionalTotalValue);

        // vat - Handled by default, during on change.
        // Choices which total amount based on if there is VAT or not.
        if(expnese.getVAT() == 1) {
            expnese.setTotalAmount(TotalVat);
        } else {
            expnese.setTotalAmount(OrigionalTotalValue);
        }

        // description
        txtAmount = findViewById(R.id.txtDescriptionValue);
        expnese.setDescription(txtAmount.getText().toString());

        // image - is handled via the activity. This is to ensure that it is a real image.

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE: {
                // if request is cancelled, the result arrays are empty.
                // Do the cameraFunctionality related task that was needed.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, yay!
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                break;
            }
        }
    }

    // checking the cameraFunctionality permissions
    // Reasons for this is as of android 23 (6.0) permissions have to be requested at runtime.
    // Even if they are already explicitly in the Manifest.
    public boolean checkCameraPermission() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)) {
                Toast noPermissions = Toast.makeText(getApplicationContext(), "No Camera permissions", Toast.LENGTH_SHORT);
                noPermissions.show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
            }
            return false;
        }
        else {
            Toast permissions = Toast.makeText(getApplicationContext(), "Camera permissions granted", Toast.LENGTH_SHORT);
            permissions.show();
            return true;
        }
    }

    // checking the hardware for the Camera
    // This could be used with permissions to enable them if the devices has a cameraFunctionality
    // when an application isn't dependent on a Camera.
    public boolean checkCameraHardware(Context context) {
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        else {
            return false;
        }
    }

    // The android cameraFunctionality application will encode the photo in the return Intent
    // which is delivered to onActivityResult() as a small Bitmap.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageview = findViewById(R.id.imgVReceipt);
        Bitmap mBitmap;

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            try {
                mBitmap = (Bitmap) extra.get("data");
                imageview.setImageBitmap(mBitmap);
                expnese.setImage(convertImageToByteArray(mBitmap));
            } catch(NullPointerException ex) {
                Toast toast = Toast.makeText(getApplicationContext(), "null pointer exceiption", Toast.LENGTH_SHORT);
                toast.show();
            }

        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            try {
                mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageview.setImageBitmap(mBitmap);
                expnese.setImage(convertImageToByteArray(mBitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // Intent to capture a photo
    public void dispatchTakePictureIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // The check here is important - since it returns the first activity component that can handle the intent.
        // stops the app from crashing
        if (cameraIntent.resolveActivity(getPackageManager()) != null ) {
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void dispatchUploadPictureIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select Upload method")
                , PICK_IMAGE_REQUEST);
    }

    private void photoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("SelectImage");
        builder.setMessage("Select to upload or take a photo.");

        // handle the button clicks
        builder.setPositiveButton("take", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dispatchTakePictureIntent();
            }
        });

        builder.setNeutralButton("upload", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dispatchUploadPictureIntent();
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Feel free to add a image later.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        builder.show();
    }

    public byte[] convertImageToByteArray(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte imageInByte[] = stream.toByteArray();
        return imageInByte;
    }
}