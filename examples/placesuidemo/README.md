## React Native Google Places UI Demo

Sample App for React Native Google Places UI.

This is using React 0.55.4 with targetSdkVersion 27, Typescript, Gradle 4.4, and Google Play Services 15.0.1

* Prepare your local copy
    ```bash
    $ git clone https://github.com/aMarCruz/react-native-google-places-ui.git
    $ cd react-native-google-places-ui/examples/placesuidemo
    $ yarn
    $ node rn-rename "My App" com.mypackageid
    ```

    The last command changes the display name of package id of the App.
    You should use other values for "My App" and "com.mypackageid".

* Go to [Google API Console](https://console.developers.google.com) and...
  - Create a new project
  - In the section "Libreary" add the "Google Places API for Android"
  - In the section "Credentials" click "Create credentials"
  - Generate an API Key for "com.mypackageid" or wherever name you used.

    This is a partial view of the configuration

    ![API Key](images/getapikey.png)

* In android/gradle.properties replace the string YOUR_API_KEY_HERE with your API key
* Execute `react-native run-android`
