package com.yanzhenjie.album.app.album;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.R;
import com.yanzhenjie.album.app.Contract;
import com.yanzhenjie.album.mvp.BasePresenter;
import com.yanzhenjie.album.util.SystemBar;

import java.io.File;

public class PreviewView extends Contract.PreviewView implements View.OnClickListener {

    private Activity mActivity;

    private ImageView mImageView;
    private VideoView mVideoView;
    private Toolbar mToolbar;

    PreviewView(Activity activity, BasePresenter presenter) {
        super(activity, presenter);
        this.mActivity = activity;
        this.mImageView = activity.findViewById(R.id.image_view);
        this.mVideoView = activity.findViewById(R.id.video_view);
        this.mToolbar = activity.findViewById(R.id.toolbar);
    }

    @Override
    public void setupViews() {
        SystemBar.invasionStatusBar(mActivity);
        SystemBar.invasionNavigationBar(mActivity);
        SystemBar.setStatusBarColor(mActivity, Color.TRANSPARENT);
        SystemBar.setNavigationBarColor(mActivity, getColor(R.color.albumSheetBottom));

        setHomeAsUpIndicator(R.drawable.album_ic_back_white);

        mToolbar.setTitle("");

        MediaController mediaController = new MediaController(mActivity);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void bindData(AlbumFile data) {
        if (data.getMediaType() == AlbumFile.TYPE_VIDEO) {
            mImageView.setVisibility(View.GONE);

            mVideoView.setVisibility(View.VISIBLE);
            mVideoView.setVideoURI(Uri.fromFile(new File(data.getPath())));
        } else {
            mVideoView.stopPlayback();
            mVideoView.setVisibility(View.GONE);

            mImageView.setVisibility(View.VISIBLE);
            Album.getAlbumConfig().getAlbumLoader().load(mImageView, data);
        }
    }

    @Override
    public void onClick(View view) {

    }
}