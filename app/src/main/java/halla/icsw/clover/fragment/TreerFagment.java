package halla.icsw.clover.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import halla.icsw.clover.R;

import static android.content.Context.MODE_PRIVATE;


public class TreerFagment extends Fragment {

    private View view;
    private Button sendButton;
    private TextView treeNameText;
    private EditText messageEdit;
    private ImageView treeImage;
    int savedCount;
    static private String SHARE_NAME="SHARE_REF";

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tree, container, false);
        sharedPreferences=getActivity().getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        sendButton=view.findViewById(R.id.sendButton);
        treeNameText=view.findViewById(R.id.treeName);
        savedCount=getSavedCount();

        treeNameText.setText(String.valueOf(savedCount));

        treeImage=view.findViewById(R.id.treeImageView);
        treeImageSet();

        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
        Glide.with(getActivity()).load(R.drawable.growing).into(gifImage);


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
    public void treeImageSet(){
        savedCount=getSavedCount();
//        if(){
//
//        }else if() {
//
//        }else if() {
//
//        }else if(){
//
//        }else if(){
//
//        }else if(){
//
//        }else {
//
//        }
    }
    public void uploadData(){
        String data=messageEdit.getText().toString();
        if(data != null && !data.equals("") ) {

            setSavedCount();
            messageEdit.setText("");
            Toast.makeText(getActivity(), "멋진 메시지에요!", Toast.LENGTH_SHORT).show();
            treeNameText.setText(String.valueOf(getSavedCount()));

        }else{
            Toast.makeText(getActivity(), "메시지를 다시한번 확인해 주세요", Toast.LENGTH_SHORT).show();

        }
    }
}