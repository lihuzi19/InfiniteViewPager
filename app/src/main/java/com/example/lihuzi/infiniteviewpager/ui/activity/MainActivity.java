package com.example.lihuzi.infiniteviewpager.ui.activity;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lihuzi.infiniteviewpager.R;
import com.example.lihuzi.infiniteviewpager.ui.adapter.WholePictureDisplayAdapter;
import com.example.lihuzi.infiniteviewpager.ui.view.DLFloatViewGroup;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    ViewPager _viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _viewpager = findViewById(R.id.act_main_viewpager);
        initView();
    }

    private void initView() {
        final ArrayList pictureList = new ArrayList();
        pictureList.add("https://img4q.duitang.com/uploads/item/201504/11/20150411H1327_QajcZ.jpeg");
        pictureList.add("https://b-ssl.duitang.com/uploads/item/201808/14/20180814175618_hypnp.jpeg");
        pictureList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547793747&di=422d3887efee0b0437ec457588dfa9a2&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fmobile%2F2019-01-07%2F5c33056a66916.jpg");
        WholePictureDisplayAdapter adapter = new WholePictureDisplayAdapter() {
            @Override
            public int setCount() {
                return pictureList.size();
            }

            @Override
            public View initCurrentView(View v, int position) {
                ImageView iv = v.findViewById(R.id.item_main_iv);
                Glide.with(_viewpager.getContext()).load(pictureList.get(position)).into(iv);
                DLFloatViewGroup floatView = v.findViewById(R.id.item_main_floatview);
                floatView.initDLFloatView(null, null);
                int visible = position == pictureList.size() - 1 ? View.VISIBLE : View.GONE;
                System.out.println("visible:" + visible);
                floatView.setVisibility(visible);
                return v;
            }

            @Override
            public int setResourceId(int resId) {
                return R.layout.item_main;
            }

            @Override
            public ViewPager setViewPager() {
                return _viewpager;
            }
        };
        adapter.setInfinite(true);
        _viewpager.setAdapter(adapter);
    }

}
