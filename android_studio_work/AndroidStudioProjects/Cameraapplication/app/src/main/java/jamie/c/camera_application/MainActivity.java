package jamie.c.camera_application;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    // May want to save a image pathname for later use.
    String mCurrentPhotoPath;
    ImageView imageview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkCameraHardware(this)) {
            checkCameraPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE: {
                // if request is cancelled, the result arrays are empty.
                // Do the camera related task that was needed.
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

    // checking the camera permissions
    // Reasons for this is as of android 23 (6.0) permissions have to be requested at runtime.
    // Even if they are already explicitly in the Manifest.
    private void checkCameraPermission() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)) {
                Toast noPermissions = Toast.makeText(getApplicationContext(), "No Camera permissions", Toast.LENGTH_LONG);
                noPermissions.show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
            }
        }
        else {
            Toast permissions = Toast.makeText(getApplicationContext(), "Camera permissions granted", Toast.LENGTH_LONG);
            permissions.show();
        }
    }


    // checking the hardware for the Camera
    // This could be used with permissions to enable them if the devices has a camera
    // when an application isn't dependent on a Camera.
    private boolean checkCameraHardware(Context context) {
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        else {
            return false;
        }
    }


    // The android camera application will encode the photo in the return Intent
    // which is delivered to onActivityResult() as a small Bitmap.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageview = findViewById(R.id.idImageHolder);
        Bitmap mBitmap;

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            File imgFile = new File(mCurrentPhotoPath);

            if(imgFile.exists()) {
                imageview.setImageURI(Uri.fromFile(imgFile));
//                galleryAddPic();
                Uri imageUri = data.getData();
                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                    createImageFile(mBitmap);
                }
                catch(Exception e)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"Could not save file", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
       // else if (resultCode == RESULT_CANCELED) {
       //     mCurrentPhotoPath = null;
        //}
    }

    public void onBtnClcik(View view) {
        dispatchTakePictureIntent();
    }

    // Intent to capture a photo
    private void dispatchTakePictureIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // The check here is important - since it returns the first activity component that can handle the intent.
        // stops the app from crashing
        if (cameraIntent.resolveActivity(getPackageManager()) != null ) {
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                Toast toast = Toast.makeText(getApplicationContext(), "Error can't create file", Toast.LENGTH_LONG);
//                toast.show();
//            }

            // Continue only if the file was successfully created.
//            if (photoFile != null ) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "jamie.c.camera_application.provider", photoFile);
                //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
//            }

        }
    }

    private String createImageFile(Bitmap bitmapImage) {


        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";

        File directory = cw.getDir("image_directory", Context.MODE_PRIVATE);
        File myPath = new File(directory, imageFileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100,fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        mCurrentPhotoPath = directory.getAbsolutePath();
        return mCurrentPhotoPath;
    }

    // Issue is can't currently access the file cause they private to the app.
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }



}
