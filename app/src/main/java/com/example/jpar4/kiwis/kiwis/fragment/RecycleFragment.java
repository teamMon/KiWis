package com.example.jpar4.kiwis.kiwis.fragment;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jpar4.kiwis.Kiwis;
import com.example.jpar4.kiwis.R;
import com.example.jpar4.kiwis.kiwis.adater.PostAdapter;
import com.example.jpar4.kiwis.kiwis.model.PostItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecycleFragment extends Fragment {
    //private mContext;
    final int ADD_ITEM_POST = 1001;
    final int EDIT_ITEM_POST = 1002;

    private RecyclerView rvList;
    ArrayList<PostItem> listItem;
    PostAdapter adapter;
    private Kiwis app;
    //public ImageButton ebtn_editdel;

    public RecycleFragment() {
        // Required empty public constructor
        //this.mContext = getContext();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycle, container, false);
        app = (Kiwis) getActivity().getApplicationContext();
        rvList = (RecyclerView) view.findViewById(R.id.kiwis_rv_list);


        listItem = new ArrayList<>();

        SharedPreferences itemPost = getActivity().getSharedPreferences("itemPost", MODE_PRIVATE);
        SharedPreferences memberList = getActivity().getSharedPreferences("memberList", MODE_PRIVATE);

        String jsonString = itemPost.getString("itemList", "defValue");
        String memberListjsonString = memberList.getString("memberList", "defValue");

      //  Toast.makeText(getActivity(), app.getProfilePath(), Toast.LENGTH_SHORT).show();
        if (jsonString.equals("defValue")) { //  itemPost가 없을 때

        } else { // itemPost가 있을때
            try {
                JSONObject wrapObject = new JSONObject(jsonString);
                JSONArray jsonArray = new JSONArray(wrapObject.getString("itemList"));
                Log.e("[Recy]onCre,jsnarr", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
           /*     for (int i = jsonArray.length()-1; 0 <= i; i--) {*/
                    // Array 에서 하나의 JSONObject 를 추출
                    JSONObject item = jsonArray.getJSONObject(i);
Log.e("jsonArray.length() / i", Integer.toString(jsonArray.length())+"/"+Integer.toString(i));
                    PostItem putitem = new PostItem(
                            item.getInt("itemNum"),
                            item.getString("userName"),
                            item.getBoolean("isUserLike"),
                            item.getInt("postLikeCount"),
                            item.getString("postImgUrl"),
                            item.getString("postText"),
                            item.getJSONObject("likeCheck"),
                            item.getString("picName"));

                    listItem.add(putitem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        adapter = new PostAdapter(getActivity(), listItem);

        rvList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //rvList.setItemAnimator();
        rvList.setItemAnimator(new DefaultItemAnimator() {//  this.notifyItemChanged(position);시 깜빡임 방지.
            @Override
            public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
                return true;
            }

            @Override
            public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull List<Object> payloads) {
                return true;
            }
        });
        rvList.setAdapter(adapter);
        if (listItem.size() >= 0) rvList.scrollToPosition(listItem.size() - 1);


        // Inflate the layout for this fragment
        view.findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName(
                        "com.example.jpar4.kiwis",
                        "com.example.jpar4.kiwis.kiwis.AddPostItemActivity"
                );
                intent.setComponent(componentName);
                intent.putExtra("REQUEST_CODE", ADD_ITEM_POST);
                startActivityForResult(intent, ADD_ITEM_POST);
            }
        });
        adapter.notifyDataSetChanged();
        return view;
    }
    //Uri에서 이미지 이름을 얻어온다.
    //String name_Str = getImageNameToUri(data.getData());

    //이미지 데이터를 비트맵으로 받아온다.
    //   Uri filepath = Uri.parse(app.getFilepath());

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_POST && resultCode == Activity.RESULT_OK) {
            //1. SharedPreferences인스턴스 얻기
            SharedPreferences itemPost = getActivity().getSharedPreferences("itemPost", MODE_PRIVATE);
            SharedPreferences conMember = getActivity().getSharedPreferences("conMember", MODE_PRIVATE);

            String jsonString = itemPost.getString("itemList", "defValue");
            String jsonString2 = conMember.getString("conMember", "defValue");


            //2. 데이터를 기록하기 위해 itemPost.Editor 인스턴스 얻기
            SharedPreferences.Editor editor = itemPost.edit();

            //3_0. 값 가져오기
            String photoUri = data.getStringExtra("photoUri");

            //3. 값넣기
            JSONObject wrapObject = null;
            PostItem putitem = null;
            try { // 첫번째값 저장
                if (jsonString.equals("defValue")) {

                    //3. 데이터 저장하기
                    //3-1. Json Object item에 값저장 값 infoMen에서 유저네임가져옴
                    wrapObject = new JSONObject();
                    JSONArray itemList = new JSONArray();
                    JSONObject item = new JSONObject();

                    JSONObject infoMem = new JSONObject(jsonString2);
                    Log.e("[ReFrg 160]:infoMem", infoMem.toString());
                    item.put("itemNum", 1);
                    item.put("userName", infoMem.getString("id"));
                    item.put("isUserLike", false);
                    item.put("postLikeCount", 0);
                    item.put("postImgUrl", photoUri);
                    item.put("postText", data.getStringExtra("content"));
                    item.put("likeCheck", new JSONObject());
                    item.put("picName", infoMem.getString("profilefile_name"));

                    //item.put("picName",infoMem.getString("id")+".jpg");
                    itemList.put(item);

                    wrapObject.put("itemList", itemList);

                    putitem = new PostItem(
                            item.getInt("itemNum"),
                            item.getString("userName"),
                            item.getBoolean("isUserLike"),
                            0,
                            item.getString("postImgUrl"),
                            item.getString("postText"),
                            item.getJSONObject("likeCheck"),
                            item.getString("picName"));
                    //app.getProfilePath());


                } else { //두번째 이후
                    wrapObject = new JSONObject(jsonString);
                    JSONArray itemList = new JSONArray(wrapObject.getString("itemList"));
                    JSONObject item = new JSONObject();

                    JSONObject infoMem = new JSONObject(jsonString2);

                    item.put("itemNum", itemList.length() + 1); // 추가하기 전이라 아직 1임( 맨처음에 1로 시작했으므로 1다음에 2가 되어야함)
                    item.put("userName", infoMem.getString("id"));
                    item.put("isUserLike", false);
                    item.put("postLikeCount", 0);
                    item.put("postImgUrl", photoUri);
                    item.put("postText", data.getStringExtra("content"));
                    item.put("likeCheck", new JSONObject());
                    item.put("picName", infoMem.getString("profilefile_name"));
                    ////////////////////////////////////////////////////////////////////////////////////////////////////이새끼네!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    //       item.put("picName",  item.getString("picName"));
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    itemList.put(item);


                    wrapObject.put("itemList", itemList);

                    putitem = new PostItem(
                            item.getInt("itemNum"),
                            item.getString("userName"),
                            item.getBoolean("isUserLike"),
                            0,
                            item.getString("postImgUrl"),
                            item.getString("postText"),
                            item.getJSONObject("likeCheck"),
                            item.getString("picName"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //3-2. SharedPreferences에 데이터 저장
            editor.putString("itemList", wrapObject.toString());
            Log.e("add_itemList", wrapObject.toString());//픽네임들어있는지 확인
            //4. 완료하기
            editor.commit();

            //listItem.add(0,putitem);
            listItem.add(putitem);

            adapter.notifyDataSetChanged();
            rvList.scrollToPosition(listItem.size() - 1);
        }

        if (requestCode == EDIT_ITEM_POST && resultCode == Activity.RESULT_OK) {
            SharedPreferences itemPost = getActivity().getSharedPreferences("itemPost", MODE_PRIVATE);
            String jsonString = itemPost.getString("itemList", "defValue");
            int position = data.getIntExtra("position", 0);
            // Toast.makeText(getActivity(), jsonString, Toast.LENGTH_SHORT).show();

            //2. 데이터를 기록하기 위해 itemPost.Editor 인스턴스 얻기
            SharedPreferences.Editor editor = itemPost.edit();
            try {
                JSONObject wrapObject = new JSONObject(jsonString); // 이거 이상한데??
                Log.e("wrapObject 값 ", wrapObject.toString());
                //JSONObject wrapObject = new JSONObject();
                JSONArray jsonArray = new JSONArray(wrapObject.getString("itemList"));
                Log.e("jsonArray 값 ", jsonArray.toString());
                String a = data.getStringExtra("photoUri");
                Log.e("getStr(photoUri)", a);
                String b = data.getStringExtra("content");
                Log.e("getStr(content)", b);
                JSONArray itemList = new JSONArray();
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Array 에서 하나의 JSONObject 를 추출
                    JSONObject olditem = jsonArray.getJSONObject(i);
                    JSONObject newitem = new JSONObject();
                    if (i == position) {
                        newitem.put("itemNum", olditem.getInt("itemNum"));
                        newitem.put("userName", olditem.getString("userName"));
                        newitem.put("isUserLike", olditem.getBoolean("isUserLike"));
                        newitem.put("postLikeCount", olditem.getInt("postLikeCount"));
                        newitem.put("postImgUrl", data.getStringExtra("photoUri"));//바꿀수 있는거 사진
                        newitem.put("postText", data.getStringExtra("content"));// 내용 두개 나머지는 그대로
                        newitem.put("likeCheck", olditem.getJSONObject("likeCheck"));
                        newitem.put("picName", olditem.getString("picName"));
                    } else {
                        newitem.put("itemNum", olditem.getInt("itemNum"));
                        newitem.put("userName", olditem.getString("userName"));
                        newitem.put("isUserLike", olditem.getBoolean("isUserLike"));
                        newitem.put("postLikeCount", olditem.getInt("postLikeCount"));
                        newitem.put("postImgUrl", olditem.getString("postImgUrl"));
                        newitem.put("postText", olditem.getString("postText"));
                        newitem.put("likeCheck", olditem.getJSONObject("likeCheck"));
                        newitem.put("picName", olditem.getString("picName"));
                    }
                    itemList.put(newitem);
                }
                wrapObject.put("itemList", itemList);
                // editor.remove("itemList");
                editor.putString("itemList", wrapObject.toString());
                Log.e("wrapObject.to", wrapObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            editor.commit();

            PostItem item = listItem.get(position);
            item.setPostText(data.getStringExtra("content"));
            item.setPostImgUrl(data.getStringExtra("photoUri"));

            listItem.set(position, item);

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
/*        Intent intent = getActivity().getIntent();
        int editCheck =intent.getIntExtra("EDIT_ITEM_POST",0);
        if(editCheck !=0){
            int position = intent.getIntExtra("position",0);
           // Toast.makeText(getActivity(), "222"+intent.getStringExtra("content"), Toast.LENGTH_SHORT).show();
            PostItem item = new PostItem("jpar4_",true,0,"",intent.getStringExtra("content"));
            listItem.set(position,item);
            adapter.notifyDataSetChanged();
            editCheck=0;
        }*/
    }
}




