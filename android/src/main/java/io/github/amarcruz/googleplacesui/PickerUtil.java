package io.github.amarcruz.googleplacesui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.PermissionAwareActivity;

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

    static WritableMap fromLatLng(final LatLng src) {
        final WritableMap loc = Arguments.createMap();

        loc.putDouble("latitude", src != null ? src.latitude : 0);
        loc.putDouble("longitude", src != null ? src.longitude : 0);

        return loc;
    }

    static LatLngBounds getBoundsFromArray(final ReadableArray src) {
        final LatLngBounds.Builder builder = LatLngBounds.builder();
        final int len = src.size();
        int points = 0;

        for (int i = 0; i < len; i++) {
            if (src.getType(i) == ReadableType.Map) {
                final LatLng latLng = toLatLng(src.getMap(i));

                if (latLng != null) {
                    builder.include(latLng);
                }
                points++;
            }
        }
        return points > 0 ? builder.build() : null;
    }

    static LatLngBounds getBounds(final ReadableMap src) throws Exception {
        final String prop = "bounds";

        if (src.hasKey(prop)) {
            final ReadableType type = src.getType(prop);

            if (type == ReadableType.Array) {
                final ReadableArray arr = src.getArray(prop);
                if (arr != null) {
                    return getBoundsFromArray(arr);
                }
            }
            if (type == ReadableType.Map) {
                final ReadableMap map = src.getMap(prop);
                if (map != null) {
                    return getLatLngBounds(map);
                }
            }
            if (type != ReadableType.Null) {
                throw new Exception("'bounds' must be LatLng[] or LatLngBounds.");
            }
        }

        return null;
    }

    static boolean hasPermissions(final Activity activity) {
        return ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    static void withPermissions(
            final Activity activity,
            final PickerResolver resolver,
            final Callable<Void> callback)
    {
        final String[] required = {Manifest.permission.ACCESS_FINE_LOCATION};

        ((PermissionAwareActivity) activity).requestPermissions(required, 1, (requestCode, permissions, result) -> {

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

    private static LatLngBounds getLatLngBounds(final ReadableMap src) {
        final ReadableMap southwest = src.getMap("southwest");
        final ReadableMap northeast = src.getMap("northeast");

        if (southwest == null || northeast == null) {
            return null;
        }
        return new LatLngBounds(
                toLatLng(southwest),
                toLatLng(northeast)
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
