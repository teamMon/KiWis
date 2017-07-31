package com.example.jpar4.kiwis.kiwis.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jpar4.kiwis.R;

import static com.example.jpar4.kiwis.R.id.iv_img;


/**
 * Created by jpar4 on 2017-06-02.
 */

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageButton ebtn_editdel;
    public CheckBox cbLike;
    public ImageView ivImg, iv_profilepic,ivLike, ivShare;
    public TextView tvLikeCount, tvUserName, tvPostText, tvUserId;
    private PostAdapter mAdapter;
    public  Context context;
   // View itemView;



    public PostViewHolder(View itemView, PostAdapter postAdapter, Context mContext) {
        super(itemView);
        this.mAdapter = postAdapter;
        this.context = mContext;


        ivImg = (ImageView)itemView.findViewById(iv_img);
        iv_profilepic = (ImageView)itemView.findViewById(R.id.iv_profilepic);

        cbLike = (CheckBox)itemView.findViewById(R.id.cb_like);
        /*ivLike = (ImageView)itemView.findViewById(R.id.iv_like);*/
        //ivShare = (ImageView)itemView.findViewById(R.id.iv_share);

        tvLikeCount = (TextView) itemView.findViewById(R.id.tv_likecount);
        tvUserName = (TextView) itemView.findViewById(R.id.tv_username);
        tvUserId = (TextView) itemView.findViewById(R.id.tv_userid);
        tvPostText = (TextView) itemView.findViewById(R.id.tv_posttext);

        ebtn_editdel = (ImageButton) itemView.findViewById(R.id.ebtn_editdel);


        /*ivLike.setOnClickListener(this);*/
        cbLike.setOnClickListener(this);
//        ivShare.setOnClickListener(this);
        ebtn_editdel.setOnClickListener(this);
        ivImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition(); // 현재 얻는 포지션이 어느부분인지 얻어올수 있음
/*        //리스트의 갯수 얻어옴.
        SharedPreferences itemPost = context.getSharedPreferences("itemPost", MODE_PRIVATE);
        String jsonString = itemPost.getString("itemList","defValue");
        JSONObject wrapObject = null;
        try {
            wrapObject = new JSONObject(jsonString);
            JSONArray jsonArray = new JSONArray(wrapObject.getString("itemList"));
            int pos = ((jsonArray.length())-(getAdapterPosition())-1);
           // Log.e("[Holder 80] pos", Integer.toString(pos));
            position = pos;
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        switch(v.getId()){
            case R.id.cb_like:


                mAdapter.onClickLike(position);
                /////////////////////////////////////////////////////////////////////////////////////// 암됨..
                //   Log.e("[Holder]","conduct check");
                Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.heard_tonado);
                cbLike.startAnimation(hyperspaceJumpAnimation);
                ///////////////////////////////////////////////////////////////////////////////////////
                break;
           /* case R.id.iv_share:
                break;*/
            case R.id.ebtn_editdel:
               // mAdapter.onClickMore(position);
                mAdapter.editdelDialog(position);
                break;
            case iv_img:

                Animation hyperspaceJumpAnimation1 = AnimationUtils.loadAnimation(context, R.anim.scale);
                ivImg.startAnimation(hyperspaceJumpAnimation1);
        }
    }
}
