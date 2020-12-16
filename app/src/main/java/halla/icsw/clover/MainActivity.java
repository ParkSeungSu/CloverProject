package halla.icsw.clover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import halla.icsw.clover.fragment.MainFragment;
import halla.icsw.clover.fragment.MessageFragment;
import halla.icsw.clover.fragment.TreerFagment;

public class MainActivity extends AppCompatActivity {
    private String mUsername;
    private BottomNavigationView bottomNavigationView;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    static private String SHARE_NAME="SHARE_REF";

    int savedCount;
    MainFragment mainFragment;
    MessageFragment messageFragment;
    TreerFagment treerFagment;

    final String TAG=MainActivity.class.getName();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.menuitem_bottombar_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,mainFragment).commit();
                    return true;
                case R.id.menuitem_bottombar_message:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,messageFragment).commit();
                    return true;
                case R.id.menuitem_bottombar_tree:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,treerFagment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SharedPrefrence 값 불러오기
        sharedPreferences=getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        savedCount=getSavedCount();
        mainFragment=new MainFragment();
        messageFragment=new MessageFragment();
        treerFagment=new TreerFagment();
        bottomNavigationView=findViewById(R.id.bottomnavigationView_main_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,mainFragment).commit();



    }
    public void setSavedCount(){
        savedCount+=1;
        editor.putInt("count",-1);
        editor.commit();
    }
    public int getSavedCount(){
        return sharedPreferences.getInt("count",-1);
    }
}