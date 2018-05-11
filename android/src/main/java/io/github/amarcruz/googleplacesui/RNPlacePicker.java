package io.github.amarcruz.googleplacesui;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLngBounds;

import io.github.amarcruz.yalog.Log;

import java.util.concurrent.Callable;

class RNPlacePicker extends BaseActivityEventListener {

    private static final int PLACE_REQUEST = Constants.PLACE_PICKER_RC;
    private static LatLngBounds lastBounds = null;

    private PickerResolver mResolver;

    RNPlacePicker(ReactApplicationContext context) {
        context.addActivityEventListener(this);
    }

    void pick(final Activity activity, final ReadableMap opts, final Promise promise) {

        mResolver = new PickerResolver(promise);

        final PlacePicker.IntentBuilder builder = getBuilder(opts);
        if (builder == null) {
            return;
        }

        try {
            final Intent intent = builder.build(activity);

            if (PickerUtil.hasPermissions(activity)) {
                activity.startActivityForResult(intent, PLACE_REQUEST);
            } else {
                PickerUtil.withPermissions(activity, mResolver, new Callable<Void>() {
                    @Override
                    public Void call() {
                        activity.startActivityForResult(intent, PLACE_REQUEST);
                        return null;
                    }
                });
            }
        } catch (GooglePlayServicesNotAvailableException |
                GooglePlayServicesRepairableException e) {
            mResolver.error(Constants.E_GOOGLE_PS_NOT_AVAILABLE, e);

        } catch (Exception e) {
            mResolver.error(Constants.E_CANNOT_LAUNCH_PICKER, e);
        }
    }

    LatLngBounds getLastBounds() {
        return lastBounds;
    }

    @Override
    public void onActivityResult(final Activity activity, final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == PLACE_REQUEST) {
            Log.v("Places", "[PlacePicker.handleResult]");
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Place place = PlacePicker.getPlace(activity, data);
                    lastBounds = PlacePicker.getLatLngBounds(data);
                    mResolver.success(place);
                    break;

                case PlacePicker.RESULT_ERROR:
                    Status status = data == null ? null : PlacePicker.getStatus(activity, data);
                    Log.v(Constants.TAG, "getStatus() data is null? " + (data == null));
                    Log.v(Constants.TAG, "getStatus() returns null? " + (status == null));
                    mResolver.error(status);
                    break;

                default:
                    mResolver.cancel();
                    break;
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    private PlacePicker.IntentBuilder getBuilder(final ReadableMap opts) {
        try {
            final PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            if (opts != null) {
                LatLngBounds bounds = null;

                if (opts.hasKey("useLastBounds") && opts.getBoolean("useLastBounds")) {
                    bounds = lastBounds;
                }
                if (bounds == null) {
                    bounds = PickerUtil.getBounds(opts);
                }
                if (bounds != null) {
                    builder.setLatLngBounds(bounds);
                }
            }
            return builder;

        } catch (Exception e) {
            mResolver.error(Constants.E_INVALID_PARAM, e);
        }
        return null;
    }

}
