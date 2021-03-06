package halla.icsw.clover.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.speech.tts.TextToSpeech;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import halla.icsw.clover.Item;
import halla.icsw.clover.ItemAdapter;
import halla.icsw.clover.MainActivity;
import halla.icsw.clover.R;

public class MainFragment extends Fragment{
    private TextToSpeech tts;

    private View view;

    RecyclerView recyclerView;

    ArrayList<Item> items= new ArrayList<>();

    ItemAdapter adapter;

    Button btn1, btn2, btn3, btn4, btn5;
    MediaPlayer mp;
    SeekBar seekBar;
    TextView text, test;
    public int play = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tts=new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.KOREA);

                    if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","THis Language is not supported");
                    }else{
                       // speakBtn.setEnabled(true);
                        //speakOut(); 주석 해제시 자동으로 초기화와 동시에 말함
                    }
                }else{
                    Log.e("TTS","Initilization Failed ");
                }
            }
        });
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView=view.findViewById(R.id.recycler);

        btn1 = (Button)view.findViewById(R.id.button1);
        btn2 = (Button)view.findViewById(R.id.button2);
        btn3 = (Button)view.findViewById(R.id.button3);
        btn4 = (Button)view.findViewById(R.id.button4);
        btn5 = (Button)view.findViewById(R.id.button5);
        text = (TextView)view.findViewById(R.id.text1);
        adapter= new ItemAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);

        final int[] playList = new int[4];
        playList[0] = R.raw.music1;
        playList[1] = R.raw.music2;
        playList[2] = R.raw.music3;
        playList[3] = R.raw.music4;


        mp = MediaPlayer.create(getActivity(), playList[play]);
        mp.start();
        Thread();
        seekBar = (SeekBar)view.findViewById(R.id.playbar);
        seekBar.setVisibility(ProgressBar.VISIBLE);
        seekBar.setMax(mp.getDuration());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mp.seekTo(progress);
                }
                int m = progress / 60000;
                int s = (progress % 60000) / 1000;
                String strTime = String.format("%02d:%02d", m, s);
                text.setText(strTime);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.start();

                Thread();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                try
                {
                    mp.prepare();
                }
                catch(IOException ie)
                {
                    ie.printStackTrace();
                }
                mp.seekTo(0);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                try
                {
                    mp.prepare();
                }
                catch(IOException ie)
                {
                    ie.printStackTrace();
                }
                mp.seekTo(0);

                play--;
                if(play>-1){
                    mp = MediaPlayer.create(getActivity(), playList[play]);
                    mp.start();

                    Thread();
                }else {
                    play = 3;
                    mp = MediaPlayer.create(getActivity(), playList[play]);
                    mp.start();

                    Thread();
                }


            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                try
                {
                    mp.prepare();
                }
                catch(IOException ie)
                {
                    ie.printStackTrace();
                }
                mp.seekTo(0);

                play++;
                if(play<playList.length){
                    mp = MediaPlayer.create(getActivity(), playList[play]);
                    mp.start();

                    Thread();
                }else {
                    play = 0;
                    mp = MediaPlayer.create(getActivity(), playList[play]);
                    mp.start();

                    Thread();
                }


            }
        });


        //리사이클러뷰의 레이아웃 매니저 설정
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        image();

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                image();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_light
        );
        return view;
    }


    public void Thread(){
        Runnable task = new Runnable(){


            public void run(){
                // 음악이 재생중일때
                while(mp.isPlaying()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    seekBar.setProgress(mp.getCurrentPosition());
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }



    public void image(){

        //서버의 loadDBtoJson.php파일에 접속하여 (DB데이터들)결과 받기
        //Volley+ 라이브러리 사용

        //서버주소
        String serverUrl="http://contest.dothome.co.kr/android/image.php";

        //결과를 JsonArray 받을 것이므로..
        //StringRequest가 아니라..
        //JsonArrayRequest를 이용할 것임
             JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
            //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용
            @Override
            public void onResponse(JSONArray response) {

                //파라미터로 응답받은 결과 JsonArray를 분석

                items.clear();
                adapter.notifyDataSetChanged();
                try {

                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject= response.getJSONObject(i);

                        int image_id= Integer.parseInt(jsonObject.getString("image_id")); //no가 문자열이라서 바꿔야함.
                        String name=jsonObject.getString("name");
                        String msg = jsonObject.getString("msg");


                        //이미지 경로의 경우 서버 IP가 제외된 주소이므로(uploads/xxxx.jpg) 바로 사용 불가.
                        name = "http://contest.dothome.co.kr/android/image/"+name;

                        items.add(0,new Item(image_id,name,msg)); // 첫 번째 매개변수는 몇번째에 추가 될지, 제일 위에 오도록
                        adapter.notifyItemInserted(0);
                    }
                } catch (JSONException e) {e.printStackTrace();}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //실제 요청 작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());

        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest);


    }



//
//    private void speakOut(){
//
//        CharSequence text=textView.getText();
//        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,"id1");
//    }
//
//    @Override
//    public void onDestroy() {
//        if(tts!=null){
//            tts.stop();
//            tts.shutdown();
//        }
//        super.onDestroy();
//    }

}