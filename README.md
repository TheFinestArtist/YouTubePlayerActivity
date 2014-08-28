SimpleYouTubePlayer
===================

Download sample app from https://play.google.com/store/apps/details?id=com.thefinestartist.simpleyoutubeplayer

There is YouTubePlayerActivity which using YouTubePlayerAPI

HOW TO USE
----------------

Put Youtube_api_key in YouTubePlayerActivity

    public static final String GOOGLE_API_KEY = "AIzaSyAOfxiG4aV66h3XmssCEkP3qCvCq******";

GET Youtube Video id from URL
    
    final String videoId = YouTubePlayerActivity.getYouTubeVideoId("http://www.youtube.com/watch?v=9bZkp7q19f0"); 
        
ADD video id as Extra and Start Activity
    
    Intent intent = new Intent(MainActivity.this, YouTubePlayerActivity.class);
    
    intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, videoId);
    
    //This Flag might cause the video to turned off automatically on phonecall
    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); 
    
    startActivity(intent);

WHAT IT DOES
----------------


* Orientation Problem (with Auto Rotation mode)

    Auto-Rotation ON : You can either use sensor or YouTube full screen button.

    Auto-Rotation OFF : You can just use YouTube full screen button.


* Youtube url Parsing Problem

    Method called parseYoutubeVideoId can make YouTube URL to Video ID.
    
    Get some help from http://androidsnippets.wordpress.com/2012/10/11/how-to-get-extract-video-id-from-an-youtube-url-in-android-java/


* Media Volume Problem

    While watching YouTube Player, users should be able to set media volume!!!
    
    
    
Developer
----------------
THE FINEST ARTIST

Facebook : www.TheFinestArtist.com

Email : contact@thefinestartist.com



Screen Shot
----------------
<img src=https://github.com/TheFinestArtist/SimpleYouTubePlayer/blob/master/res/drawable-xhdpi/screenshot_1.png?raw=true width=500px>


<img src=https://github.com/TheFinestArtist/SimpleYouTubePlayer/blob/master/res/drawable-xhdpi/screenshot_2.png?raw=true width=500px>


<img src=https://github.com/TheFinestArtist/SimpleYouTubePlayer/blob/master/res/drawable-xhdpi/screenshot_3.png?raw=true width=500px>

## License

    The MIT License (MIT)

    Copyright (c) 2013 TheFinestArtist

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.

