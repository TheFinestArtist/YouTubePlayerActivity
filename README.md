# YouTube Player Activity [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MovingButton-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1542) [![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat)](http://opensource.org/licenses/MIT)

Simply pass a url to play youtube video on new activity. It supports screen orientation, media volume control and etc.


#### Features
* Orientation Support (with Auto Rotation mode)

    Auto-Rotation ON : You can either use sensor or YouTube full screen button.

    Auto-Rotation OFF : You can just use YouTube full screen button.


* Media Volume Support

    While watching YouTube Player, users should be able to set media volume!!!

* Video Play Error Support

    If the video is not playable, it send to youtube app or other browser which will might play it.

* Animation Support

    Activity closing animation can be customized.


#### Set Up AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.INTERNET" />

<activity
    android:name="com.thefinestartist.ytpa.YouTubePlayerActivity"
    android:configChanges="keyboardHidden|orientation|screenSize"
    android:screenOrientation="sensor"
    android:theme="@android:style/Theme.Black.NoTitleBar.Fullscreen" />

<meta-data
    android:name="com.thefinestartist.ytpa.YouTubePlayerActivity.ApiKey"
    android:value="your_google_api_key" />
```

#### Usage
```java
Intent intent = new Intent(MainActivity.this, YouTubePlayerActivity.class);

// Youtube video ID or Url (Required)
intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, "iS1g8G_njx8");
// These kind of url can be parsed!!
// https://youtu.be/iS1g8G_njx8
// https://www.youtube.com/watch?v=iS1g8G_njx8
// https://www.youtube.com/watch?v=iS1g8G_njx8&vq=hd1080
intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_URL, "https://youtu.be/iS1g8G_njx8");

// Show audio interface when user adjust volume
// true for default
intent.putExtra(YouTubePlayerActivity.EXTRA_SHOW_AUDIO_UI, true);

// If the video is not playable, use Youtube app or Internet Browser to play it
// true for default
intent.putExtra(YouTubePlayerActivity.EXTRA_HANDLE_ERROR, true);

// Animation when closing youtubeplayeractivity (none for default)
intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_ENTER, R.anim.fade_in);
intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_EXIT, R.anim.fade_out);

intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(intent);
```

## YoutubeUrlParser

This util helps to retrieve youtube video id from youtube url or vice versa. [Reference](https://androidsnippets.wordpress.com/2012/10/11/how-to-get-extract-video-id-from-an-youtube-url-in-android-java)

```java
String vidoeId = YoutubeUrlParser.getVideoId(videoUrl);
String vidoeUrl = YoutubeUrlParser.getVideoId(videoId);
```


## License

```
The MIT License (MIT)

Copyright (c) 2015 TheFinestArtist

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
```