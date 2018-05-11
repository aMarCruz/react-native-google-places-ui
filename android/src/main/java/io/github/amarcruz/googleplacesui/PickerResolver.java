package io.github.amarcruz.googleplacesui;

import com.facebook.react.bridge.Promise;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacesStatusCodes;

import io.github.amarcruz.yalog.Log;

class PickerResolver {
    static private final String TAG = Constants.TAG;
    static private final String CANCELLED = "RESULT_CANCELED";
    static private final String E_RESULT_ERROR = "E_RESULT_ERROR";

    private Promise mPromise;

    PickerResolver(final Promise promise) {
        mPromise = promise;
    }

    void success(final Place place) {
        if (mPromise != null) {
            try {
                if (place != null) {
                    mPromise.resolve(PickerUtil.makePlaceMap(place));
                } else {
                    cancel();
                }
            } catch (Exception ex) {
                mPromise.reject(E_RESULT_ERROR, ex);
            } finally {
                mPromise = null;
            }
        }
    }

    void error(final String code, final Exception ex) {
        if (mPromise != null) {
            Log.e(TAG, code, ex);
            mPromise.reject(code, ex);
            mPromise = null;
        }
    }

    void error(final Status status) {
        if (mPromise != null) {
            String code = Constants.E_PICKER_ERROR;
            String message = "Picker error.";

            try {
                if (status != null) {
                    code = getStatusCodeName(status.getStatusCode());
                    message = status.getStatusMessage();
                }
            } catch (Exception ex) {
                Log.w("Places", "[RESULT_ERROR]", ex);
            }
            Log.e(TAG, code + ": " + message);
            mPromise.reject(code, message);
        }
    }

    void cancel() {
        if (mPromise != null) {
            mPromise.reject(CANCELLED, "Cancelled.");
            mPromise = null;
        }
    }

    private static String getStatusCodeName(int statusCode) {
        return "E_" + PlacesStatusCodes.getStatusCodeString(statusCode);
    }

}
