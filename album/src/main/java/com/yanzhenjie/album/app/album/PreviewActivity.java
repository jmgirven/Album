package com.yanzhenjie.album.app.album;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.R;
import com.yanzhenjie.album.app.Contract;
import com.yanzhenjie.album.mvp.BaseActivity;
import com.yanzhenjie.album.mvp.BasePresenter;

/**
 * <p>Preview the pictures in the folder in enlarged form.</p>
 */
public class PreviewActivity extends BaseActivity implements BasePresenter {

    public static AlbumFile sAlbumFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_activity_preview);

        Contract.PreviewView previewView = new PreviewView(this, this);
        previewView.setupViews();
        previewView.bindData(sAlbumFile);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        sAlbumFile = null;
        super.finish();
    }
}