package com.example.jpar4.kiwis.kiwis.fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jpar4.kiwis.Kiwis;
import com.example.jpar4.kiwis.R;
import com.example.jpar4.kiwis.kiwis.ProfileEditActivity;
import com.example.jpar4.kiwis.kiwis.func.MRRoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    final int EDIT_PROFILE = 2011;

    TextView profile_tv_username2;
    TextView profile_tv_userid2;
    TextView profile_tv_useremail2;
    TextView profile_tv_userphonumber2;
    ImageView profile_iv_person;
    ImageButton ibtn_editProfile;
    Kiwis app;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_profile, container, false);
        app = (Kiwis)getActivity().getApplicationContext();
        profile_tv_username2 = (TextView) v.findViewById(R.id.profile_tv_username2);
        profile_tv_userid2 = (TextView) v.findViewById(R.id.profile_tv_userid2);
        profile_tv_useremail2 = (TextView) v.findViewById(R.id.profile_tv_useremail2);
        profile_tv_userphonumber2 = (TextView) v.findViewById(R.id.profile_tv_userphonumber2);
        profile_tv_userphonumber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = profile_tv_userphonumber2.getText().toString();
                Uri uri = Uri.parse("tel:"+tel);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(callIntent);
            }
        });

        profile_iv_person = (ImageView) v.findViewById(R.id.profile_iv_person);
        ibtn_editProfile = (ImageButton) v.findViewById(R.id.ibtn_editProfile);

        ibtn_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileEditActivity.class);
                startActivityForResult(intent, EDIT_PROFILE);
            }
        });

        SharedPreferences conMember = getActivity().getSharedPreferences("conMember", MODE_PRIVATE);
        String profile_path ="";
        String jsonString = conMember.getString("conMember","defValue");
        try {
            JSONObject infoMem = new JSONObject(jsonString);
            profile_path= infoMem.getString("profilefile_name");
            profile_tv_username2.setText(infoMem.getString("name"));
            profile_tv_userid2.setText(infoMem.getString("id"));
            profile_tv_useremail2.setText(infoMem.getString("email"));
            profile_tv_userphonumber2.setText(infoMem.getString("phone"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }


        Bitmap image_bitmap = null;
        try {
            //image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(app.getProfilePath()));
            if(!profile_path.equals("")) {
                File pathpath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/imagefolder/" + profile_path);
                //String pathpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/imagefolder/123.jpg";
                Uri uripath = FileProvider.getUriForFile(getContext(), "com.example.jpar4.kiwis", pathpath);
                image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uripath);
                Bitmap image_bitmap2 = MRRoundedImageView.getCroppedBitmap(image_bitmap, 1024);
                profile_iv_person.setImageBitmap(image_bitmap2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==EDIT_PROFILE && resultCode == Activity.RESULT_OK) {
            SharedPreferences conMember = getActivity().getSharedPreferences("conMember", MODE_PRIVATE);
            String profile_path ="";
            String jsonString = conMember.getString("conMember","defValue");
            try {
                JSONObject infoMem = new JSONObject(jsonString);
                profile_path= infoMem.getString("profilefile_name");
                profile_tv_username2.setText(infoMem.getString("name"));
                profile_tv_userid2.setText(infoMem.getString("id"));
                profile_tv_useremail2.setText(infoMem.getString("email"));
                profile_tv_userphonumber2.setText(infoMem.getString("phone"));
            }
            catch (JSONException e){
                e.printStackTrace();
            }


            Bitmap image_bitmap = null;
            try {
                //image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(app.getProfilePath()));
                if(!profile_path.equals("")) {
                    File pathpath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/imagefolder/" + profile_path);
                    //String pathpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/imagefolder/123.jpg";
                    Uri uripath = FileProvider.getUriForFile(getContext(), "com.example.jpar4.kiwis", pathpath);
                    image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uripath);
                    Bitmap image_bitmap2 = MRRoundedImageView.getCroppedBitmap(image_bitmap, 1024);
                    profile_iv_person.setImageBitmap(image_bitmap2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
