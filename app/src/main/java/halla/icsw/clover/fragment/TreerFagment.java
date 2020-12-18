package halla.icsw.clover.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private TextView nameText;

    ImageButton nameButton;
    String savedName;
    int savedCount;
    private ImageView treeImage;
    static private String SHARE_NAME = "SHARE_REF";

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tree, container, false);
        sharedPreferences = getActivity().getSharedPreferences(SHARE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        treeNameText = view.findViewById(R.id.treeName);
        savedCount = getSavedCount();

        treeNameText.setText("LV."+String.valueOf(savedCount));
        nameText=view.findViewById(R.id.nameView);
        nameText.setText(getSavedName());
        nameButton=view.findViewById(R.id.NameIgBt);
        treeImage = view.findViewById(R.id.treeImageView);
        treeImageSet();

        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());

        ad.setTitle("이름 입력");       // 제목 설정
        ad.setMessage("나무에 이름을 지어주세요.");   // 내용 설정

// EditText 삽입하기
        final EditText et = new EditText(getActivity());
        ad.setView(et);

// 확인 버튼 설정
        ad.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Text 값 받아서 로그 남기기
                String value = et.getText().toString();

                setSavedName(value);
                nameText.setText(getSavedName());
                dialog.dismiss();     //닫기
                // Event
            }
        });


// 취소 버튼 설정
        ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //닫기
                // Event
            }
        });

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.show();
            }
        });


        return view;
    }

    public void setSavedName(String name) {
        savedCount = getSavedCount() + 1;
        editor.putString("name", name);
        editor.commit();
    }

    public int getSavedCount() {
        return sharedPreferences.getInt("count", 0);
    }

    public String getSavedName() {
        return sharedPreferences.getString("name", "이름을 지정해주세요.");
    }

    public void treeImageSet() {
        savedCount = getSavedCount();
        if (savedCount >= 0 && savedCount <= 1) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.tree1).override(600,600).into(gifImage);
            Toast.makeText(getActivity(), "모두에게 응원의 메시지!", Toast.LENGTH_SHORT).show();
        } else if (savedCount >= 2 && savedCount <= 3) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.tree2).override(600,600).into(gifImage);
            Toast.makeText(getActivity(), "잎이 생겼어요!", Toast.LENGTH_SHORT).show();
        } else if (savedCount >= 4 && savedCount <= 5) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.tree3).override(600,600).into(gifImage);
            Toast.makeText(getActivity(), "쑥쑥 자랐어요!", Toast.LENGTH_SHORT).show();
        } else if (savedCount >= 6 && savedCount <= 7) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.tree4).override(600,600).into(gifImage);
            Toast.makeText(getActivity(), "무럭무럭 자랐어용!", Toast.LENGTH_SHORT).show();
        } else if (savedCount >= 8 && savedCount <= 9) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.tree5).override(600,600).into(gifImage);
            Toast.makeText(getActivity(), "쑥쑥 자랐어용!", Toast.LENGTH_SHORT).show();
        } else if (savedCount >= 10 && savedCount <= 11) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.tree6).override(600,600).into(gifImage);
            Toast.makeText(getActivity(), "정말 자상한 분이시군요!", Toast.LENGTH_SHORT).show();
        } else if (savedCount >= 12 && savedCount <= 13) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.tree7).override(600,600).into(gifImage);
            Toast.makeText(getActivity(), "와! 많은 분들의 당신의 메시지를 보고 힘을 얻었어요!", Toast.LENGTH_SHORT).show();
        } else if (savedCount >= 14 && savedCount <= 15) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.tree8).override(600,600).into(gifImage);
        } else if (savedCount >= 16 && savedCount <= 17) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.tree9).override(600,600).into(gifImage);
        } else if (savedCount >= 18 && savedCount <= 19) {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.tree10).override(600,600).into(gifImage);
        }else {
            GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(treeImage);
            Glide.with(getActivity()).load(R.drawable.tree11).override(600,600).into(gifImage);
        }
    }

}