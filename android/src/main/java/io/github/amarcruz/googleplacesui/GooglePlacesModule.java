package io.github.amarcruz.googleplacesui;

import android.app.Activity;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import com.google.android.gms.maps.model.LatLngBounds;

import io.github.amarcruz.yalog.Log;

@SuppressWarnings("unused")
class GooglePlacesModule extends ReactContextBaseJavaModule {
    private static final String TAG = Constants.TAG;

    private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
    private static final String E_INVALID_PARAM = "E_INVALID_PARAM";

    private final RNPlaceAutocomplete mPlaceAutocomplete;
    private final RNPlacePicker mPlacePicker;

    GooglePlacesModule(ReactApplicationContext reactContext) {
        super(reactContext);

        Log.setLevel(BuildConfig.DEBUG ? Log.ALL : Log.WARN);

        mPlaceAutocomplete = new RNPlaceAutocomplete(reactContext);
        mPlacePicker = new RNPlacePicker(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void placeAutocomplete(final ReadableMap options, final Promise promise) {
        final Activity activity = getCurrentActivity();
        if (activity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist.");
            return;
        }

        mPlaceAutocomplete.pick(activity, options, promise);
    }

    @ReactMethod
    public void placePicker(final ReadableMap options, final Promise promise) {
        final Activity activity = getCurrentActivity();
        if (activity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist.");
            return;
        }

        mPlacePicker.pick(activity, options, promise);
    }

    @ReactMethod
    public void getLastBounds(final Promise promise) {
        final LatLngBounds bounds = mPlacePicker.getLastBounds();

        promise.resolve(PickerUtil.getViewport(bounds));
    }

    @ReactMethod
    public void buildBounds(final ReadableArray src, final Promise promise) {
        try {
            WritableMap result = null;

            if (src != null) {
                final LatLngBounds bounds = PickerUtil.getBoundsFromArray(src);

                if (bounds != null) {
                    result = Arguments.createMap();
                    result.putMap("northeast", PickerUtil.fromLatLng(bounds.northeast));
                    result.putMap("southwest", PickerUtil.fromLatLng(bounds.southwest));
                }
            }
            promise.resolve(result);
        } catch (Exception e) {
            promise.reject(E_INVALID_PARAM, "Cannot make bounds: " + e.getMessage());
        }
    }

    @ReactMethod
    public void setLogLevel(final int level) {
        Log.setLevel(level);
    }
}
