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

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLngBounds;

import com.github.amarcruz.yalog.Log;

import java.util.HashMap;
import java.util.Map;

class GooglePlacesModule extends ReactContextBaseJavaModule {
    private static final String TAG = Constants.TAG;

    private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
    private static final String E_INVALID_PARAM = "E_INVALID_PARAM";

    private final RNPlaceAutocomplete mPlaceAutocomplete;
    private final RNPlacePicker mPlacePicker;

    GooglePlacesModule(ReactApplicationContext reactContext) {
        super(reactContext);

        Log.setLevel(TAG, BuildConfig.DEBUG ? Log.ALL : Log.WARN);

        mPlaceAutocomplete = new RNPlaceAutocomplete(reactContext);
        mPlacePicker = new RNPlacePicker(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();

        constants.put("PlaceType", getTypeConst());

        return constants;
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void placeAutocomplete(final ReadableMap options, final Promise promise) {
        final Activity activity = getCurrentActivity();
        if (activity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist.");
            return;
        }

        mPlaceAutocomplete.pick(activity, options, promise);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void placePicker(final ReadableMap options, final Promise promise) {
        final Activity activity = getCurrentActivity();
        if (activity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist.");
            return;
        }

        mPlacePicker.pick(activity, options, promise);
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void getLastBounds(final Promise promise) {
        final LatLngBounds bounds = mPlacePicker.getLastBounds();

        promise.resolve(PickerUtil.getViewport(bounds));
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    @ReactMethod
    public void setLogLevel(final int level) {
        Log.setLevel(TAG, level);
    }

    private Map<String, Object> getTypeConst() {
        final Map<String, Object> types = new HashMap<>();

        types.put("ACCOUNTING", Place.TYPE_ACCOUNTING);
        types.put("ADMINISTRATIVE_AREA_LEVEL_1", Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_1);
        types.put("ADMINISTRATIVE_AREA_LEVEL_2", Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_2);
        types.put("ADMINISTRATIVE_AREA_LEVEL_3", Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_3);
        types.put("AIRPORT", Place.TYPE_AIRPORT);
        types.put("AMUSEMENT_PARK", Place.TYPE_AMUSEMENT_PARK);
        types.put("AQUARIUM", Place.TYPE_AQUARIUM);
        types.put("ART_GALLERY", Place.TYPE_ART_GALLERY);
        types.put("ATM", Place.TYPE_ATM);
        types.put("BAKERY", Place.TYPE_BAKERY);
        types.put("BANK", Place.TYPE_BANK);
        types.put("BAR", Place.TYPE_BAR);
        types.put("BEAUTY_SALON", Place.TYPE_BEAUTY_SALON);
        types.put("BICYCLE_STORE", Place.TYPE_BICYCLE_STORE);
        types.put("BOOK_STORE", Place.TYPE_BOOK_STORE);
        types.put("BOWLING_ALLEY", Place.TYPE_BOWLING_ALLEY);
        types.put("BUS_STATION", Place.TYPE_BUS_STATION);
        types.put("CAFE", Place.TYPE_CAFE);
        types.put("CAMPGROUND", Place.TYPE_CAMPGROUND);
        types.put("CAR_DEALER", Place.TYPE_CAR_DEALER);
        types.put("CAR_RENTAL", Place.TYPE_CAR_RENTAL);
        types.put("CAR_REPAIR", Place.TYPE_CAR_REPAIR);
        types.put("CAR_WASH", Place.TYPE_CAR_WASH);
        types.put("CASINO", Place.TYPE_CASINO);
        types.put("CEMETERY", Place.TYPE_CEMETERY);
        types.put("CHURCH", Place.TYPE_CHURCH);
        types.put("CITY_HALL", Place.TYPE_CITY_HALL);
        types.put("CLOTHING_STORE", Place.TYPE_CLOTHING_STORE);
        types.put("COLLOQUIAL_AREA", Place.TYPE_COLLOQUIAL_AREA);
        types.put("CONVENIENCE_STORE", Place.TYPE_CONVENIENCE_STORE);
        types.put("COUNTRY", Place.TYPE_COUNTRY);
        types.put("COURTHOUSE", Place.TYPE_COURTHOUSE);
        types.put("DENTIST", Place.TYPE_DENTIST);
        types.put("DEPARTMENT_STORE", Place.TYPE_DEPARTMENT_STORE);
        types.put("DOCTOR", Place.TYPE_DOCTOR);
        types.put("ELECTRICIAN", Place.TYPE_ELECTRICIAN);
        types.put("ELECTRONICS_STORE", Place.TYPE_ELECTRONICS_STORE);
        types.put("EMBASSY", Place.TYPE_EMBASSY);
        types.put("ESTABLISHMENT", Place.TYPE_ESTABLISHMENT);
        types.put("FINANCE", Place.TYPE_FINANCE);
        types.put("FIRE_STATION", Place.TYPE_FIRE_STATION);
        types.put("FLOOR", Place.TYPE_FLOOR);
        types.put("FLORIST", Place.TYPE_FLORIST);
        types.put("FOOD", Place.TYPE_FOOD);
        types.put("FUNERAL_HOME", Place.TYPE_FUNERAL_HOME);
        types.put("FURNITURE_STORE", Place.TYPE_FURNITURE_STORE);
        types.put("GAS_STATION", Place.TYPE_GAS_STATION);
        types.put("GENERAL_CONTRACTOR", Place.TYPE_GENERAL_CONTRACTOR);
        types.put("GEOCODE", Place.TYPE_GEOCODE);
        types.put("GROCERY_OR_SUPERMARKET", Place.TYPE_GROCERY_OR_SUPERMARKET);
        types.put("GYM", Place.TYPE_GYM);
        types.put("HAIR_CARE", Place.TYPE_HAIR_CARE);
        types.put("HARDWARE_STORE", Place.TYPE_HARDWARE_STORE);
        types.put("HEALTH", Place.TYPE_HEALTH);
        types.put("HINDU_TEMPLE", Place.TYPE_HINDU_TEMPLE);
        types.put("HOME_GOODS_STORE", Place.TYPE_HOME_GOODS_STORE);
        types.put("HOSPITAL", Place.TYPE_HOSPITAL);
        types.put("INSURANCE_AGENCY", Place.TYPE_INSURANCE_AGENCY);
        types.put("INTERSECTION", Place.TYPE_INTERSECTION);
        types.put("JEWELRY_STORE", Place.TYPE_JEWELRY_STORE);
        types.put("LAUNDRY", Place.TYPE_LAUNDRY);
        types.put("LAWYER", Place.TYPE_LAWYER);
        types.put("LIBRARY", Place.TYPE_LIBRARY);
        types.put("LIQUOR_STORE", Place.TYPE_LIQUOR_STORE);
        types.put("LOCALITY", Place.TYPE_LOCALITY);
        types.put("LOCAL_GOVERNMENT_OFFICE", Place.TYPE_LOCAL_GOVERNMENT_OFFICE);
        types.put("LOCKSMITH", Place.TYPE_LOCKSMITH);
        types.put("LODGING", Place.TYPE_LODGING);
        types.put("MEAL_DELIVERY", Place.TYPE_MEAL_DELIVERY);
        types.put("MEAL_TAKEAWAY", Place.TYPE_MEAL_TAKEAWAY);
        types.put("MOSQUE", Place.TYPE_MOSQUE);
        types.put("MOVIE_RENTAL", Place.TYPE_MOVIE_RENTAL);
        types.put("MOVIE_THEATER", Place.TYPE_MOVIE_THEATER);
        types.put("MOVING_COMPANY", Place.TYPE_MOVING_COMPANY);
        types.put("MUSEUM", Place.TYPE_MUSEUM);
        types.put("NATURAL_FEATURE", Place.TYPE_NATURAL_FEATURE);
        types.put("NEIGHBORHOOD", Place.TYPE_NEIGHBORHOOD);
        types.put("NIGHT_CLUB", Place.TYPE_NIGHT_CLUB);
        types.put("OTHER", Place.TYPE_OTHER);
        types.put("PAINTER", Place.TYPE_PAINTER);
        types.put("PARK", Place.TYPE_PARK);
        types.put("PARKING", Place.TYPE_PARKING);
        types.put("PET_STORE", Place.TYPE_PET_STORE);
        types.put("PHARMACY", Place.TYPE_PHARMACY);
        types.put("PHYSIOTHERAPIST", Place.TYPE_PHYSIOTHERAPIST);
        types.put("PLACE_OF_WORSHIP", Place.TYPE_PLACE_OF_WORSHIP);
        types.put("PLUMBER", Place.TYPE_PLUMBER);
        types.put("POINT_OF_INTEREST", Place.TYPE_POINT_OF_INTEREST);
        types.put("POLICE", Place.TYPE_POLICE);
        types.put("POLITICAL", Place.TYPE_POLITICAL);
        types.put("POSTAL_CODE", Place.TYPE_POSTAL_CODE);
        types.put("POSTAL_CODE_PREFIX", Place.TYPE_POSTAL_CODE_PREFIX);
        types.put("POSTAL_TOWN", Place.TYPE_POSTAL_TOWN);
        types.put("POST_BOX", Place.TYPE_POST_BOX);
        types.put("POST_OFFICE", Place.TYPE_POST_OFFICE);
        types.put("PREMISE", Place.TYPE_PREMISE);
        types.put("REAL_ESTATE_AGENCY", Place.TYPE_REAL_ESTATE_AGENCY);
        types.put("RESTAURANT", Place.TYPE_RESTAURANT);
        types.put("ROOFING_CONTRACTOR", Place.TYPE_ROOFING_CONTRACTOR);
        types.put("ROOM", Place.TYPE_ROOM);
        types.put("ROUTE", Place.TYPE_ROUTE);
        types.put("RV_PARK", Place.TYPE_RV_PARK);
        types.put("SCHOOL", Place.TYPE_SCHOOL);
        types.put("SHOE_STORE", Place.TYPE_SHOE_STORE);
        types.put("SHOPPING_MALL", Place.TYPE_SHOPPING_MALL);
        types.put("SPA", Place.TYPE_SPA);
        types.put("STADIUM", Place.TYPE_STADIUM);
        types.put("STORAGE", Place.TYPE_STORAGE);
        types.put("STORE", Place.TYPE_STORE);
        types.put("STREET_ADDRESS", Place.TYPE_STREET_ADDRESS);
        types.put("SUBLOCALITY", Place.TYPE_SUBLOCALITY);
        types.put("SUBLOCALITY_LEVEL_1", Place.TYPE_SUBLOCALITY_LEVEL_1);
        types.put("SUBLOCALITY_LEVEL_2", Place.TYPE_SUBLOCALITY_LEVEL_2);
        types.put("SUBLOCALITY_LEVEL_3", Place.TYPE_SUBLOCALITY_LEVEL_3);
        types.put("SUBLOCALITY_LEVEL_4", Place.TYPE_SUBLOCALITY_LEVEL_4);
        types.put("SUBLOCALITY_LEVEL_5", Place.TYPE_SUBLOCALITY_LEVEL_5);
        types.put("SUBPREMISE", Place.TYPE_SUBPREMISE);
        types.put("SUBWAY_STATION", Place.TYPE_SUBWAY_STATION);
        types.put("SYNAGOGUE", Place.TYPE_SYNAGOGUE);
        types.put("SYNTHETIC_GEOCODE", Place.TYPE_SYNTHETIC_GEOCODE);
        types.put("TAXI_STAND", Place.TYPE_TAXI_STAND);
        types.put("TRAIN_STATION", Place.TYPE_TRAIN_STATION);
        types.put("TRANSIT_STATION", Place.TYPE_TRANSIT_STATION);
        types.put("TRAVEL_AGENCY", Place.TYPE_TRAVEL_AGENCY);
        types.put("UNIVERSITY", Place.TYPE_UNIVERSITY);
        types.put("VETERINARY_CARE", Place.TYPE_VETERINARY_CARE);
        types.put("ZOO", Place.TYPE_ZOO);

        return types;
    }
}
