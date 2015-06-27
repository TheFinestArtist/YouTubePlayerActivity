package com.thefinestartist.ytpa.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by TheFinestArtist on 2/16/15.
 */
public class YouTubeApp {

    public static void startVideo(@NonNull Context context, @NonNull String videoId) {
        Uri video_uri = Uri.parse(YouTubeUrlParser.getVideoUrl(videoId));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        if (list.size() == 0)
            intent = new Intent(Intent.ACTION_VIEW, video_uri);

        context.startActivity(intent);
    }
}
