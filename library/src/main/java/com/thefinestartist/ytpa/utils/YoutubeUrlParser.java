package com.thefinestartist.ytpa.utils;

import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by TheFinestArtist on 2/15/15.
 */
public class YouTubeUrlParser {

    // ^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/
    final static String reg = "^.*((youtu.be\\/)|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";

    public static String parseVideoId(String url) {
        String videoDd = null;
        if (url != null && url.trim().length() > 0 && url.startsWith("http")) {
            Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(url);

            // Regular reg some how doesn't work with id with "v" at prefix
            if (matcher.matches()) {
                String groupIndex = matcher.group(7);
                if (groupIndex != null && groupIndex.length() == 11)
                    videoDd = groupIndex;
                else if (groupIndex != null && groupIndex.length() == 10)
                    videoDd = "v" + groupIndex;
            }
        }
        return videoDd;
    }

    public static String getVideoId(String url) {
        if (url == null)
            return null;

        Uri uri = Uri.parse(url);
        String videoId = uri.getQueryParameter("v");

        if (videoId == null)
            videoId = parseVideoId(url);

        return videoId;
    }
}
