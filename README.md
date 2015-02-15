# Moving Button

Simply pass a url to play youtube video on new activity. It supports screen orientation, media volume control and etc.

```xml
<uses-permission android:name="android.permission.INTERNET" />
<activity
    android:name="com.thefinestartist.ytpa.YouTubePlayerActivity"
    android:configChanges="keyboardHidden|orientation|screenSize"
    android:screenOrientation="sensor"
    android:theme="@android:style/Theme.Black.NoTitleBar.Fullscreen" >
</activity>
```

## YoutubeUrlParser

https://androidsnippets.wordpress.com/2012/10/11/how-to-get-extract-video-id-from-an-youtube-url-in-android-java/


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