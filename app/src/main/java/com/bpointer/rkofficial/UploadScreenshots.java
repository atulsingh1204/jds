package com.bpointer.rkofficial;

import static com.bpointer.rkofficial.Common.AppConstant.BaseURL;
import static com.bpointer.rkofficial.Common.AppConstant.ID;
import static com.bpointer.rkofficial.Common.AppConstant.INSTRUCTION;
import static com.bpointer.rkofficial.Common.AppConstant.ImgURL;
import static com.bpointer.rkofficial.Common.AppConstant.MANUAL_QR_IMG;
import static com.bpointer.rkofficial.Common.AppConstant.USER_ID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bpointer.rkofficial.Activity.MainActivity;
import com.bpointer.rkofficial.Api.Api;
import com.bpointer.rkofficial.Api.Authentication;

import com.bpointer.rkofficial.Common.CustomDialog;
import com.bpointer.rkofficial.Common.PreferenceManager;
import com.bpointer.rkofficial.Model.Response.LoginResponseModel.LoginResponseModel;
import com.bpointer.rkofficial.Model.Response.UploadResponseModel;
import com.bpointer.rkofficial.Utils.Constants;
import com.bpointer.rkofficial.Utils.FileUtils;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UploadScreenshots extends AppCompatActivity {

    Button bt_submit;
    EditText et_point;
    TextView tv_ins;
    ImageView iv_screenshots_preview, ivUpload;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    double amount;
    int user_id;
    MultipartBody.Part screenshot_image = null;
    CustomDialog customDialog;
    PreferenceManager mPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_screenshots);
        customDialog = new CustomDialog(this);
        bt_submit = findViewById(R.id.bt_submit);
        iv_screenshots_preview = findViewById(R.id.iv_screenshots_preview);
        et_point = findViewById(R.id.et_point);
        ivUpload = findViewById(R.id.ivUpload);
        tv_ins = findViewById(R.id.tv_ins);
        mPreferenceManager = new PreferenceManager(UploadScreenshots.this);


        setData();

        ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });


        checkPermissions();


        iv_screenshots_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openGallery();
            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageUri != null) {
                    amount = 200;
                    user_id = 15;
                    String amount = et_point.getText().toString();
                    String user_id = mPreferenceManager.getStringPreference(ID);
//                    Toast.makeText(UploadScreenshots.this, "" + user_id, Toast.LENGTH_SHORT).show();


//                    if (!et_point.getText().toString().isEmpty() ){
//                        upload();
//                    }else {
//                        et_point.setError("Enter point");
//                    }

                    if (et_point.getText().toString().isEmpty()) {
                        et_point.setError("Enter Point");
                    } else if (Integer.parseInt(et_point.getText().toString()) < 100) {
                        et_point.setError("Enter Point greater than or equal to 100");
                    } else {
                        upload(amount, user_id);
                    }


                } else {
                    Toast.makeText(UploadScreenshots.this, "Please select screenshot!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    private void setData() {
        String img = mPreferenceManager.getStringPreference(MANUAL_QR_IMG);
        String instruction = mPreferenceManager.getStringPreference(INSTRUCTION);

        tv_ins.setText(instruction);

        // Display selected image in ImageView

        Log.e("check", "" + ImgURL + img);

        Glide.with(this)
                .load(ImgURL + img)
                .into(iv_screenshots_preview);
    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            try {
                // Display selected image in ImageView
                String img = mPreferenceManager.getStringPreference(MANUAL_QR_IMG);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ivUpload.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    public void upload(String amount, String user_id) {


        String strImagePath = Constants.getPath(this, imageUri);


        if (imageUri != null) {
            screenshot_image = prepareImagePart(strImagePath, "manual_transaction_img");


            Call<ResponseBody> call = Api.getClient().create(Authentication.class).addWalletRequestManual(
                    RequestBody.create(MediaType.parse("text/plain"), user_id),
                    RequestBody.create(MediaType.parse("text/plain"), amount),
                    screenshot_image
            );

            customDialog.showLoader();

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    customDialog.closeLoader();

                    if (response.isSuccessful()) {
                        Toast.makeText(UploadScreenshots.this, "Wallet balance request sent!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(UploadScreenshots.this, MainActivity.class));

                    } else {
                        Toast.makeText(UploadScreenshots.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    customDialog.closeLoader();
                    Toast.makeText(UploadScreenshots.this, "failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @NonNull
    private MultipartBody.Part prepareImagePart(String path, String partName) {
        File file = new File(path);
        Log.e("response", "path: " + path);
        Log.e("response", "partName: " + partName);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);

    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);

        } else {
            // Permissions granted, proceed with file operations
            // ...
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}