package com.example.tejas.smartcityapp;

import android.*;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.RecyclerAdapter.AlertsRecyclerAdapter;

import net.gotev.uploadservice.MultipartUploadRequest;

import java.io.IOException;
import java.util.UUID;

public class ReportFillActivity extends AppCompatActivity {

    EditText editText_title,editText_description;
    ImageView imageView;
    Button select_btn,upload_btn;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_fill);

        if (Build.VERSION.SDK_INT > 21) setupWindowAnimations();

        editText_title=(EditText)findViewById(R.id.activity_report_fill_title);
        editText_description=(EditText)findViewById(R.id.activity_report_fill_description);
        imageView=(ImageView)findViewById(R.id.activity_report_fill_imageview);
        select_btn=(Button)findViewById(R.id.activity_report_fill_select_btn);
        upload_btn=(Button)findViewById(R.id.activity_report_fill_upload_btn);

        requestStoragePermission();
        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = getSharedPreferences(AppConstants.CURRENT_USER, MODE_PRIVATE);
                String user_id = prefs.getString("user_id", "0");
                String title = String.valueOf(editText_title.getText());
                String description = String.valueOf(editText_description.getText());

                if (title.length()==0 || description.length()==0)
                {
                    Toast.makeText(ReportFillActivity.this, "Please Enter all the fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                uploadMultipart(user_id,title,description);
            }
        });

    }


    public void showFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public void uploadMultipart(final String user_id, final String title, final String description)
    {
        //getting name for the image
        class GetJSON2  extends AsyncTask<Void,Void,Void>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                String path = getPath(filePath);
                //Uploading code
                try {
                    String uploadId = UUID.randomUUID().toString();

                    new MultipartUploadRequest(ReportFillActivity.this, uploadId, AppConstants.upload_report)
                            .addFileToUpload(path, "image") //Adding file
                            .addParameter("user_id",user_id)
                            .addParameter("title",title)
                            .addParameter("description",description)
                            .setMaxRetries(2)
                            .startUpload(); //Starting the upload

                } catch (Exception exc) {
                    Toast.makeText(ReportFillActivity.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(ReportFillActivity.this, "Report Submitted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
        //getting the actual path of the image

    }



    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

    }

    private void setupWindowAnimations() {
        Slide slide = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Log.v("HERE","Animation");
            slide = new Slide();
            slide.setDuration(300);
            getWindow().setEnterTransition(slide);
        }

    }

}
