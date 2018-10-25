/*
 * Copyright 2017 Yan Zhenjie.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.album.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.Filter;
import com.yanzhenjie.album.FilterWithReason;
import com.yanzhenjie.album.app.album.AlbumActivity;

import java.util.ArrayList;

/**
 * <p>Album wrapper.</p>
 * Created by yanzhenjie on 17-3-29.
 */
public class AlbumMultipleWrapper extends BasicChoiceAlbumWrapper<AlbumMultipleWrapper, ArrayList<AlbumFile>, String, ArrayList<AlbumFile>> {

    private int mLimitCount = Integer.MAX_VALUE;
    private FilterWithReason<Long> mDurationFilter;

    public AlbumMultipleWrapper(Context context) {
        super(context);
    }

    /**
     * Set the list has been selected.
     *
     * @param checked the data list.
     */
    public final AlbumMultipleWrapper checkedList(ArrayList<AlbumFile> checked) {
        this.mChecked = checked;
        return this;
    }

    /**
     * Set the maximum number to be selected.
     *
     * @param count the maximum number.
     */
    public AlbumMultipleWrapper selectCount(@IntRange(from = 1, to = Integer.MAX_VALUE) int count) {
        this.mLimitCount = count;
        return this;
    }

    /**
     * Filter video duration.
     *
     * @param filter filter.
     */
    public AlbumMultipleWrapper filterDuration(FilterWithReason<Long> filter) {
        this.mDurationFilter = filter;
        return this;
    }

    @Override
    public void start() {
        AlbumActivity.sSizeFilter = mSizeFilter;
        AlbumActivity.sMimeFilter = mMimeTypeFilter;
        AlbumActivity.sDurationFilter = mDurationFilter;
        AlbumActivity.sResult = mResult;
        AlbumActivity.sCancel = mCancel;
        Intent intent = new Intent(mContext, AlbumActivity.class);
        intent.putExtra(Album.KEY_INPUT_WIDGET, mWidget);
        intent.putParcelableArrayListExtra(Album.KEY_INPUT_CHECKED_LIST, mChecked);

        intent.putExtra(Album.KEY_INPUT_FUNCTION, Album.FUNCTION_CHOICE_ALBUM);
        intent.putExtra(Album.KEY_INPUT_CHOICE_MODE, Album.MODE_MULTIPLE);
        intent.putExtra(Album.KEY_INPUT_COLUMN_COUNT, mColumnCount);
        intent.putExtra(Album.KEY_INPUT_ALLOW_CAMERA, mHasCamera);
        intent.putExtra(Album.KEY_INPUT_ALLOW_GOPRO, mHasGoPro);
        intent.putExtra(Album.KEY_INPUT_ALLOW_OTHER_FILES, mHasOtherFiles);
        intent.putExtra(Album.KEY_INPUT_LIMIT_COUNT, mLimitCount);
        intent.putExtra(Album.KEY_INPUT_FILTER_VISIBILITY, mFilterVisibility);
        intent.putExtra(Album.KEY_INPUT_CAMERA_QUALITY, mQuality);
        intent.putExtra(Album.KEY_INPUT_CAMERA_DURATION, mLimitDuration);
        intent.putExtra(Album.KEY_INPUT_CAMERA_BYTES, mLimitBytes);

        if (mRevealView != null) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) mContext, mRevealView, "albumRevealTransition");

            int revealX = (int) (mRevealView.getX() + mRevealView.getWidth() / 2);
            int revealY = (int) (mRevealView.getY() + mRevealView.getHeight() / 2);

            intent.putExtra(Album.EXTRA_CIRCULAR_REVEAL_X, revealX);
            intent.putExtra(Album.EXTRA_CIRCULAR_REVEAL_Y, revealY);

            ActivityCompat.startActivity(mContext, intent, options.toBundle());
        } else {
            mContext.startActivity(intent);
        }
    }
}