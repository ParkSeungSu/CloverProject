package halla.icsw.clover.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import halla.icsw.clover.R;

public class MainFragment extends Fragment{
    private TextToSpeech tts;
    private Button speakBtn;
    private TextView textView;

    private View view;
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
                        speakBtn.setEnabled(true);
                        //speakOut(); 주석 해제시 자동으로 초기화와 동시에 말함
                    }
                }else{
                    Log.e("TTS","Initilization Failed ");
                }
            }
        });
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_main, container, false);

        textView=view.findViewById(R.id.messageView);
        speakBtn=view.findViewById(R.id.speakBtn);

        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakOut();
            }
        });


        return view;
    }

    private void speakOut(){

        CharSequence text=textView.getText();
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,"id1");
    }

    @Override
    public void onDestroy() {
        if(tts!=null){
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

}