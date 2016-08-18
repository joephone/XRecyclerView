package com.example.xrecyclerview.mugoo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xrecyclerview.R;
import com.example.xrecyclerview.bean.ItemVO;
import com.example.xrecyclerview.mogu.fragment.ListFragment;
import com.example.xrecyclerview.mugoo.fragment.DetailFragment;
import com.example.xrecyclerview.mugoo.fragment.RecylerFragment;
import com.example.xrecyclerview.view.AutoHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

public class MugooAct extends AppCompatActivity {
    private static final int NUM_FRAGMENT = 10;


    private LinearLayout tabLayouts;
    private LinearLayout typeLayouts;
    private ViewPager viewPager;
    private AutoHorizontalScrollView menu;


    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();
    private List<ItemVO> itemList = new ArrayList<>();

    private String[] typeTitles = {"李易峰专区","当剩女遇见桃花","春季遮肉必看",
            "甜心开胃菜","租男友","开学衣橱大改造","没有PS你可以吗","藏肉显瘦搭配"};
    private int[] typeImgs = {R.mipmap.icon1,R.mipmap.icon1,R.mipmap.icon1,R.mipmap.icon1,
            R.mipmap.icon1,R.mipmap.icon1,R.mipmap.icon1,R.mipmap.icon1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mugoo);

        viewPager = (ViewPager)findViewById(R.id.id_viewpager);
        menu = (AutoHorizontalScrollView)findViewById(R.id.id_horizontalmenu);
        tabLayouts = (LinearLayout)findViewById(R.id.tab_layout);
        typeLayouts = (LinearLayout)findViewById(R.id.id_horizontalview_layout);

        initFragments();
        initView();
        initTypeLayout();
    }

    private void initFragments() {
        DetailFragment detailFragment = new DetailFragment();
        RecylerFragment recylerFragment = new RecylerFragment();
        fragmentList.add(detailFragment);
        fragmentList.add(recylerFragment);

        for (int i = 0;i < NUM_FRAGMENT;i++){
            titles.add("title"+i);
            ListFragment fragment = new ListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type",i);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);

            LinearLayout tabLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.menu_item,null);
            final TextView textView = (TextView)tabLayout.findViewById(R.id.tab_tv);
            textView.setText(titles.get(i));
            final int id = i;
            tabLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelector(id);
                }
            });
            tabLayouts.addView(tabLayout);
            textViews.add(textView);
        }
    }


    private void initView() {
        setSelector(0);
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelector(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return NUM_FRAGMENT;
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }
        });
    }

    /**
     * 选中效果
     * @param position
     */
    private void setSelector(final int position) {
        for (int i = 0;i < NUM_FRAGMENT; i++){
            if (position == i){
                viewPager.setCurrentItem(position);
                menu.resetScrollWidth(position);
                textViews.get(i).setBackgroundResource(R.drawable.bg_nav_contacts);
            }else {
                textViews.get(i).setBackgroundResource(R.color.alpha);
            }
        }
    }

    /**
     * 初始化横向滑动的layouts
     */
    private void initTypeLayout() {
        initItemList();
        for (final ItemVO itemVO : itemList){
            FrameLayout tabLayout = (FrameLayout)LayoutInflater.from(this).inflate(R.layout.horizontal_item,null);
            ImageView imageView = (ImageView)tabLayout.findViewById(R.id.id_horizontal_item_img);
            TextView textView = (TextView)tabLayout.findViewById(R.id.id_horizontal_item_desc);
            imageView.setImageResource(itemVO.getImage());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            textView.setText(itemVO.getDesc());

            tabLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //intent进入其他页面
                    //……
                    Toast.makeText(MugooAct.this, "进入页面", Toast.LENGTH_SHORT).show();
                }
            });

            LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            vlp.setMargins(calculateDpToPx(5),calculateDpToPx(5),calculateDpToPx(5),calculateDpToPx(5));
            typeLayouts.addView(tabLayout,vlp);
        }

    }

    private void initItemList() {
        itemList.clear();
        for (int i = 0;i < typeImgs.length;i ++){
            ItemVO itemVO = new ItemVO(typeTitles[i],typeImgs[i]);
            itemList.add(itemVO);
        }
    }

    private int calculateDpToPx(int padding_in_dp){
        final float scale = getResources().getDisplayMetrics().density;
        return  (int) (padding_in_dp * scale + 0.5f);
    }

}
