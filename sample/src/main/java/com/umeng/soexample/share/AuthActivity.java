package com.umeng.soexample.share;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.soexample.BaseActivity;
import com.umeng.soexample.R;
import com.umeng.soexample.share.views.AuthAdapter;

import java.util.ArrayList;
import java.util.List;

public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("授权");
        setBackVisibily();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView listView = findViewById(R.id.list);
        listView.setLayoutManager(layoutManager);

        List<SnsPlatform> items = new ArrayList<>();
        items.add(SHARE_MEDIA.QQ.toSnsPlatform());
        items.add(SHARE_MEDIA.SINA.toSnsPlatform());
        items.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        listView.setAdapter(new AuthAdapter(this, items));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_auth;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }
}
