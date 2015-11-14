# YouTube Player Activity
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-YouTubePlayerActivity-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1542)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-7%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=7)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat)](http://opensource.org/licenses/MIT)

Simply pass an url to play youtube video in new activity. It supports screen orientation, media volume control and etc.

## Preview

![Preview](https://github.com/thefinestartist/YouTubePlayerActivity/blob/master/art/preview.gif)

## Sample Demo

You can download demo movie file here : [demo.mov](https://github.com/thefinestartist/YouTubePlayerActivity/raw/master/art/demo.mov)

It's also on Youtube:

<a href="https://www.youtube.com/watch?v=5U-Yu_OZHes">
  <img alt="Youtube"
       src="https://github.com/thefinestartist/YouTubePlayerActivity/blob/master/art/youtube.png" />
</a>

## Sample Project

You can download the latest sample APK from this repo here: [sample-release.apk](https://github.com/thefinestartist/YouTubePlayerActivity/raw/master/sample/sample-release.apk)

It's also on Google Play:

<a href="https://play.google.com/store/apps/details?id=com.thefinestartist.ytpa.sample">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

Having the sample project installed is a good way to be notified of new releases.

## Gradle Dependency (jcenter)

Easily reference the library in your Android projects using this dependency in your module's `build.gradle` file:

```Gradle
dependencies {
    compile 'com.thefinestartist:ytpa:1.2.1'
}
```

## Requirements

It supports Android API 7+.


## Features
* Orientation Support
    * AUTO
    * AUTO_START_WITH_LANDSCAPE
    * ONLY_LANDSCAPE
    * ONLY_PORTRAIT

* Media Volume Support
    * While watching YouTube Player, users should be able to set media volume!!!

* Video Play Error Support
    * If the video is not playable, it send to youtube app or other browser which will might play it.

* Animation Support
    * Activity closing animation can be customized.

* Status Bar Support
    * On screen portrait mode, it removed status bar automatically.


## Set Up AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.INTERNET" />

<activity
    android:name="com.thefinestartist.ytpa.YouTubePlayerActivity"
    android:configChanges="keyboardHidden|orientation|screenSize"
    android:screenOrientation="sensor"
    android:theme="@android:style/Theme.NoTitleBar.Fullscreen" />

<!--Need Your Google API Key-->
<meta-data
    android:name="com.thefinestartist.ytpa.YouTubePlayerActivity.ApiKey"
    android:value="your_google_api_key" />
```

## Usage
```java
Intent intent = new Intent(MainActivity.this, YouTubePlayerActivity.class);

// Youtube video ID (Required, You can use YouTubeUrlParser to parse Video Id from url)
intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, "iS1g8G_njx8");

// Youtube player style (DEFAULT as default)
intent.putExtra(YouTubePlayerActivity.EXTRA_PLAYER_STYLE, YouTubePlayer.PlayerStyle.DEFAULT);

// Screen Orientation Setting (AUTO for default)
// AUTO, AUTO_START_WITH_LANDSCAPE, ONLY_LANDSCAPE, ONLY_PORTRAIT
intent.putExtra(YouTubePlayerActivity.EXTRA_ORIENTATION, Orientation.AUTO);

// Show audio interface when user adjust volume (true for default)
intent.putExtra(YouTubePlayerActivity.EXTRA_SHOW_AUDIO_UI, true);

// If the video is not playable, use Youtube app or Internet Browser to play it
// (true for default)
intent.putExtra(YouTubePlayerActivity.EXTRA_HANDLE_ERROR, true);

// Animation when closing youtubeplayeractivity (none for default)
intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_ENTER, R.anim.fade_in);
intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_EXIT, R.anim.fade_out);

intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
startActivity(intent);
```

## YouTubeUrlParser

This util helps to retrieve youtube video id from youtube url or vice versa. [Reference](https://gist.github.com/afeld/1254889)

```java
String vidoeId = YouTubeUrlParser.getVideoId(videoUrl);
String vidoeUrl = YouTubeUrlParser.getVideoUrl(videoId);
```

## YouTubeApp

This util helps to open Youtube App and play specific video.

```java
YouTubeApp.startVideo(context, videoId);
```

## YouTubeThumbnail

This util returns Youtube thumbnail image url.

```java
YouTubeThumbnail.getUrlFromVideoId(videoId, Quality.HIGH);
```


## License

```
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
```
