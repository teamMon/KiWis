package com.example.jpar4.kiwis.memorygame;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jpar4.kiwis.R;


public class Memory_Activity extends AppCompatActivity {
    TextView tv_time;
    Button startBtn;
    Button stopBtn;
    BackgroundTask task;
    int value;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;

    int num[];
    int btn_txt[];
    int handler_int;
    int num_click; // 클릭수 측정 버튼을 누를때마다 증가함 (1부터 시작~9끝)
    boolean isRunning;

    Handler handler;
    ButtonDrawRunnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        handler = new Handler();
        runnable = new ButtonDrawRunnable();
        tv_time = (TextView) findViewById(R.id.tv_time);
        startBtn = (Button) findViewById(R.id.startBtn);

        btn1 = (Button) findViewById(R.id.bt1);
        btn2 = (Button) findViewById(R.id.bt2);
        btn3 = (Button) findViewById(R.id.bt3);
        btn4 = (Button) findViewById(R.id.bt4);
        btn5 = (Button) findViewById(R.id.bt5);
        btn6 = (Button) findViewById(R.id.bt6);
        btn7 = (Button) findViewById(R.id.bt7);
        btn8 = (Button) findViewById(R.id.bt8);
        btn9 = (Button) findViewById(R.id.bt9);





        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBtn.setEnabled(false);
                stopBtn.setEnabled(true);
                task = new BackgroundTask();
                task.execute();

                //num = new int[9];
                btn_txt = new int[9]; //버튼의 택스트 저장
                num_click=1;// 클릭수 초기화
                handler_int=0; //그리는 순서 초기화
                num = makeRandomNum();
                isRunning=true;
                for(int i=0;i<9;i++){
                    Log.e("ddd",Integer.toString(num[i]));
                }
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 0; i <10 && isRunning; i++) {
                                Thread.sleep(500);
                                // Message msg = handler.obtainMessage();
                                // handler.sendMessage(msg);
                                handler.post(runnable);
                            }
                        } catch (Exception e) {

                        }
                    }
                });
                thread1.start();

            }
        });
        stopBtn = (Button) findViewById(R.id.stopBtn);
        stopBtn.setEnabled(false);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.cancel(true);
                startBtn.setEnabled(true);
                stopBtn.setEnabled(false);
                tv_time.setText("00:00:00");
                btn1.setText("");
                btn2.setText("");
                btn3.setText("");
                btn4.setText("");
                btn5.setText("");
                btn6.setText("");
                btn7.setText("");
                btn8.setText("");
                btn9.setText("");
            }
        });
    }

    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            value = 0;
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            while(isCancelled()== false && isRunning ){
               value++;
                if(value>6000){
                    break;
                }
                else{
                    publishProgress(value);
                }
                try{
                    Thread.sleep(10);
                }
                catch(Exception e){ }
            }
            return value;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
             value= values[0].intValue();
           String sEll = String.format("%02d:%02d:%02d",  values[0].intValue() / 100 / 60, ( values[0].intValue()/100)%60, ( values[0].intValue()) %100);
           //  tv_time.setText(values[0].toString());
            tv_time.setText(sEll);

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            isRunning=false;
   /*         btn1.setText("");
            btn2.setText("");
            btn3.setText("");
            btn4.setText("");
            btn5.setText("");
            btn6.setText("");
            btn7.setText("");
            btn8.setText("");
            btn9.setText("");*/
            //tv_time.setText("00:00:00");

            btn1.setEnabled(false);
            btn2.setEnabled(false);
            btn3.setEnabled(false);
            btn4.setEnabled(false);
            btn5.setEnabled(false);
            btn6.setEnabled(false);
            btn7.setEnabled(false);
            btn8.setEnabled(false);
            btn9.setEnabled(false);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            isRunning=false;
/*            btn1.setText("");
            btn2.setText("");
            btn3.setText("");
            btn4.setText("");
            btn5.setText("");
            btn6.setText("");
            btn7.setText("");
            btn8.setText("");
            btn9.setText("");
            tv_time.setText("00:00:00");*/

            btn1.setEnabled(false);
            btn2.setEnabled(false);
            btn3.setEnabled(false);
            btn4.setEnabled(false);
            btn5.setEnabled(false);
            btn6.setEnabled(false);
            btn7.setEnabled(false);
            btn8.setEnabled(false);
            btn9.setEnabled(false);
        }
    }

    public void onClick1(View v){
    //    Toast.makeText(this, Integer.toString(btn_txt[0])+", "+Integer.toString(num_click), Toast.LENGTH_SHORT).show();
        if(btn_txt[0] == num_click){
            btn1.setText(Integer.toString(btn_txt[0])); // 맞으면 번호 보여줌
        }
        else{
            task.cancel(true);// 틀리면 게임종료
            Toast.makeText(this, "Mission Failure", Toast.LENGTH_SHORT).show();
        }
        num_click++;
        num9Check(num_click);

    }
    public void onClick2(View v){
    //   Toast.makeText(this, Integer.toString(btn_txt[1])+", "+Integer.toString(num_click), Toast.LENGTH_SHORT).show();
        if(btn_txt[1] == num_click){
            btn2.setText(Integer.toString(btn_txt[1])); // 맞으면 번호 보여줌
        }
        else{
            task.cancel(true);// 틀리면 게임종료
            Toast.makeText(this, "Mission Failure", Toast.LENGTH_SHORT).show();
        }
        num_click++;
        num9Check(num_click);


    }
    public void onClick3(View v){
  //      Toast.makeText(this, Integer.toString(btn_txt[2])+", "+Integer.toString(num_click), Toast.LENGTH_SHORT).show();
        if(btn_txt[2] == num_click){
            btn3.setText(Integer.toString(btn_txt[2])); // 맞으면 번호 보여줌
        }
        else{
            task.cancel(true);// 틀리면 게임종료
            Toast.makeText(this, "Mission Failure", Toast.LENGTH_SHORT).show();
        }
        num_click++;
        num9Check(num_click);

    }
    public void onClick4(View v){
     //   Toast.makeText(this, Integer.toString(btn_txt[3])+", "+Integer.toString(num_click), Toast.LENGTH_SHORT).show();
        if(btn_txt[3] == num_click){
            btn4.setText(Integer.toString(btn_txt[3])); // 맞으면 번호 보여줌

        }
        else{
            task.cancel(true);// 틀리면 게임종료
            Toast.makeText(this, "Mission Failure", Toast.LENGTH_SHORT).show();
        }
        num_click++;
        num9Check(num_click);

    }
    public void onClick5(View v){
  //      Toast.makeText(this, Integer.toString(btn_txt[4])+", "+Integer.toString(num_click), Toast.LENGTH_SHORT).show();
        if(btn_txt[4] == num_click){
            btn5.setText(Integer.toString(btn_txt[4])); // 맞으면 번호 보여줌
        }
        else{
            task.cancel(true);// 틀리면 게임종료
            Toast.makeText(this, "Mission Failure", Toast.LENGTH_SHORT).show();
        }
        num_click++;
        num9Check(num_click);

    }
    public void onClick6(View v){
      //  Toast.makeText(this, Integer.toString(btn_txt[5])+", "+Integer.toString(num_click), Toast.LENGTH_SHORT).show();
        if(btn_txt[5] == num_click){
            btn6.setText(Integer.toString(btn_txt[5])); // 맞으면 번호 보여줌
        }
        else{
            task.cancel(true);// 틀리면 게임종료
            Toast.makeText(this, "Mission Failure", Toast.LENGTH_SHORT).show();
        }
        num_click++;
        num9Check(num_click);

    }
    public void onClick7(View v){
    //    Toast.makeText(this, Integer.toString(btn_txt[6])+", "+Integer.toString(num_click), Toast.LENGTH_SHORT).show();
        if(btn_txt[6] == num_click){
            btn7.setText(Integer.toString(btn_txt[6])); // 맞으면 번호 보여줌
        }
        else{
            task.cancel(true);// 틀리면 게임종료
            Toast.makeText(this, "Mission Failure", Toast.LENGTH_SHORT).show();
        }
        num_click++;
        num9Check(num_click);

    }
    public void onClick8(View v){
    //    Toast.makeText(this, Integer.toString(btn_txt[7])+", "+Integer.toString(num_click), Toast.LENGTH_SHORT).show();
        if(btn_txt[7] == num_click){
            btn8.setText(Integer.toString(btn_txt[7])); // 맞으면 번호 보여줌
        }
        else{
            task.cancel(true);// 틀리면 게임종료
            Toast.makeText(this, "Mission Failure", Toast.LENGTH_SHORT).show();
        }
        num_click++;
        num9Check(num_click);

    }
    public void onClick9(View v){
      //  Toast.makeText(this, Integer.toString(btn_txt[8])+", "+Integer.toString(num_click), Toast.LENGTH_SHORT).show();
        if(btn_txt[8] == num_click){
            btn9.setText(Integer.toString(btn_txt[8])); // 맞으면 번호 보여줌
        }
        else{
            task.cancel(true);// 틀리면 게임종료
            Toast.makeText(this, "Mission Failure", Toast.LENGTH_SHORT).show();
        }
        num_click++;
        num9Check(num_click);

    }
    public void num9Check(int num_click){
        if(num_click == 10){
            Toast.makeText(this, "Misson Success" + tv_time.getText(), Toast.LENGTH_SHORT).show();
            isRunning= false;
        }else{
            //Toast.makeText(this, "Misson ?" + tv_time.getText()+", "+num_click, Toast.LENGTH_SHORT).show();
        }
    }


    public int[] makeRandomNum(){
        int[] randomSet = new int[10];
        randomSet[9]=10;
        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                randomSet[j]= j+1;
            }
        }
        //첫줄 랜덤 셔플.
        for(int i =0; i<9;i++) {
            int x = (int) (Math.random() * 9);// 10 입력시 범위 : 0 ~ 9
            int temp = randomSet[i];
            randomSet[i] = randomSet[x];
            randomSet[x] = temp;
        }

        return randomSet;
    }

    public class ButtonDrawRunnable implements Runnable {
        @Override
        public void run() {
            Log.e("dddd",Integer.toString(handler_int));
            if(handler_int>9){
                handler_int=0;
            }
            switch (num[handler_int]){
                case 1:
                    btn1.setText(Integer.toString(handler_int+1));
                    btn_txt[0]=handler_int+1;
               /*     try{
                       Thread.sleep(500);
                        btn1.setText("");
                    }
                    catch(Exception e){ }*/
                    break;
                case 2:
                    btn2.setText(Integer.toString(handler_int+1));
                    btn_txt[1]=handler_int+1;
             /*       try{
                        Thread.sleep(500);
                        btn2.setText("");
                    }
                    catch(Exception e){ }*/
                    break;
                case 3:
                    btn3.setText(Integer.toString(handler_int+1));
                    btn_txt[2]=handler_int+1;
                  /*  try{
                        Thread.sleep(500);
                        btn3.setText("");
                    }
                    catch(Exception e){ }*/
                    break;
                case 4:
                    btn4.setText(Integer.toString(handler_int+1));
                    btn_txt[3]=handler_int+1;
             /*       try{
                        Thread.sleep(500);
                        btn4.setText("");
                    }
                    catch(Exception e){ }*/
                    break;
                case 5:
                    btn5.setText(Integer.toString(handler_int+1));
                    btn_txt[4]=handler_int+1;
           /*         try{
                        Thread.sleep(500);
                        btn5.setText("");
                    }
                    catch(Exception e){ }*/
                    break;
                case 6:
                    btn6.setText(Integer.toString(handler_int+1));
                    btn_txt[5]=handler_int+1;
              /*      try{
                        Thread.sleep(500);
                        btn6.setText("");
                    }
                    catch(Exception e){ }*/
                    break;
                case 7:
                    btn7.setText(Integer.toString(handler_int+1));
                    btn_txt[6]=handler_int+1;
            /*        try{
                        Thread.sleep(500);
                        btn7.setText("");
                    }
                    catch(Exception e){ }*/
                    break;
                case 8:
                    btn8.setText(Integer.toString(handler_int+1));
                    btn_txt[7]=handler_int+1;
      /*              try{
                        Thread.sleep(500);
                        btn8.setText("");
                    }
                    catch(Exception e){ }*/
                    break;
                case 9:
                    btn9.setText(Integer.toString(handler_int+1));
                    btn_txt[8]=handler_int+1;
                    break;
                default://randumset[9]에 10을 넣어줘서 마지막에 초기화 되게 했음.
                    btn1.setText("");
                    btn2.setText("");
                    btn3.setText("");
                    btn4.setText("");
                    btn5.setText("");
                    btn6.setText("");
                    btn7.setText("");
                    btn8.setText("");
                    btn9.setText("");

                    btn1.setEnabled(true);
                    btn2.setEnabled(true);
                    btn3.setEnabled(true);
                    btn4.setEnabled(true);
                    btn5.setEnabled(true);
                    btn6.setEnabled(true);
                    btn7.setEnabled(true);
                    btn8.setEnabled(true);
                    btn9.setEnabled(true);
                    break;
            }
                handler_int++;
           /* if(handler_int>9){

            }*/
           if(!isRunning){
               btn1.setText("");
               btn2.setText("");
               btn3.setText("");
               btn4.setText("");
               btn5.setText("");
               btn6.setText("");
               btn7.setText("");
               btn8.setText("");
               btn9.setText("");
           }

        }
    }
}

