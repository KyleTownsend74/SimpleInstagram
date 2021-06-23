# SimpleInstagram

**SimpleInstagram** is a photo sharing app similar to Instagram but using Parse as its backend. Made for the CodePath Android University course (Project 3).

## Features

The following features have been implemented:

- User can sign up to create a new account using Parse authentication.
- User can log in and log out of his or her account.
- The current signed in user is persisted across app restarts.
- User can take a photo, add a caption, and post it to "Instagram".
- User can view the last 20 posts submitted to "Instagram".
- The user can switch between different tabs - home (feed view), compose (capture photos from camera), and profile tabs (posts made) using fragments and a Bottom Navigation View.
- User can pull to refresh the last 20 posts submitted to "Instagram".
- User sees app icon in home screen and styled bottom navigation view
- The username and creation time are shown for each post.
- User can like and unlike each post.
- User can post a comment for each post.

## Video Walkthrough

Here's a walkthrough of some implemented features:

<img src='walkthrough-final.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Open-source libraries used

- [Android Async HTTP](https://github.com/codepath/CPAsyncHttpClient) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright 2021 Kyle Townsend

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
