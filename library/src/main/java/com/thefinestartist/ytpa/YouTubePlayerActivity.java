package com.thefinestartist.ytpa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
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
import com.thefinestartist.ytpa.enums.Orientation;
import com.thefinestartist.ytpa.enums.PlayerStyle;
import com.thefinestartist.ytpa.utils.AudioUtil;
import com.thefinestartist.ytpa.utils.StatusBarUtil;
import com.thefinestartist.ytpa.utils.YouTubeUrlParser;
import com.thefinestartist.ytpa.utils.YoutubeUtil;

public class YouTubePlayerActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener,
        YouTubePlayer.OnFullscreenListener,
        YouTubePlayer.PlayerStateChangeListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    public static final String META_DATA_NAME = "com.thefinestartist.ytpa.YouTubePlayerActivity.ApiKey";

    public static final String EXTRA_VIDEO_ID = "video_id";
    public static final String EXTRA_VIDEO_URL = "video_url";

    public static final String EXTRA_PLAYER_STYLE = "player_style";

    public static final String EXTRA_ORIENTATION = "orientation";

    public static final String EXTRA_SHOW_AUDIO_UI = "show_audio_ui";

    public static final String EXTRA_HANDLE_ERROR = "handle_error";

    public static final String EXTRA_ANIM_ENTER = "anim_enter";
    public static final String EXTRA_ANIM_EXIT = "anim_exit";

    private String googleApiKey;
    private String videoId;

    private PlayerStyle playerStyle;
    private Orientation orientation;
    private boolean showAudioUi;
    private boolean handleError;
    private int animEnter;
    private int animExit;

    private YouTubePlayerView playerView;
    private YouTubePlayer player;
    private boolean systemAutoRotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

        playerView = new YouTubePlayerView(this);
        playerView.initialize(googleApiKey, this);

        systemAutoRotation = Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1;

        addContentView(playerView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        playerView.setBackgroundResource(android.R.color.black);

        StatusBarUtil.hide(this);
    }

    private void initialize() {
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            googleApiKey = bundle.getString(META_DATA_NAME);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (googleApiKey == null)
            throw new NullPointerException("Google API key must not be null");

        String videoUrl = getIntent().getStringExtra(EXTRA_VIDEO_URL);
        if (videoUrl != null)
            videoId = YouTubeUrlParser.getVideoId(videoUrl);

        if (videoId == null)
            videoId = getIntent().getStringExtra(EXTRA_VIDEO_ID);

        if (videoId == null)
            throw new NullPointerException("Video ID must not be null");

        playerStyle = (PlayerStyle) getIntent().getSerializableExtra(EXTRA_PLAYER_STYLE);
        orientation = (Orientation) getIntent().getSerializableExtra(EXTRA_ORIENTATION);
        showAudioUi = getIntent().getBooleanExtra(EXTRA_SHOW_AUDIO_UI, true);
        handleError = getIntent().getBooleanExtra(EXTRA_HANDLE_ERROR, true);
        animEnter = getIntent().getIntExtra(EXTRA_ANIM_ENTER, 0);
        animExit = getIntent().getIntExtra(EXTRA_ANIM_EXIT, 0);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player,
                                        boolean wasRestored) {
        this.player = player;
        player.setOnFullscreenListener(this);
        player.setPlayerStateChangeListener(this);

//        switch (screenOrientation) {
//            case AUTO:
//                break;
//            case AUTO_START_WITH_LANDSCAPE:
//                setRequestedOrientation(LANDSCAPE_ORIENTATION);
//                break;
//            case AUTO_START_WITH_PORTRAIT:
//                setRequestedOrientation(PORTRAIT_ORIENTATION);
//                break;
//        }

        if (systemAutoRotation) {
            player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                    | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                    | YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE
                    | YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        } else {
            player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                    | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                    | YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        }

        switch (playerStyle) {
            case CHROMELESS:
                player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                break;
            case MINIMAL:
                player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                break;
            case DEFAULT:
            default:
                player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                break;
        }

        if (!wasRestored)
            player.loadVideo(videoId);
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
            playerView.initialize(googleApiKey, this);
        }
    }

    // YouTubePlayer.OnFullscreenListener
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (player != null)
                player.setFullscreen(true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (player != null)
                player.setFullscreen(false);
        }
    }

    @SuppressLint("InlinedApi")
    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

    @SuppressLint("InlinedApi")
    private static final int LANDSCAPE_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

    @Override
    public void onFullscreen(boolean fullScreen) {
        if (fullScreen)
            setRequestedOrientation(LANDSCAPE_ORIENTATION);
        else
            setRequestedOrientation(PORTRAIT_ORIENTATION);
    }

    // YouTubePlayer.PlayerStateChangeListener
    @Override
    public void onError(ErrorReason reason) {
        if (handleError && ErrorReason.NOT_PLAYABLE.equals(reason))
            YoutubeUtil.startVideo(this, videoId);
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

    // Audio Managing
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            AudioUtil.adjustMusicVolume(getApplicationContext(), true, showAudioUi);
            StatusBarUtil.hide(this);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            AudioUtil.adjustMusicVolume(getApplicationContext(), false, showAudioUi);
            StatusBarUtil.hide(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // Animation
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (animEnter != 0 && animExit != 0)
            overridePendingTransition(animEnter, animExit);
    }
}
