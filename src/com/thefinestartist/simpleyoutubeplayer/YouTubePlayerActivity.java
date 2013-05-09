/**
 * Copyright 2013 The Finest Artist
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thefinestartist.simpleyoutubeplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubePlayerActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnFullscreenListener,
        YouTubePlayer.OnInitializedListener,
        YouTubePlayer.PlayerStateChangeListener {

    public static final String EXTRA_VIDEO_ID = "video_id";
    private static final boolean TOAST = false;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    public static final String GOOGLE_API_KEY = "AIzaSyAOfxiG4aV66h3XmssCEkP3qCvCqMbDGDI";

    @SuppressLint("InlinedApi")
    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

    @SuppressLint("InlinedApi")
    private static final int LANDSCAPE_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

    private YouTubePlayerView mPlayerView;
    private String mVideoId = null;
    private YouTubePlayer mPlayer = null;
    private boolean mAutoRotation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPlayerView = new YouTubePlayerView(this);
        mPlayerView.initialize(GOOGLE_API_KEY, this);
        mVideoId = getIntent().getStringExtra(EXTRA_VIDEO_ID);

        mAutoRotation = Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1;

        addContentView(mPlayerView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
            boolean wasRestored) {
        mPlayer = player;
        player.setPlayerStateChangeListener(this);
        player.setOnFullscreenListener(this);
        
        if (mAutoRotation) {
            player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                    | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                    | YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE
                    | YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        } else {
            player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                    | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                    | YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        }
        
        if (!wasRestored)
            player.loadVideo(mVideoId);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
            YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(GOOGLE_API_KEY, this);
        }
    }

    public YouTubePlayer.Provider getYouTubePlayerProvider() {
        return mPlayerView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (mPlayer != null)
                mPlayer.setFullscreen(true);
            if (TOAST)
                Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (mPlayer != null)
                mPlayer.setFullscreen(false);
            if (TOAST)
                Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        } else {
            if (TOAST)
                Toast.makeText(this, "configuration changed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFullscreen(boolean fullsize) {
        if (TOAST)
            Toast.makeText(this, "full size change : " + fullsize, Toast.LENGTH_SHORT).show();
        if (fullsize) {
            setRequestedOrientation(LANDSCAPE_ORIENTATION);
        } else {
            setRequestedOrientation(PORTRAIT_ORIENTATION);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            AudioManager audioManager = (AudioManager) getBaseContext().getSystemService(
                    Context.AUDIO_SERVICE);
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_RAISE,
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE | AudioManager.FLAG_SHOW_UI);
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            AudioManager audioManager = (AudioManager) getBaseContext().getSystemService(
                    Context.AUDIO_SERVICE);
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_LOWER,
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE | AudioManager.FLAG_SHOW_UI);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public static String getYouTubeVideoId(String video_url) {

        if (video_url != null && video_url.length() > 0) {

            Uri video_uri = Uri.parse(video_url);
            String video_id = video_uri.getQueryParameter("v");

            if (video_id == null)
                video_id = parseYoutubeVideoId(video_url);

            return video_id;
        }
        return null;
    }

    public static String parseYoutubeVideoId(String youtubeUrl)
    {
        String video_id = null;
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 &&
                youtubeUrl.startsWith("http"))
        {
            // ^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/
            String expression = "^.*((youtu.be" + "\\/)"
                    + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
            CharSequence input = youtubeUrl;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches())
            {
                // Regular expression some how doesn't work with id with "v" at
                // prefix
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11)
                    video_id = groupIndex1;
                else if (groupIndex1 != null && groupIndex1.length() == 10)
                    video_id = "v" + groupIndex1;
            }
        }
        return video_id;
    }

    @Override
    public void onError(ErrorReason reason) {
        if (YouTubePlayer.ErrorReason.BLOCKED_FOR_APP.equals(reason)
                || YouTubePlayer.ErrorReason.EMBEDDING_DISABLED.equals(reason)) {
            Uri video_uri = Uri.parse("http://www.youtube.com/watch?v=" + mVideoId);
            startVideo(this, video_uri);
        }
    }

    private void startVideo(Activity act, Uri video_url) {
        String video_id = video_url.getQueryParameter("v");
        Intent intent;

        if (video_id != null) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video_id));
            List<ResolveInfo> list = act.getPackageManager().queryIntentActivities(
                    intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            
            if (list.size() == 0)
                intent = new Intent(Intent.ACTION_VIEW, video_url);
        } else {
            intent = new Intent(Intent.ACTION_VIEW, video_url);
        }

        act.startActivity(intent);
    }

    @Override
    public void onAdStarted() {
    }

    @Override
    public void onLoaded(String videoId) {
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onVideoEnded() {
    }

    @Override
    public void onVideoStarted() {
    }

}
