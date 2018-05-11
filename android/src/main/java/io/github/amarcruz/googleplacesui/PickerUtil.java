package io.github.amarcruz.googleplacesui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

class PickerUtil {
    private static final String E_CALLBACK_ERROR = "E_CALLBACK_ERROR";
    private static final String E_PERMISSIONS_MISSING = "E_PERMISSIONS_MISSING";

    private PickerUtil() {
        throw new AssertionError();
    }

    static WritableMap makePlaceMap(final Place place) {
        final CharSequence address = place.getAddress();
        final CharSequence attributions = place.getAttributions();
        final CharSequence phoneNumber = place.getPhoneNumber();
        final WritableMap viewport = getViewport(place.getViewport());
        final Locale locale = place.getLocale();
        final List<Integer> types = place.getPlaceTypes();
        final Uri website = place.getWebsiteUri();

        final WritableMap result = Arguments.createMap();

        result.putString("id", place.getId());
        result.putString("address", address == null ? "" : address.toString());
        result.putString("attributions", attributions == null ? "" : attributions.toString());
        result.putMap("latlng", PickerUtil.fromLatLng(place.getLatLng()));
        result.putString("name", place.getName().toString());
        result.putArray("placeTypes", getPlaceTypes(types));
        result.putInt("priceLevel", place.getPriceLevel());
        result.putDouble("rating", place.getRating());
        if (locale != null) {
            result.putString("locale", locale.toString());
        }
        if (phoneNumber != null) {
            result.putString("phoneNumber", phoneNumber.toString());
        }
        if (viewport != null) {
            result.putMap("viewport", viewport);
        }
        if (website != null) {
            result.putString("website", website.toString());
        }

        return result;
    }

    @NonNull
    static WritableMap fromLatLng(@NonNull final LatLng src) {
        final WritableMap loc = Arguments.createMap();

        loc.putDouble("latitude", src.latitude);
        loc.putDouble("longitude", src.longitude);

        return loc;
    }

    static LatLngBounds getBoundsFromArray(@NonNull final ReadableArray src) {
        final LatLngBounds.Builder builder = LatLngBounds.builder();
        final int len = src.size();
        int points = 0;

        for (int i = 0; i < len; i++) {
            if (src.getType(i) == ReadableType.Map) {
                builder.include(toLatLng(src.getMap(i)));
                points++;
            }
        }
        return points > 0 ? builder.build() : null;
    }

    static LatLngBounds getBounds(@NonNull final ReadableMap src) throws Exception {
        final String prop = "bounds";

        if (src.hasKey(prop)) {
            final ReadableType type = src.getType(prop);

            if (type == ReadableType.Array) {
                return getBoundsFromArray(src.getArray(prop));
            }
            if (type == ReadableType.Map) {
                return getLatLngBounds(src.getMap(prop));
            }
            if (type != ReadableType.Null) {
                throw new Exception("'bounds' must be LatLng[] or LatLngBounds.");
            }
        }

        return null;
    }

    static boolean hasPermissions(@NonNull final Activity activity) {
        return ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    static void withPermissions(
            @NonNull final Activity activity,
            @NonNull final PickerResolver resolver,
            @NonNull final Callable<Void> callback)
    {
        final String[] required = {Manifest.permission.ACCESS_FINE_LOCATION};

        ((PermissionAwareActivity) activity).requestPermissions(required, 1, new PermissionListener() {
            @Override
            public boolean onRequestPermissionsResult(final int requestCode,
                    @NonNull final String[] permissions, @NonNull final int[] result) {

                if (result.length > 0 && result[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        callback.call();
                    } catch (Exception ex) {
                        resolver.error(E_CALLBACK_ERROR, ex);
                    }
                } else {
                    resolver.error(E_PERMISSIONS_MISSING,  new Exception("Required permission missing"));
                }
                return true;
            }
        });
    }

    static WritableMap getViewport(final LatLngBounds viewPort) {
        if (viewPort != null) {
            final WritableMap map = Arguments.createMap();

            map.putMap("northeast", fromLatLng(viewPort.northeast));
            map.putMap("southwest", fromLatLng(viewPort.southwest));

            return map;
        }
        return null;
    }

    private static WritableArray getPlaceTypes(final List<Integer> types) {
        final WritableArray placeTypes = Arguments.createArray();

        if (types != null) {
            for (int type : types) {
                placeTypes.pushString(PlaceTypes.getPlaceTypeById(type));
            }
        }
        return placeTypes;
    }

    private static LatLngBounds getLatLngBounds(@NonNull final ReadableMap src) {
        return new LatLngBounds(
                toLatLng(src.getMap("southwest")),
                toLatLng(src.getMap("northeast"))
        );
    }

    private static LatLng toLatLng(final ReadableMap map) {
        if (map != null &&
                map.hasKey("latitude") && map.hasKey("longitude")) {
            return new LatLng(
                    map.getDouble("latitude"),
                    map.getDouble("longitude")
            );
        }
        return null;
    }
}
