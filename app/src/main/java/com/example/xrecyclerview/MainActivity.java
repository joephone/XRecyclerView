package com.example.xrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.xrecyclerview.mogu.MoguAct;
import com.example.xrecyclerview.mugoo.MugooAct;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void gotoLinearActivity(View v) {
        Intent intent = new Intent();
        intent.setClass(this,LinearActivity.class);
        startActivity(intent);
    }
    public void gotoGridActivity(View v) {
        Intent intent = new Intent();
        intent.setClass(this,GridActivity.class);
        startActivity(intent);
    }
    public void gotoStaggeredGridActivity(View v) {
        Intent intent = new Intent();
        intent.setClass(this,StaggeredGridActivity.class);
        startActivity(intent);
    }

    public void gotoEmptyViewActivity(View v) {
        Intent intent = new Intent();
        intent.setClass(this, EmptyViewActivity.class);
        startActivity(intent);
    }

    public void gotoNoFreshActivity(View v) {
        Intent intent = new Intent();
        intent.setClass(this, NoFreshLinearActivity.class);
        startActivity(intent);
    }

    public void gotoNoLoadActivity(View v) {
        Intent intent = new Intent();
        intent.setClass(this, NoLoadLinearActivity.class);
        startActivity(intent);
    }

    public void gotoSoftKeyBoard(View v) {
        Intent intent = new Intent();
        intent.setClass(this, SoftKeyBoard.class);
        startActivity(intent);
    }

    public void gotoMoguActivity(View v) {
        Intent intent = new Intent();
        intent.setClass(this, MoguAct.class);
        startActivity(intent);
    }

    public void gotoMugooAct(View v) {
        Intent intent = new Intent();
        intent.setClass(this, MugooAct.class);
        startActivity(intent);
    }

}
