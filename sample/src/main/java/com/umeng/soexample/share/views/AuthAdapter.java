package com.umeng.soexample.share.views;

import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.soexample.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;


public class AuthAdapter extends BaseQuickAdapter<SnsPlatform, BaseViewHolder> {
    private final FragmentActivity mActivity;
    private final ProgressDialog dialog;

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mActivity, "成功了", Toast.LENGTH_LONG).show();
            notifyDataSetChanged();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mActivity, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mActivity, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    public AuthAdapter(FragmentActivity activity,@Nullable List<SnsPlatform> data) {
        super(R.layout.app_authadapter_share, data);
        this.mActivity = activity;
        dialog = new ProgressDialog(activity);
    }
    @Override
    protected void convert(@NotNull BaseViewHolder holder, SnsPlatform platform) {
        boolean isAuth = UMShareAPI.get(mActivity).isAuthorize(mActivity, platform.mPlatform);
        ImageView img = holder.getView(R.id.adapter_image);
        img.setImageResource(ResContainer.getResourceId(mActivity, "drawable", platform.mIcon));
        TextView tv = holder.getView(R.id.name);
        tv.setText(platform.mShowWord);
        TextView authBtn = holder.getView(R.id.auth_button);
        if (isAuth) {
            authBtn.setText("删除授权");
        } else {
            authBtn.setText("授权");
        }
        authBtn.setOnClickListener(view -> {
            if (isAuth) {
                UMShareAPI.get(mActivity).deleteOauth(mActivity, platform.mPlatform, authListener);
            } else {
                UMShareAPI.get(mActivity).doOauthVerify(mActivity, platform.mPlatform, authListener);
            }
        });
    }
}

