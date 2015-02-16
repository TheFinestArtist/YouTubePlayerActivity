package com.thefinestartist.ytpa.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.crashlytics.android.Crashlytics;
import com.thefinestartist.ytpa.YouTubePlayerActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.play_bt)
    ImageButton play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, YouTubePlayerActivity.class);
                intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_URL, "https://youtu.be/iS1g8G_njx8");
                intent.putExtra(YouTubePlayerActivity.EXTRA_SHOW_AUDIO_UI, true);
                intent.putExtra(YouTubePlayerActivity.EXTRA_HANDLE_ERROR, true);
                intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_ENTER, R.anim.fade_in);
                intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_EXIT, R.anim.fade_out);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
