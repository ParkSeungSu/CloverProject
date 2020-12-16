package halla.icsw.clover.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import halla.icsw.clover.MainActivity;
import halla.icsw.clover.R;



public class TreerFagment extends Fragment {

    private View view;
    private Button sendButton;
    private TextView treeNameText;
    private EditText messageEdit;
    private ImageView treeImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tree, container, false);

        sendButton=view.findViewById(R.id.sendButton);
        treeNameText=view.findViewById(R.id.treeName);
        treeNameText.setText("TEST TREE");

        treeImage=view.findViewById(R.id.treeImageView);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
        Glide.with(getActivity()).load(R.drawable.growing).into(gifImage);


        messageEdit=view.findViewById(R.id.messageEdit);


        return view;
    }
}