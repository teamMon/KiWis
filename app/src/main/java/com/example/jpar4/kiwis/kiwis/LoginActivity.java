package com.example.jpar4.kiwis.kiwis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jpar4.kiwis.Kiwis;
import com.example.jpar4.kiwis.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    TextView tv_logo;
    EditText et_input_id;
    EditText et_input_pw;
    Button btn_login;
    Button btn_join;
    Kiwis app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        app = (Kiwis)getApplicationContext();

        tv_logo = (TextView) findViewById(R.id.tv_logo);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_bounce_effect2);
        tv_logo.startAnimation(animation);
        et_input_id = (EditText) findViewById(R.id.et_input_id);
        et_input_pw = (EditText) findViewById(R.id.et_input_pw);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_join = (Button) findViewById(R.id.btn_join);

        SharedPreferences conMember = getSharedPreferences("conMember", MODE_PRIVATE);
        String conUser = conMember.getString("conMember", "defValue");
        if(conUser.equals("defValue")){

        }
        else{
            try {
                JSONObject beforeLogin = new JSONObject(conUser);
                et_input_id.setText(beforeLogin.getString("id"));
                et_input_pw.setText(beforeLogin.getString("pw"));

               // jsonConMember.put("id", jsonMemberObject.getString("id"));
               // jsonConMember.put("pw", jsonMemberObject.getString("pw"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1. SharedPreferences인스턴스 얻기
                SharedPreferences memberList = getSharedPreferences("memberList", MODE_PRIVATE);

                String jsonString = memberList.getString("memberList","defValue");
                Log.e("[Login]jsonString", jsonString);
                int loginCheck=0;//아이디 비번체크할때 사용

                try {
                    if(jsonString.equals("defValue")){
                        Toast.makeText(LoginActivity.this, "먼저 회원가입을 해주세요~", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        /*
                            jsonString 값 = wrapOject.toString 값
                            json형식으로 저장된 스트링값을 JSONObject생성자의 인수로 넣어주면 Json형식의 값을 가진 JSONObjedt를 만들 수 있음.
                         */
                        JSONObject wrapObject = new JSONObject(jsonString);
                        Log.e("[Login]wrapObject", wrapObject.toString());
                        JSONArray jsonArray = new JSONArray(wrapObject.getString("memberList"));// wrapObject(여러개의 키값을 가지고 있는 뭉처진 JSONObject에서 memberLIst라는 키값의 밸류 값을 가져옴
                        Log.e("[Login]jsonArray", jsonArray.toString());

                        for(int i = 0; i < jsonArray.length(); i++){
                            // Array 에서 하나의 JSONObject 를 추출
                            JSONObject jsonMemberObject = jsonArray.getJSONObject(i);
                            if(et_input_id.getText().toString().equals("")){ // 아이디를 입력하지 않았을 때
                                loginCheck=3;
                            }
                            else if(et_input_pw.getText().toString().equals("")){ // 비밀번호를 입력하지 않았을 때
                                loginCheck=4;
                            }
                            else if(et_input_id.getText().toString().equals(jsonMemberObject.getString("id"))) { // ID체크 /
                                if(et_input_pw.getText().toString().equals(jsonMemberObject.getString("pw"))) { // PW체크
                                    app.setUserID(et_input_id.getText().toString()); //어플객체에 ID저장

                                    if(jsonMemberObject.getString("profilefile_name").equals(et_input_id.getText().toString()+".jpg")){
                                        String filepath= et_input_id.getText().toString()+".jpg";
                                        app.setProfilePath(filepath);
                                    }else{
                                        String filepath = jsonMemberObject.getString("profilefile_name");
                                        app.setProfilePath(filepath);
                                    }

                                    loginCheck=0;

                                    //로그인하는 멤버정보 저장
                                    //1. SharedPreferences인스턴스 얻기
                                    SharedPreferences conMember = getSharedPreferences("conMember", MODE_PRIVATE);

                                    //2. 데이터를 기록하기 위해 SharedPreferencs.Editor 인스턴스 얻기
                                    SharedPreferences.Editor editor = conMember.edit();

                                    //3. 데이터 저장하기
                                    //3-1. Json Object에 이름 id pw pwchk 저장
                                    JSONObject jsonConMember = new JSONObject();
                                    jsonConMember.put("name", jsonMemberObject.getString("name"));
                                    jsonConMember.put("id", jsonMemberObject.getString("id"));
                                    jsonConMember.put("pw", jsonMemberObject.getString("pw"));
                                    if(jsonMemberObject.has("email")){
                                        jsonConMember.put("email",  jsonMemberObject.getString("email"));
                                    }
                                    else{
                                        jsonConMember.put("email",  "");
                                    }
                                    if(jsonMemberObject.has("phone")){
                                        jsonConMember.put("phone",  jsonMemberObject.getString("phone"));
                                    }
                                    else{
                                        jsonConMember.put("phone",  "");
                                    }
                                    jsonConMember.put("profilefile_name", jsonMemberObject.getString("profilefile_name"));

                                    //3-2. SharedPreferences에 데이터 저장
                                    editor.putString("conMember", jsonConMember.toString());
                                    Log.e("[Login 113]comMember", jsonConMember.toString());
                                    //4. 완료하기
                                    editor.commit();
                                    break;
                                }
                                else{//(1) 비밀번호 다름
                                    loginCheck=1;
                                    break;
                                }
                            }
                            else{// (2)아이디 없음
                                loginCheck=2;
                            }
                        }
                        if(loginCheck==0){
                            Intent intent = new Intent(LoginActivity.this, KiwisMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(loginCheck==1){
                            Toast.makeText(LoginActivity.this, "존재하지 않는 ID 이거나,\n비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else if(loginCheck==2){//loginCheck=2;
                            Toast.makeText(LoginActivity.this, "존재하지 않는 ID 이거나,\n비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else if(loginCheck==3){
                            Toast.makeText(LoginActivity.this, "User ID를 입력해주세요", Toast.LENGTH_SHORT).show();
                            et_input_id.requestFocus();
                        }
                        else{//loginCheck==4
                            Toast.makeText(LoginActivity.this, "Password를 입력해주세요", Toast.LENGTH_SHORT).show();
                            et_input_pw.requestFocus();
                        }
Log.e("wrapObject.toString()",wrapObject.toString());
                      //  Toast.makeText(LoginActivity.this, wrapObject.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Gson g = new Gson();
                // MemberList memberList1 = g.fromJson(test, MemberList.class);
               // Toast.makeText(LoginActivity.this,memberList1.getName() + memberList1.getId() + memberList1.getPw(), Toast.LENGTH_SHORT).show();
                /*Toast.makeText(LoginActivity.this,memberList.getString("memberList","defValue"), Toast.LENGTH_SHORT).show();*/
            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,  JoinActivity.class);
                startActivity(intent);
            }
        });
    }
}
