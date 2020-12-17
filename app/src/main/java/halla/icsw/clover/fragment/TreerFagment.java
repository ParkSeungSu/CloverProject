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
    private TextView treeNameText;

    int savedCount;
    private ImageView treeImage;
    static private String SHARE_NAME="SHARE_REF";

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tree, container, false);
        sharedPreferences=getActivity().getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        editor=sharedPreferences.edit();

        treeNameText=view.findViewById(R.id.treeName);
        savedCount=getSavedCount();

        treeNameText.setText(String.valueOf(savedCount));

        treeImage=view.findViewById(R.id.treeImageView);
        treeImageSet();

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
        if(savedCount>=0&&savedCount<=4){
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.growplant).into(gifImage);
            Toast.makeText(getActivity(), "모두에게 응원의 메시지!", Toast.LENGTH_SHORT).show();
        }else if(savedCount>=5&&savedCount<=9) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.plant2).into(gifImage);
            Toast.makeText(getActivity(), "잎이 생겼어요!", Toast.LENGTH_SHORT).show();
        }else if(savedCount>=10&&savedCount<=14) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.leaves).into(gifImage);
            Toast.makeText(getActivity(), "쑥쑥 자랐어요!", Toast.LENGTH_SHORT).show();
        }else if(savedCount>=15&&savedCount<=19) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.plant1).into(gifImage);
            Toast.makeText(getActivity(), "무럭무럭 자랐어용!", Toast.LENGTH_SHORT).show();
        }else if(savedCount>=20&&savedCount<=24) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.plant).into(gifImage);
        }else {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.clover).into(gifImage);
        }
    }

}