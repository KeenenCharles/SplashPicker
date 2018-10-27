# SplashPicker
An image picker that loads images from Unsplash

## Usage

### Open Image Picker
~~~~~
SplashPicker.open(this, YOUR_CLIENT_ID)
~~~~~
You can sign up for a Client Id at <https://unsplash.com/developers>

### Receive the Selected Photo
~~~~~
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if(requestCode == SplashPicker.PICK_IMAGE_REQUEST) {
        if(resultCode == Activity.RESULT_OK) {
            Photo photo  = data.getParcelableExtra(SplashPicker.KEY_IMAGE);
        }
    }
}
~~~~~

## Gradle
Add the line below to your build.gradle to use:
~~~
dependencies {
    implementation 'com.kc.splashpicker:splashpicker:1.0.0'
    implementation 'com.kc.androidunsplash:androidunsplash:0.2.1'
}
~~~

# License
~~~
MIT License

Copyright (c) 2018 Keenen Charles

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

~~~
