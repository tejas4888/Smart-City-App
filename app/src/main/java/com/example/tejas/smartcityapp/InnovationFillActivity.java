package com.example.tejas.smartcityapp;

import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.FilePath;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class InnovationFillActivity extends AppCompatActivity {

    EditText editText_title, editText_description;
    Button upload_document_btn,upload_idea_btn;
    private int PICK_PDF_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_innovation_fill);

        requestStoragePermission();

        editText_title=(EditText)findViewById(R.id.activity_innovation_fill_title);
        editText_description=(EditText)findViewById(R.id.activity_innovation_fill_description);

        upload_document_btn=(Button)findViewById(R.id.activity_innovation_fill_upload_document);
        upload_idea_btn=(Button)findViewById(R.id.activity_innovation_fill_upload_btn);

        upload_document_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        upload_idea_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences(AppConstants.CURRENT_USER, MODE_PRIVATE);
                String user_id = prefs.getString("user_id", "0");
                String title = String.valueOf(editText_title.getText());
                String description = String.valueOf(editText_description.getText());

                uploadMultipart(user_id,title,description);
            }
        });

    }

    public void showFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Document"),PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
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

    public void uploadMultipart(final String user_id, final String title, final String description)
    {
        //getting name for the image
        class GetJSON2  extends AsyncTask<Void,Void,Void>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.v("Hello",""+filePath);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                //Uploading code
                try {
                    Log.v("FILEPATH",""+filePath);
                    String path = FilePath.getPath(InnovationFillActivity.this, filePath);

                    String uploadId = UUID.randomUUID().toString();

                    String id="";
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        id = DocumentsContract.getDocumentId(filePath);
                    }
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    String[] projection = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String pdf_path = cursor.getString(column_index);
                    Log.v("PDF_PATH",""+pdf_path);

                    if (pdf_path==null)
                    {
                        Log.v("COMPLETED","Hello "+pdf_path);
                    }
                    else
                    {
                        Log.v("SUCCESS",path);
                    }


                    new MultipartUploadRequest(InnovationFillActivity.this, uploadId, AppConstants.upload_innovation_idea)
                            .addFileToUpload(path, "image") //Adding file
                            .addParameter("user_id",user_id)
                            .addParameter("title",title)
                            .addParameter("description",description)
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(2)
                            .startUpload(); //Starting the upload

                } catch (Exception exc) {
                    //Toast.makeText(InnovationFillActivity.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(InnovationFillActivity.this, "Idea Submitted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
        //getting the actual path of the image

    }
}
