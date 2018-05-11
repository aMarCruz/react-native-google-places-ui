package io.github.amarcruz.googleplacesui;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLngBounds;

import io.github.amarcruz.yalog.Log;

import java.util.concurrent.Callable;

class RNPlaceAutocomplete extends BaseActivityEventListener {
    private static final int PLACE_REQUEST = Constants.PLACE_AUTOCOMPLETE_RC;

    private PickerResolver mResolver;

    RNPlaceAutocomplete(ReactApplicationContext reactContext) {
        reactContext.addActivityEventListener(this);
    }

    public void pick(final Activity activity, final ReadableMap opts, final Promise promise) {

        mResolver = new PickerResolver(promise);

        final PlaceAutocomplete.IntentBuilder builder = getBuilder(opts);
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
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
            mResolver.error(Constants.E_GOOGLE_PS_NOT_AVAILABLE, e);

        } catch (Exception e) {
            mResolver.error(Constants.E_CANNOT_LAUNCH_PICKER, e);
        }
    }

    @Override
    public void onActivityResult(final Activity activity, final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == PLACE_REQUEST) {
            Log.v("Places", "[PlaceAutocomplete.handleResult]");
            switch (resultCode) {
                case Activity.RESULT_OK:
                    final Place place = data == null ? null : PlacePicker.getPlace(activity, data);
                    mResolver.success(place);                break;
                case PlaceAutocomplete.RESULT_ERROR:
                    final Status status = data == null ? null : PlaceAutocomplete.getStatus(activity, data);
                    mResolver.error(status);
                    break;
                default:
                    mResolver.cancel();
                    break;
            }
        }
    }

    @Override
    public void onNewIntent(Intent data) {
    }

    private PlaceAutocomplete.IntentBuilder getBuilder(final ReadableMap opts) {
        try {
            int mode = PlaceAutocomplete.MODE_FULLSCREEN;
            LatLngBounds bounds = null;
            AutocompleteFilter.Builder filter = null;

            if (opts != null) {
                bounds = PickerUtil.getBounds(opts);

                if (opts.hasKey("mode")) {
                    mode = getPickerMode(opts);
                }

                if (opts.hasKey("filter") && opts.getType("filter") == ReadableType.Map) {
                    final ReadableMap map = opts.getMap("filter");
                    AutocompleteFilter.Builder _filter = new AutocompleteFilter.Builder();

                    if (map.hasKey("country")) {
                        if (map.getType("country") == ReadableType.String) {
                            filter = _filter;
                            filter.setCountry(map.getString("country"));
                        } else if (map.getType("country") != ReadableType.Null) {
                            throw new Exception("Type mismatch in `country`");
                        }
                    }
                    if (map.hasKey("type")) {
                        filter = _filter;
                        filter.setTypeFilter(getFilterType(map));
                    }
                }
            }

            final PlaceAutocomplete.IntentBuilder builder = new PlaceAutocomplete.IntentBuilder(mode);
            if (bounds != null) {
                builder.setBoundsBias(bounds);
            }
            if (filter != null) {
                builder.setFilter(filter.build());
            }
            return builder;

        } catch (Exception e) {
            mResolver.error(Constants.E_INVALID_PARAM, e);
        }
        return null;
    }

    private int getPickerMode(@NonNull final ReadableMap map) throws Exception {
        final ReadableType type = map.getType("mode");

        if (type == ReadableType.String) {
            final String modeStr = map.getString("mode");

            switch (modeStr.toLowerCase()) {
                case "fullscreen":
                    return PlaceAutocomplete.MODE_FULLSCREEN;
                case "overlay":
                    return PlaceAutocomplete.MODE_OVERLAY;
            }
            throw new Exception("Unknown mode '" + modeStr + "'");
        }

        if (type == null) {
            return PlaceAutocomplete.MODE_FULLSCREEN;
        }

        throw new Exception("Type mismatch on `mode`");
    }

    private int getFilterType(final ReadableMap map) throws Exception {
        final ReadableType type = map.getType("type");

        if (type == ReadableType.String) {
            final String typeStr = map.getString("type");

            switch (typeStr.toLowerCase()) {
                case "address":
                    return AutocompleteFilter.TYPE_FILTER_ADDRESS;
                case "cities":
                    return AutocompleteFilter.TYPE_FILTER_CITIES;
                case "establishment":
                    return AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT;
                case "geocode":
                    return AutocompleteFilter.TYPE_FILTER_GEOCODE;
                case "regions":
                    return AutocompleteFilter.TYPE_FILTER_REGIONS;
                case "":
                    return AutocompleteFilter.TYPE_FILTER_NONE;
            }
            throw new Exception("Unknown filter type '" + typeStr + "'");
        }

        if (type == ReadableType.Null) {
            return AutocompleteFilter.TYPE_FILTER_NONE;
        }

        throw new Exception("Type mismatch on `filter`.");
    }

}
