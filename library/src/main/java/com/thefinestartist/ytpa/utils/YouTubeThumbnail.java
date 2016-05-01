package com.thefinestartist.ytpa.utils;

import android.support.annotation.NonNull;

import com.thefinestartist.ytpa.enums.Quality;

/**
 * Created by TheFinestArtist on 6/27/15.
 */
public class YouTubeThumbnail {

    public static final String IMG_YOUTUBE_COM_VI = "http://img.youtube.com/vi/";

    private YouTubeThumbnail() {}

//    Each YouTube video has 4 generated images. They are predictably formatted as follows:
//
//    http://img.youtube.com/vi/<insert-youtube-video-id-here>/0.jpg
//    http://img.youtube.com/vi/<insert-youtube-video-id-here>/1.jpg
//    http://img.youtube.com/vi/<insert-youtube-video-id-here>/2.jpg
//    http://img.youtube.com/vi/<insert-youtube-video-id-here>/3.jpg
//
//    The first one in the list is a full size image and others are thumbnail images. The default thumbnail image (ie. one of `1.jpg`, `2.jpg`, `3.jpg`) is:
//
//    http://img.youtube.com/vi/<insert-youtube-video-id-here>/default.jpg
//
//    For the high quality version of the thumbnail use a url similar to this:
//
//    http://img.youtube.com/vi/<insert-youtube-video-id-here>/hqdefault.jpg
//
//    There is also a medium quality version of the thumbnail, using a url similar to the HQ:
//
//    http://img.youtube.com/vi/<insert-youtube-video-id-here>/mqdefault.jpg
//
//    For the standard definition version of the thumbnail, use a url similar to this:
//
//    http://img.youtube.com/vi/<insert-youtube-video-id-here>/sddefault.jpg
//
//    For the maximum resolution version of the thumbnail use a url similar to this:
//
//    http://img.youtube.com/vi/<insert-youtube-video-id-here>/maxresdefault.jpg
//
//    All of the above urls are available over https too. Just change `http` to `https` in any of the above urls. Additionally, the slightly shorter hostname `i3.ytimg.com` works in place of `img.youtube.com` in the example urls above.
//
//            Alternatively, you can use the [YouTube Data API (v3)][1] or the older [YouTube API v2.0][2] to get thumbnail images.
//
//
//            [1]: https://developers.google.com/youtube/v3/
//            [2]: http://code.google.com/apis/youtube/2.0/developers_guide_php.html

    public static String getUrlFromVideoId(@NonNull String videoId, @NonNull Quality quality) {
        switch (quality) {
            case FIRST:
                return IMG_YOUTUBE_COM_VI + videoId + "/0.jpg";
            case SECOND:
                return IMG_YOUTUBE_COM_VI + videoId + "/1.jpg";
            case THIRD:
                return IMG_YOUTUBE_COM_VI + videoId + "/2.jpg";
            case FOURTH:
                return IMG_YOUTUBE_COM_VI + videoId + "/3.jpg";
            case MAXIMUM:
                return IMG_YOUTUBE_COM_VI + videoId + "/maxresdefault.jpg";
            case STANDARD_DEFINITION:
                return IMG_YOUTUBE_COM_VI + videoId + "/sddefault.jpg";
            case MEDIUM:
                return IMG_YOUTUBE_COM_VI + videoId + "/mqdefault.jpg";
            case HIGH:
                return IMG_YOUTUBE_COM_VI + videoId + "/hqdefault.jpg";
            case DEFAULT:
            default:
                return IMG_YOUTUBE_COM_VI + videoId + "/default.jpg";
        }
    }
}
