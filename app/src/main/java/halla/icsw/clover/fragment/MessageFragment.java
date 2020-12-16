package halla.icsw.clover.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import halla.icsw.clover.MainActivity;
import halla.icsw.clover.R;


public class MessageFragment extends Fragment {
    private EditText messageEdit;
    private Button sendButton;
    private View view;
    int savedCount;

    static private String SHARE_NAME="SHARE_REF";

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_message, container, false);
        sendButton=view.findViewById(R.id.sendButton);

        messageEdit=view.findViewById(R.id.messageEdit);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();

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

    public void uploadData(){
        String data=messageEdit.getText().toString();
        if(data != null && !data.equals("") ) {

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