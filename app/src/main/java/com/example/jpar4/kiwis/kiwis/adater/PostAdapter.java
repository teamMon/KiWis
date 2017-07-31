package com.example.jpar4.kiwis.kiwis.adater;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jpar4.kiwis.Kiwis;
import com.example.jpar4.kiwis.R;
import com.example.jpar4.kiwis.kiwis.func.MRRoundedImageView;
import com.example.jpar4.kiwis.kiwis.func.PicMethod;
import com.example.jpar4.kiwis.kiwis.model.PostItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.jpar4.kiwis.kiwis.func.PicMethod.exifOrientationToDegrees;
import static com.example.jpar4.kiwis.kiwis.func.PicMethod.rotate;

/**
 * Created by jpar4 on 2017-06-02.
 */

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder>{

    private Context mContext;
    private  ArrayList<PostItem> postItems;
    private Kiwis app ;
    final int EDIT_ITEM_POST = 1002;
    private LayoutInflater inflater;
    private ViewGroup viewGroup;
    //애니메이션테스트를 위해 홀더와 포지션 추가
    //PostViewHolder holder;
 //   int position;
    //View v;

    public PostAdapter(Context context, ArrayList<PostItem> listItem) {
        this.mContext=context;
        this.postItems = listItem;
        inflater = LayoutInflater.from(context);
        app = (Kiwis)this.mContext.getApplicationContext();
    }


 // 갑자기 안됨.
    private int lastPosition = -1;
    private void setAnimation(View viewToAnimate, int position) {
        // 새로 보여지는 뷰라면 애니메이션을 해줍니다
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.translate);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }




    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {

        //this.holder = holder;
  //      this.position = position;

      setAnimation(holder.itemView, position);
       // Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.translate);
       // holder.itemView.startAnimation(animation);
        //Log.e("[Adapter 93]", holder.itemView.toString());

        PostItem item = postItems.get(position);
        holder.tvUserId.setText(item.getUserName());
        holder.tvUserName.setText(item.getUserName());
        holder.tvPostText.setText(item.getPostText());
        holder.tvLikeCount.setText(String.valueOf(item.getPostLikeCount()));
        if(!holder.tvUserId.getText().equals(app.getUserID())){
            holder.ebtn_editdel.setVisibility(View.GONE);
        }else{
            holder.ebtn_editdel.setVisibility(View.VISIBLE);
        }

     /*   if(item.isUserLike()){
            holder.cbLike.setChecked(true);
        }
        else{
            holder.cbLike.setChecked(false);
        }
*/
        //View v = inflater.inflate(R.layout.post_item,viewGroup,false);
        //Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.heard_tonado);
        if(item.getLikeCheck().has(app.getUserID())){ // 좋아요 목록에 이름이 있으면 true
            Boolean a = item.getLikeCheck().has(app.getUserID());
            Log.e("알려줘", a.toString());
            holder.cbLike.setChecked(true);
          //  holder.cbLike.startAnimation(hyperspaceJumpAnimation);


        }
        else{// 없으면 false
            holder.cbLike.setChecked(false);
            //holder.cbLike.startAnimation(hyperspaceJumpAnimation);

        }




        //이미지
        View v = inflater.inflate(R.layout.post_item,viewGroup,false);
        ImageView iv_img = (ImageView)v.findViewById(R.id.iv_img);

        if(item.getPostImgUrl().equals("")){
           // iv_img.setImageResource(R.mipmap.ic_photo);
            holder.ivImg.setImageResource(R.mipmap.ic_photo);
        }else{
            holder.ivImg.setImageURI(Uri.parse(item.getPostImgUrl()));
            Bitmap image_bitmap = null;
            try {

           /*     BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize=2;*/
                image_bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse(item.getPostImgUrl()));

                String filepath = PicMethod.getRealPathFromUri(mContext, Uri.parse(item.getPostImgUrl()));
                //이미지 리싸이징
                //image_bitmap=resizeBitmapImageFn(filepath);

                // 이미지를 상황에 맞게 회전시킨다
                ExifInterface exif = new ExifInterface(filepath);
                int exifOrientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                int exifDegree = exifOrientationToDegrees(exifOrientation);
                image_bitmap = rotate(image_bitmap, exifDegree);


            } catch (IOException e) {
                e.printStackTrace();
            }
            iv_img.setImageBitmap(image_bitmap);
            holder.ivImg.setImageBitmap(image_bitmap);
        }

        //프로필사진
        ImageView iv_profilepic = (ImageView)v.findViewById(R.id.iv_profilepic);
        if(!item.getPicName().equals("basic.jpg")){

            File pathpath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/imagefolder/" + item.getPicName());
            //String pathpath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/imagefolder/123.jpg";
            Uri uripath = FileProvider.getUriForFile(mContext, "com.example.jpar4.kiwis", pathpath);
            Bitmap image_bitmap = null;
            try {
                image_bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uripath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap image_bitmap2 = MRRoundedImageView.getCroppedBitmap(image_bitmap, 50);
            iv_profilepic.setImageBitmap(image_bitmap2);
            holder.iv_profilepic.setImageBitmap(image_bitmap2);
        }else{
            iv_profilepic.setImageResource(R.mipmap.ic_launcher_round);
            holder.iv_profilepic.setImageResource(R.mipmap.ic_launcher_round);
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewGroup= parent;
        //View baseView = View.inflate(mContext, R.layout.post_item2, null);
        View baseView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
     /*   View baseView = inflater.inflate(R.layout.post_item, parent, false);*/
        PostViewHolder postViewHolder = new PostViewHolder(baseView, this, mContext);//홀더에서 PostItem에 있는 정보를 사용하기위해 this (adapter)를 넘겨줌.
        return postViewHolder;
    }
    

    @Override
    public int getItemCount() {
        return postItems.size();
    }

    public void onClickLike(int position) {
        PostItem item = postItems.get(position);

        SharedPreferences itemPost = mContext.getSharedPreferences("itemPost", MODE_PRIVATE);
        String jsonString = itemPost.getString("itemList","defValue");
        // Toast.makeText(getActivity(), jsonString, Toast.LENGTH_SHORT).show();



        //2. 데이터를 기록하기 위해 itemPost.Editor 인스턴스 얻기
        SharedPreferences.Editor editor = itemPost.edit();

        try {
            JSONObject wrapObject = new JSONObject(jsonString);
            JSONArray jsonArray = new JSONArray(wrapObject.getString("itemList"));
            JSONArray itemList = new JSONArray();
            for(int i =0; i <  jsonArray.length(); i++) {
                // Array 에서 하나의 JSONObject 를 추출
                JSONObject olditem = jsonArray.getJSONObject(i);

                JSONObject newitem = new JSONObject();
                if(i == position){ //해당포지션에 있는 값만 바꿔주고
                    if( item.getLikeCheck().has(app.getUserID())){// 트루였을 때 누르면 false로 바꿔주고 마이너스 1~!
                        //item.setUserLike(false);
                       // item.setPostLikeCount(item.getPostLikeCount()-1);
                        newitem.put("itemNum", olditem.getInt("itemNum"));
                        newitem.put("userName", olditem.getString("userName"));
                        newitem.put("isUserLike", false);
                        newitem.put("postLikeCount", olditem.getInt("postLikeCount")-1);
                        newitem.put("postImgUrl",olditem.getString("postImgUrl"));
                        newitem.put("postText", olditem.getString("postText"));
                        olditem.getJSONObject("likeCheck").remove(app.getUserID()); // 있으면 지워주고 넣어주고
                        newitem.put("likeCheck", olditem.getJSONObject("likeCheck"));
                        if(olditem.getString("picName")==null){
                            Log.e("[apdt_Onlike]picName", olditem.getString("picName"));
                        }
                        newitem.put("picName", olditem.getString("picName")); ////////////////////////////////////////////////////////////////이새끼!!! 라이크 오작동!!!!
                        Log.e("position / itemNum", Integer.toString(position) + "/" + newitem.getInt("itemNum"));
                    }
                    else{
                        //item.setUserLike(true);
                        //item.setPostLikeCount(item.getPostLikeCount()+1);
                        newitem.put("itemNum", olditem.getInt("itemNum"));
                        newitem.put("userName", olditem.getString("userName"));
                        newitem.put("isUserLike", true);
                        newitem.put("postLikeCount", olditem.getInt("postLikeCount")+1);
                        newitem.put("postImgUrl",olditem.getString("postImgUrl"));
                        newitem.put("postText", olditem.getString("postText"));
                        olditem.getJSONObject("likeCheck").put(app.getUserID(),true);// 없으면 추가해서 넣어주고
                        newitem.put("likeCheck", olditem.getJSONObject("likeCheck"));
                        if(olditem.getString("picName")==null) {
                            Log.e("[apdt_Onlike]picName", olditem.getString("picName"));
                        }
                        newitem.put("picName", olditem.getString("picName"));////////////////////////////////////////////////////////////////이새끼!!! 라이크 오작동!!!!
                        Log.e("position / itemNum", Integer.toString(position) + "/" + newitem.getInt("itemNum"));
                    }

                }
                else{//나머진 그대로
                    newitem.put("itemNum", olditem.getInt("itemNum"));
                    newitem.put("userName", olditem.getString("userName"));
                    newitem.put("isUserLike", olditem.getBoolean("isUserLike"));
                    newitem.put("postLikeCount", olditem.getInt("postLikeCount"));
                    newitem.put("postImgUrl",olditem.getString("postImgUrl"));
                    newitem.put("postText", olditem.getString("postText"));
                    newitem.put("likeCheck", olditem.getJSONObject("likeCheck"));
                    newitem.put("picName", olditem.getString("picName"));////////////////////////////////////////////////////////////////이새끼!!! 라이크 오작동!!!!
                }

                itemList.put(newitem);
            }
            wrapObject.put("itemList", itemList);
            editor.remove("itemList");
            editor.putString("itemList", wrapObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        editor.commit();

        if(  item.getLikeCheck().has(app.getUserID())){// 트루였을 때 누르면 false로 바꿔주고 마이너스 1~!
            //item.setUserLike(false);
            item.delLikeCheck(app.getUserID());
            item.setPostLikeCount(item.getPostLikeCount()-1);
        }
        else{
            //item.setUserLike(true);
            item.addLikeCheck(app.getUserID());
            item.setPostLikeCount(item.getPostLikeCount()+1);
        }
       /* if(item.isUserLike()){// 트루였을 때 누르면 false로 바꿔주고 마이너스 1~!
            item.setUserLike(false);
            item.setPostLikeCount(item.getPostLikeCount()-1);
        }
        else{
            item.setUserLike(true);
            item.setPostLikeCount(item.getPostLikeCount()+1);
        }*/
        //Toast.makeText(mContext, "Like Clicked"+item.getPostText()+", P:"+position, Toast.LENGTH_SHORT).show();
        //item.setPostLikeCount(item.getPostLikeCount()+1);
        //PostItem item = new PostItem("jpar4_",true,0,"",data.getStringExtra("content"));
        postItems.set(position,item);
        this.notifyItemChanged(position);
        //this.notifyDataSetChanged();
      //  Log.e("[PostAdapter]","endCheck");
      //  Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.heard_tonado);
    //    holder.cbLike.startAnimation(hyperspaceJumpAnimation);
    }


    public void onClickEdit(int position) {
        //kiwis = (Kiwis)mContext.getApplicationContext();

        PostItem item = postItems.get(position);

        //Toast.makeText(mContext, "More Btn Clicked"+item.getPostText()+", P:"+position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(
                "com.example.jpar4.kiwis",
                "com.example.jpar4.kiwis.kiwis.AddPostItemActivity"
        );
        intent.setComponent(componentName);
        intent.putExtra("position", position);
        intent.putExtra("content", item.getPostText());
        intent.putExtra("photoUri",item.getPostImgUrl());
        intent.putExtra("REQUEST_CODE",EDIT_ITEM_POST);
        //mContext.startActivity(intent);
        ((Activity)mContext).startActivityForResult(intent, EDIT_ITEM_POST);
    }
    public void onClickDel(int position){
        SharedPreferences itemPost = mContext.getSharedPreferences("itemPost", MODE_PRIVATE);
        String jsonString = itemPost.getString("itemList","defValue");
        // Toast.makeText(getActivity(), jsonString, Toast.LENGTH_SHORT).show();

        //2. 데이터를 기록하기 위해 itemPost.Editor 인스턴스 얻기
        SharedPreferences.Editor editor = itemPost.edit();

        try {
            JSONObject wrapObject = new JSONObject(jsonString);
            JSONArray jsonArray = new JSONArray(wrapObject.getString("itemList"));
            JSONArray itemList = new JSONArray();
            for(int i =0; i <  jsonArray.length(); i++) {
                // Array 에서 하나의 JSONObject 를 추출
                JSONObject olditem = jsonArray.getJSONObject(i);

                if(i == position) continue;
                JSONObject newitem = new JSONObject();
                newitem.put("itemNum", olditem.getInt("itemNum"));
                newitem.put("userName", olditem.getString("userName"));
                newitem.put("isUserLike", olditem.getBoolean("isUserLike"));
                newitem.put("postLikeCount", olditem.getInt("postLikeCount"));
                newitem.put("postImgUrl",olditem.getString("postImgUrl"));
                newitem.put("postText", olditem.getString("postText"));
                newitem.put("likeCheck", olditem.getJSONObject("likeCheck"));
                newitem.put("picName", olditem.getString("picName"));
                itemList.put(newitem);
            }
            wrapObject.put("itemList", itemList);
            editor.remove("itemList");
            editor.putString("itemList", wrapObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        editor.commit();

        postItems.remove(position);
        this.notifyDataSetChanged();
        Toast.makeText(mContext, "게시물이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
    }
    public void editdelDialog(int position){
        final int pos = position;
        String[]  menu = new String[]{ "수정하기", "삭제하기"};
        new AlertDialog.Builder(mContext).setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){//수정
                    onClickEdit(pos);
                }
                else{//삭제
                  //  Toast.makeText(mContext, "삭제하기"+which+pos, Toast.LENGTH_SHORT).show();
                    onClickDel(pos);
                }

            }
        }).show();
    }

    public Bitmap resizeBitmapImageFn(String url) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=2;
        Bitmap srcBmp = BitmapFactory.decodeFile(url);

        ExifInterface exif = new ExifInterface(url);
        int exifOrientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);
        srcBmp = rotate(srcBmp, exifDegree);

        int iWidth   = 520;   // 축소시킬 너비
        int iHeight  = 520;   // 축소시킬 높이
        float fWidth  = srcBmp.getWidth();
        float fHeight = srcBmp.getHeight();

// 원하는 널이보다 클 경우의 설정
        if(fWidth > iWidth) {
            float mWidth = (float) (fWidth / 100);
            float fScale = (float) (iWidth / mWidth);
            fWidth *= (fScale / 100);
            fHeight *= (fScale / 100);
// 원하는 높이보다 클 경우의 설정
        }else if (fHeight > iHeight) {
            float mHeight = (float) (fHeight / 100);
            float fScale = (float) (iHeight / mHeight);
            fWidth *= (fScale / 100);
            fHeight *= (fScale / 100);
        }

        FileOutputStream fosObj = null;
        Bitmap resizedBmp = null;
        try {
            // 리사이즈 이미지 동일파일명 덮어 쒸우기 작업
            resizedBmp = Bitmap.createScaledBitmap(srcBmp, (int)fWidth, (int)fHeight, true);
            fosObj = new FileOutputStream(url);
            resizedBmp.compress(Bitmap.CompressFormat.JPEG, 100, fosObj);
        } catch (Exception e){

        } finally {
            fosObj.flush();
            fosObj.close();
        }

// 저장된 이미지를 스트림으로 불러오기
        File pathFile = new File(url);
        FileInputStream fisObj = new FileInputStream(pathFile);


        return resizedBmp;
    }

}
