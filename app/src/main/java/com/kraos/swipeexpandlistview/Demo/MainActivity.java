package com.kraos.swipeexpandlistview.Demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.kraos.swipeexpandlistview.R;

public class MainActivity extends AppCompatActivity {

    private Fragment[] fragments;
    private int selectedIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments = new Fragment[]{new ExpandableDemoFragment(),new ExpandableDemoFragment(),new ExpandableDemoFragment()};
        selectedIndex = -1;
        switchContent(0);
    }
    private void switchContent(int index){
        if(selectedIndex!=index) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragments[index])
                    .commit();
            selectedIndex = index;
        }
    }

}
