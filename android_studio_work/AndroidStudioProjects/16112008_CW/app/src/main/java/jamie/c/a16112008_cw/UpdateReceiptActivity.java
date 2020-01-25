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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class UpdateReceiptActivity extends Activity {

    private int receiptID;
    private ReceiptTrackerDbHelper receiptDbHelper;
    private Expense expense;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String temp;
    private double OrigionalTotalValue = 0;
    private double TotalVat = 0;
    private int VAT = 0;

    static final int PICK_IMAGE_REQUEST = 2;
    static final int REQUEST_IMAGE_CAPTURE = 1;

     ImageView ivReceiptImage;
     TextView tvDateAdded;
     TextView tvDateIncurred;
     TextView tvDatePaid;
     TextView tvTotalValue;
     CheckBox chkVAT;
     CheckBox chkPaid;
     EditText etDescription;
     EditText etAmount;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.update_reciept_activity);

        receiptID = getIntent().getExtras().getInt("id");

        receiptDbHelper = new ReceiptTrackerDbHelper(this);

        expense = receiptDbHelper.getReceipt(receiptID);

        ivReceiptImage = findViewById(R.id.ivReceipt);
        tvDateAdded = findViewById(R.id.txtChangeDateAdded);
        tvDateIncurred = findViewById(R.id.txtChangeDateIssued);
        tvDatePaid = findViewById(R.id.txtChangeDatePaid);
        tvTotalValue = findViewById(R.id.txtTotalAmount);
        chkVAT = findViewById(R.id.chkVAT);
        chkPaid = findViewById(R.id.chkPaid);
        etDescription = findViewById(R.id.txtDescriptionValue);
        etAmount = findViewById(R.id.txtAmountValue);

        if(expense.getImage() != null) {
            ivReceiptImage.setImageBitmap(convertByteArrayToBitmap(expense.getImage()));
        }

        tvTotalValue.setText("£" + Double.toString(expense.getTotalamount()));
        tvDateAdded.setText(expense.getDateAdded());
        tvDateIncurred.setText(expense.getDateIssued());
        tvDatePaid.setText(expense.getDatePaid());
        etAmount.setText(String.valueOf(expense.getAmount()));
        etDescription.setText(expense.getDescription());

        if(expense.getVAT() == 0) {
            chkVAT.setChecked(false);
        } else {
            chkVAT.setChecked(true);
        }

        if(expense.getPaid() == 0) {
            chkVAT.setChecked(false);
        } else {
            chkVAT.setChecked(true);
        }


        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()) {
                    tvTotalValue.setText("£0");
                } else {
                    temp = s.toString();
                    OrigionalTotalValue = Double.parseDouble(temp);
                    expense.setAmount(OrigionalTotalValue);
                    tvTotalValue.setText("£" + s.toString());
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        chkVAT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    TotalVat = OrigionalTotalValue + OrigionalTotalValue * 0.2;
                    tvTotalValue.setText("£" + Double.toString(TotalVat));
                    VAT = 1;
                } else {
                    tvTotalValue.setText("£" + Double.toString(OrigionalTotalValue));
                    VAT = 0;
                }
                expense.setVAT(VAT);
            }
        });

        chkPaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    String dateString = day + "/" + month  + "/" + year;

                    tvDatePaid.setText(dateString);

                    if(receiptDbHelper.updatePaid(dateString, 1, receiptID) != -1) {
                        receiptDbHelper.updateReceipt(expense);
                        updateInformation();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Paid update was unsucessful try again", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // inputs are starting from a zero index.
                month = month + 1;
                String date = month + "/" + day + "/" +year;
                tvDateIncurred.setText(date);
            }
        };

    }

    public void updateInformation() {
        if(expense.getImage() != null) {
            ivReceiptImage.setImageBitmap(convertByteArrayToBitmap(expense.getImage()));
        }

        tvTotalValue.setText("£" + String.valueOf(expense.getTotalamount()));
        tvDateAdded.setText(expense.getDateAdded());
        tvDateIncurred.setText(expense.getDateIssued());
        tvDatePaid.setText(expense.getDatePaid());
        etAmount.setText(String.valueOf(expense.getAmount()));
        etDescription.setText(expense.getDescription());

        if(expense.getVAT() == 0) {
            chkVAT.setChecked(false);
        } else {
            chkVAT.setChecked(true);
        }

        if(expense.getPaid() == 0) {
            chkVAT.setChecked(false);
        } else {
            chkVAT.setChecked(true);
        }
    }

    public void onHomeClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onPhotoClick(View view) {

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
        receiptDbHelper.updateReceipt(expense);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onDateClick(View view) {

        tvDateIncurred = (TextView) view;
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

        expense.setDateAdded(tvDateAdded.getText().toString());
        expense.setDateIssued(tvDateIncurred.getText().toString());

        expense.setAmount(Double.parseDouble(etAmount.getText().toString()));

        // vat - Handled by default, during on change.
        // Choices which total amount based on if there is VAT or not.
        if(expense.getVAT() == 1) {
            expense.setTotalAmount(TotalVat);
        } else {
            expense.setTotalAmount(Double.parseDouble(etAmount.getText().toString()));
        }

        // description
        expense.setDescription(etDescription.getText().toString());

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
        Bitmap mBitmap;

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            try {
                mBitmap = (Bitmap) extra.get("data");
                ivReceiptImage.setImageBitmap(mBitmap);
                expense.setImage(convertImageToByteArray(mBitmap));
            } catch(NullPointerException ex) {
                Toast toast = Toast.makeText(getApplicationContext(), "null pointer exceiption", Toast.LENGTH_SHORT);
                toast.show();
            }

        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            try {
                mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ivReceiptImage.setImageBitmap(mBitmap);
                expense.setImage(convertImageToByteArray(mBitmap));
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

    public Bitmap convertByteArrayToBitmap(byte[] receiptImage) {
        ByteArrayInputStream imageStream = new ByteArrayInputStream(receiptImage);
        Bitmap image = BitmapFactory.decodeStream(imageStream);
        return image;
    }




}
