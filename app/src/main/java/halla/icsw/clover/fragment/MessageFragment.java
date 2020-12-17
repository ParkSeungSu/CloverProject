package halla.icsw.clover.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;


import halla.icsw.clover.R;

import static android.content.Context.MODE_PRIVATE;


public class MessageFragment extends Fragment {
    private EditText messageEdit;
    private Button sendButton;
    private View view;
    int savedCount;

    Intent intent;
    SpeechRecognizer mRecognizer;
    ImageButton sttBtn;
    final int PERMISSION=1;

    static private String SHARE_NAME="SHARE_REF";

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_message, container, false);
        sendButton=view.findViewById(R.id.sendButton);
        sharedPreferences=getActivity().getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        editor=sharedPreferences.edit();

        messageEdit=view.findViewById(R.id.messageEdit);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();

            }
        });

        if(Build.VERSION.SDK_INT>=23){
            //퍼미션 체크
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.INTERNET,
            Manifest.permission.RECORD_AUDIO},PERMISSION);
        }
        sttBtn=view.findViewById(R.id.voiceButton);
        intent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getActivity().getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        sttBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecognizer=SpeechRecognizer.createSpeechRecognizer(getActivity());
                mRecognizer.setRecognitionListener(listener);
                mRecognizer.startListening(intent);
            }
        });


        return view;
    }

    public void setSavedCount(){
        savedCount=getSavedCount()+1;
        editor.putInt("count",savedCount);
        editor.commit();
    }
    public int getSavedCount(){
        return sharedPreferences.getInt("count",0);
    }


    private RecognitionListener listener=new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {
            sttBtn=view.findViewById(R.id.voiceButton);
            Toast.makeText(getActivity(), "음성인식을 시작합니다.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
            sttBtn=view.findViewById(R.id.voiceButton);
        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {
            sttBtn=view.findViewById(R.id.voiceButton);
        }

        @Override
        public void onError(int i) {
            String message;

            switch (i){
                case SpeechRecognizer.ERROR_AUDIO:
                    message="오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message="클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message="퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message="네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message="네트워크 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message="찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message="RECOGNIZER가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message="서버 에러";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message="말하는 시간 초과";
                    break;
                default:
                    message="알 수 없는 오류";
                    break;
            }

            Toast.makeText(getActivity(), "에러가 발생했습니다. :"+message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle bundle) {
            //말을 하면 ArrayList에 단어를 넣고 EditText에 단어를 이어줍니다.
            ArrayList<String> matches=
                    bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for(int i=0;i<matches.size();i++){
                messageEdit.setText(matches.get(i));
            }
        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };

    public void uploadData(){
        String data=messageEdit.getText().toString();
        if(data != null && !data.equals("") ) {
            insertToDatabase(data);
            setSavedCount();
            messageEdit.setText("");
            Toast.makeText(getActivity(), "멋진 메시지에요!", Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(getActivity(), "메시지를 다시한번 확인해 주세요", Toast.LENGTH_SHORT).show();

        }
    }
    private void insertToDatabase(String msg){


        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(String... params) {

                try{

                    String msg = (String)params[0];

                    String link="http://contest.dothome.co.kr/android/contents.php";
                    String data  = URLEncoder.encode("msg", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(msg);
    }
}