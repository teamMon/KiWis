package com.example.jpar4.kiwis.kiwis;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jpar4.kiwis.Kiwis;
import com.example.jpar4.kiwis.R;
import com.example.jpar4.kiwis.kiwis.func.MRRoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.jpar4.kiwis.kiwis.func.PicMethod.exifOrientationToDegrees;
import static com.example.jpar4.kiwis.kiwis.func.PicMethod.getRealPathFromUri;
import static com.example.jpar4.kiwis.kiwis.func.PicMethod.rotate;

public class AddPostItemActivity extends AppCompatActivity {

    //int edit_flag;
    final int ADD_ITEM_POST = 1001;
    final int EDIT_ITEM_POST = 1002;
    final int PICK_FROM_ALBUM = 2001;
    final int AFTER_FROM_ALBUM = 2002;

    int REQUEST_CODE;

    Button add_btn_comfirm;
    EditText add_et_posttext;
    ImageButton add_ebtn_addPhoto;
    ImageView add_iv_img;
    ImageView add_iv_profilepic;
    TextView add_tv_userid;
    TextView add_tv_username;
    private Kiwis app;

    int position;
    private Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_item);
        app = (Kiwis)getApplicationContext();

        add_btn_comfirm = (Button) findViewById(R.id.add_btn_comfirm);
        add_et_posttext = (EditText) findViewById(R.id.add_et_posttext);
        add_ebtn_addPhoto = (ImageButton) findViewById(R.id.add_ebtn_addPhoto);
        add_iv_img = (ImageView) findViewById(R.id.add_iv_img);
        add_iv_profilepic =(ImageView) findViewById(R.id.add_iv_profilepic);
        add_tv_userid = (TextView) findViewById(R.id.add_tv_userid);
        add_tv_username = (TextView) findViewById(R.id.add_tv_username);


        Intent intent = getIntent();
        REQUEST_CODE = intent.getIntExtra("REQUEST_CODE",0);

        if(REQUEST_CODE==(ADD_ITEM_POST)){
           // Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
            SharedPreferences conMember = getSharedPreferences("conMember", MODE_PRIVATE);
            String jsonString = conMember.getString("conMember","defValue");

            try {
                JSONObject infoMem = new JSONObject(jsonString);
               //String userid = infoMem.getString("id");
                add_tv_userid.setText(infoMem.getString("id"));
                add_tv_username.setText(infoMem.getString("id"));

                try {
                    //image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(app.getProfilePath()));
                    if(!infoMem.getString("profilefile_name").equals("basic.jpg")) {
                      //  File pathpath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/imagefolder/" + app.getProfilePath());
                        File pathpath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/imagefolder/" + infoMem.getString("profilefile_name"));
                        //String pathpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/imagefolder/123.jpg";
                        Uri uripath = FileProvider.getUriForFile(this, "com.example.jpar4.kiwis", pathpath);
                        Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uripath);
                        Bitmap image_bitmap2 = MRRoundedImageView.getCroppedBitmap(image_bitmap, 50);
                        add_iv_profilepic.setImageBitmap(image_bitmap2);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if(REQUEST_CODE==(EDIT_ITEM_POST)){
           // Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
            SharedPreferences conMember = getSharedPreferences("conMember", MODE_PRIVATE);
            String jsonString = conMember.getString("conMember","defValue");

            try {
                JSONObject infoMem = new JSONObject(jsonString);
                //String userid = infoMem.getString("id");
                add_tv_userid.setText(infoMem.getString("id"));
                add_tv_username.setText(infoMem.getString("id"));
                try {
                    //image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(app.getProfilePath()));
                    //if(!app.getProfilePath().equals("")) {
                    if(!infoMem.getString("profilefile_name").equals("basic.jpg")) {
                        //File pathpath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/imagefolder/" + app.getProfilePath());
                        File pathpath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/imagefolder/" + infoMem.getString("profilefile_name"));
                        //String pathpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/imagefolder/123.jpg";
                        Uri uripath = FileProvider.getUriForFile(this, "com.example.jpar4.kiwis", pathpath);
                        Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uripath);
                        Bitmap image_bitmap2 = MRRoundedImageView.getCroppedBitmap(image_bitmap, 50);
                        add_iv_profilepic.setImageBitmap(image_bitmap2);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            add_et_posttext.setText(intent.getStringExtra("content").toString());

            ///////////////////////////////이미지//////////////////////////////////////////
            if(intent.getStringExtra("photoUri").equals("")){
                add_iv_img.setImageResource(R.mipmap.ic_photo);
            }
            else{
                String photoUri = intent.getStringExtra("photoUri").toString();
                mImageCaptureUri =  Uri.parse(photoUri);
                add_iv_img.setImageURI(mImageCaptureUri);
                // holder.ivImg.setImageURI(Uri.parse(item.getPostImgUrl()));
                Bitmap image_bitmap = null;
                try {
                    image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageCaptureUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                add_iv_img.setImageBitmap(image_bitmap);
            }
           //////////////////////////////////////////////////////////////////////////
            position = intent.getIntExtra("position",0);
        }else{
            Toast.makeText(this, "REQUEST_CODE에러..", Toast.LENGTH_SHORT).show();
        }

        add_ebtn_addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

       /* edit_flag = intent.getIntExtra("EDIT_ITEM_POST",0);
        if(edit_flag==EDIT_ITEM_POST){
            add_et_posttext.setText(intent.getStringExtra("content").toString());
        }*/

        add_btn_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String content = add_et_posttext.getText().toString();
                intent.putExtra("content",  content);
                intent.putExtra("position", position);
                if(mImageCaptureUri == null){
                    intent.putExtra("photoUri","");
                }
                else{
                    intent.putExtra("photoUri", mImageCaptureUri.toString());
                }
                // if(edit_flag==0){ // add일때
                    setResult(RESULT_OK, intent);
                    finish();
              //  }
              //  else if(edit_flag == 1002){ //edit일때
         /*           ComponentName componentName = new ComponentName(
                            "com.example.jpar4.kiwis",
                            "com.example.jpar4.kiwis.kiwis.KiwisMainActivity"
                    );
                    intent.setComponent(componentName);
                    intent.putExtra("EDIT_ITEM_POST", EDIT_ITEM_POST);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/
                   // setResult(RESULT_OK, intent);
                 //   finish();
              //  }
                //setResult(RESULT_OK, intent);
                //finish();
            }
        });

    }

    private void checkPermissions(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    1052);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1052: {
                // If request is cancelled, the result
                // arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED ){

                    // permission was granted.

                } else {
                    // Permission denied - Show a message
                    // to inform the user that this app only works
                    // with these permissions granted
                }
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkPermissions();
                }
                mImageCaptureUri = data.getData();

                try {
                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ImageView image = (ImageView) findViewById(R.id.add_iv_img);

                    String filepath =  getRealPathFromUri(this, mImageCaptureUri);
                    // 이미지를 상황에 맞게 회전시킨다
                    ExifInterface exif = new ExifInterface(filepath);
                    int exifOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int exifDegree = exifOrientationToDegrees(exifOrientation);
                    image_bitmap = rotate(image_bitmap, exifDegree);

                    image.setImageBitmap(image_bitmap);

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            default: {
                Toast.makeText(getBaseContext(), "에러발생" , Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

}
