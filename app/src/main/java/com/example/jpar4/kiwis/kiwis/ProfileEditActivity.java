package com.example.jpar4.kiwis.kiwis;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileEditActivity extends AppCompatActivity {
    final int PICK_FROM_ALBUM = 2001;
    final int AFTER_FROM_ALBUM = 2002;

    Kiwis app;
    boolean picOk = false;
    private Uri mImageCaptureUri;

    Bitmap roundimage_bitmap;
    Button edit_btn_confirm;

    EditText edit_tv_username2,edit_tv_useremail2,edit_tv_userphonumber2;
    TextView edit_tv_userid2;
    ImageButton edit_ibtn_uploadprofile;
    ImageView edit_iv_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_edit);

        app = (Kiwis)getApplicationContext();

        edit_tv_username2 = (EditText) findViewById(R.id.edit_tv_username2);
        edit_tv_userid2 = (TextView) findViewById(R.id.edit_tv_userid2);
        edit_tv_userid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(app, "ID는 변경할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        edit_tv_useremail2 = (EditText) findViewById(R.id.edit_tv_useremail2);
        edit_tv_userphonumber2 = (EditText) findViewById(R.id.edit_tv_userphonumber2);
        edit_ibtn_uploadprofile = (ImageButton) findViewById(R.id.edit_ibtn_uploadprofile);
        edit_iv_person=(ImageView) findViewById(R.id.edit_iv_person);

       // SharedPreferences memberList = getSharedPreferences("memberList", MODE_PRIVATE);
        SharedPreferences conMember = getSharedPreferences("conMember", MODE_PRIVATE);

        String jsonString = conMember.getString("conMember","defValue");
        try {
            JSONObject infoMem = new JSONObject(jsonString);
            edit_tv_username2.setText(infoMem.getString("name"));
            edit_tv_userid2.setText(infoMem.getString("id"));
            edit_tv_useremail2.setText(infoMem.getString("email"));
            edit_tv_userphonumber2.setText(infoMem.getString("phone"));

            File pathpath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/imagefolder/" + infoMem.getString("profilefile_name"));
            //String pathpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/imagefolder/123.jpg";
            Uri uripath = FileProvider.getUriForFile(this, "com.example.jpar4.kiwis", pathpath);
            Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uripath);
            Bitmap image_bitmap2 = MRRoundedImageView.getCroppedBitmap(image_bitmap, 1024);
            edit_iv_person.setImageBitmap(image_bitmap2);

        } catch (JSONException e) {
            e.printStackTrace();
        }catch(IOException ee){
            ee.printStackTrace();
        }

        edit_ibtn_uploadprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });


        edit_btn_confirm = (Button) findViewById(R.id.edit_btn_confirm);
        edit_btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences memberList1 = getSharedPreferences("memberList", MODE_PRIVATE);
                SharedPreferences conMember1 = getSharedPreferences("conMember", MODE_PRIVATE);

                String jsonString = memberList1.getString("memberList","defValue");
                String jsonString2 = conMember1.getString("conMember","defValue");

                SharedPreferences.Editor editor = memberList1.edit();
                SharedPreferences.Editor editor2 = conMember1.edit();

                JSONObject wrapObject = null;
                if(jsonString.equals("defValue")){

                }
                else{
                    try {
                        wrapObject = new JSONObject(jsonString);
                        Log.e("[PEdit]wrapObject", wrapObject.toString());
                        JSONArray jsonArray = new JSONArray(wrapObject.getString("memberList"));// wrapObject(여러개의 키값을 가지고 있는 뭉처진 JSONObject에서 memberLIst라는 키값의 밸류 값을 가져옴
                        JSONArray newjsonArray = new JSONArray();
                        Log.e("[PEdit]jsonArray", jsonArray.toString());

                        JSONObject infoMem = new JSONObject(jsonString2); //현재 접속자 정보// 로그인할때마다 바뀜.
                        Log.e("[PEdit]infoMem", infoMem.toString());
                        for(int i = 0; i < jsonArray.length(); i++){
                            Log.e("i", Integer.toString(i));
                            // Array 에서 하나의 JSONObject 를 추출
                            JSONObject jsonMemberObject = jsonArray.getJSONObject(i);
                            JSONObject newMem = new JSONObject();
                            if(jsonMemberObject.getString("id").equals(infoMem.getString("id"))){ // 접속자정보와 같은 JSONOBJECT를 찾아서 수정해줌
                                newMem.put("name", edit_tv_username2.getText().toString());
                                newMem.put("id", edit_tv_userid2.getText().toString());
                                newMem.put("pw", jsonMemberObject.getString("pw"));
                                newMem.put("email", edit_tv_useremail2.getText().toString());
                                newMem.put("phone", edit_tv_userphonumber2.getText().toString());

         /*             infoMem.put("name", edit_tv_username2.getText().toString());
                        infoMem.put("id", edit_tv_userid2.getText().toString());
                        infoMem.put("pw", jsonMemberObject.getString("pw"));
                        infoMem.put("email", edit_tv_useremail2.getText().toString());
                        infoMem.put("phone", edit_tv_userphonumber2.getText().toString());*/
                                if(picOk) {
                                    Log.e("[profile_E]p_name : 0", jsonMemberObject.getString("profilefile_name"));
                                    Log.e("[profile_E]p_name : 0",  edit_tv_userid2.getText().toString()+".jpg");
                                    newMem.put("profilefile_name",   edit_tv_userid2.getText().toString()+".jpg");
                              //      infoMem.put("profilefile_name",  jsonMemberObject.getString("profilefile_name"));
                                    saveBitmaptoJpeg(roundimage_bitmap, "imagefolder",  edit_tv_userid2.getText().toString());
                                    ////////////////////////////////////////////////////////////////////////////////////////////////////////아이템의 picName과 memberlist의 profilefile_name 동기화

                                    SharedPreferences itemPost = getSharedPreferences("itemPost", MODE_PRIVATE);
                                    SharedPreferences.Editor itemPostEditor = itemPost.edit();
                                    String jsonString1 = itemPost.getString("itemList", "defValue");
                                    JSONObject wrapObject1 = new JSONObject(jsonString1);
                                    JSONArray OlditemArray = new JSONArray(wrapObject1.getString("itemList"));
                                    JSONArray NewitemArray = new JSONArray();
                                    if (jsonString.equals("defValue")) { //  itemPost가 없을 때

                                    } else { // itemPost가 있을때
                                        try {
                                            Log.e("[Recy]onCre,jsnarr", OlditemArray.toString());
                                            for (int a = 0; a < OlditemArray.length(); a++) {
                                                JSONObject olditem = OlditemArray.getJSONObject(a);
                                                Log.e("olditem",olditem.toString());
                                                JSONObject newitem = new JSONObject();
                                                Log.e("userName",olditem.getString("userName"));
                                                if(olditem.getString("userName").equals( edit_tv_userid2.getText().toString())){// 아이디가 같은것은 프로필 사진을 바꿔줌
                                                    newitem.put("itemNum", olditem.getInt("itemNum"));
                                                    newitem.put("userName", olditem.getString("userName"));
                                                    newitem.put("isUserLike", olditem.getBoolean("isUserLike"));
                                                    newitem.put("postLikeCount", olditem.getInt("postLikeCount"));
                                                    newitem.put("postImgUrl", olditem.getString("postImgUrl"));
                                                    newitem.put("postText", olditem.getString("postText"));
                                                    newitem.put("likeCheck", olditem.getJSONObject("likeCheck"));
                                                    newitem.put("picName", edit_tv_userid2.getText().toString()+".jpg");

                                                    NewitemArray.put(newitem);
                                                //    Log.e("newitemarray",NewitemArray.toString());
                                                }
                                                else{//다른것은 그냥 그대로 저장
                                                    NewitemArray.put(olditem);
                                               //     Log.e("newitemarray",NewitemArray.toString());
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    wrapObject1.put("itemList", NewitemArray);
                                    // editor.remove("itemList");
                                    itemPostEditor.putString("itemList", wrapObject1.toString());
                                    Log.e("wrapObject.to", wrapObject1.toString());
                                    itemPostEditor.commit();


                                }else{//프로필 사진 등록 안한 경우
                                    // jsonMemberObject.put("profilefile_name","basic.jpg"); // 이미지 초기화 할때 이거 쓰면 됨.
                                    newMem.put("profilefile_name",  jsonMemberObject.getString("profilefile_name"));// 변경 안했을때는 그냥 이름만 그대로 해줌
                              //      infoMem.put("profilefile_name",  jsonMemberObject.getString("profilefile_name"));
                                }
                                //      jsonMemberObject.put("pwchk", join_et_input_pwchk.getText().toString());
                                newjsonArray.put(newMem);
                                editor2.putString("conMember",newMem.toString()); // 이건 큐런트 멤버용.


                            }
                            else{ //해당 JSONObject가 아니면 기존의 값을 보존해줌.

                                newMem.put("name",  jsonMemberObject.getString("name"));
                                newMem.put("id",  jsonMemberObject.getString("id"));
                                newMem.put("pw", jsonMemberObject.getString("pw"));
                                if(jsonMemberObject.has("email")){
                                    newMem.put("email",  jsonMemberObject.getString("email"));
                                }
                                else{
                                    newMem.put("email",  "");
                                }
                                if(jsonMemberObject.has("phone")){
                                    newMem.put("phone",  jsonMemberObject.getString("phone"));
                                }
                                else{
                                    newMem.put("phone",  "");
                                }
                                newMem.put("profilefile_name",  jsonMemberObject.getString("profilefile_name"));// 변경 안했을때는 그냥 이름만 그대로 해줌
                                newjsonArray.put(jsonMemberObject);

                            }
                        }
                        wrapObject.put("memberList", newjsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    editor.putString("memberList", wrapObject.toString());
                    editor.apply();
                    editor2.apply();

                }

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
/*
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
        finish();*/
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
                picOk = true;
                mImageCaptureUri = data.getData();
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, AFTER_FROM_ALBUM);

       /*         try {
                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ImageView image = (ImageView) findViewById(R.id.join_iv_person);
                    Bitmap image_bitmap2 = MRRoundedImageView.getCroppedBitmap(image_bitmap, 1024);
                    image.setImageBitmap(image_bitmap2);


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                break;
            }
            case AFTER_FROM_ALBUM :
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();

                if(extras != null)
                {
                    Bitmap photo = extras.getParcelable("data");
                    ImageView image = (ImageView) findViewById(R.id.edit_iv_person);
                    roundimage_bitmap = MRRoundedImageView.getCroppedBitmap(photo, 1024);
                    image.setImageBitmap(roundimage_bitmap);
                    //saveBitmaptoJpeg(image_bitmap2, "imagefolder","123");

                }

        /*        // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }
*/
                break;
            default: {
                Toast.makeText(getBaseContext(), "에러발생" , Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
    public void saveBitmaptoJpeg(Bitmap bitmap, String folder, String name){
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        // Get Absolute Path in External Sdcard
        String foler_name = "/"+folder+"/";
        String file_name = name+".jpg";
        String string_path = ex_storage+foler_name;

        File file_path;
        try{
            file_path = new File(string_path);
            if(!file_path.isDirectory()){
                file_path.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(string_path+file_name);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            app.setProfilePath(file_name);//////////////////////////////////////////////////////////////////////////////////////////////////
        }catch(FileNotFoundException exception){
            Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }
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
}
