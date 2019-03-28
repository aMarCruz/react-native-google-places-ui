package io.github.amarcruz.googleplacesui;

import android.util.SparseArray;

import com.google.android.gms.location.places.Place;

final class PlaceTypes {

    private static final SparseArray<String> types = new SparseArray<>(127);
    static {
        types.put(Place.TYPE_OTHER, "other");
        types.put(Place.TYPE_ACCOUNTING, "accounting");
        types.put(Place.TYPE_AIRPORT, "airport");
        types.put(Place.TYPE_AMUSEMENT_PARK, "amusement_park");
        types.put(Place.TYPE_AQUARIUM, "aquarium");
        types.put(Place.TYPE_ART_GALLERY, "art_gallery");
        types.put(Place.TYPE_ATM, "atm");
        types.put(Place.TYPE_BAKERY, "bakery");
        types.put(Place.TYPE_BANK, "bank");
        types.put(Place.TYPE_BAR, "bar");
        types.put(Place.TYPE_BEAUTY_SALON, "beauty_salon");
        types.put(Place.TYPE_BICYCLE_STORE, "bicycle_store");
        types.put(Place.TYPE_BOOK_STORE, "book_store");
        types.put(Place.TYPE_BOWLING_ALLEY, "bowling_alley");
        types.put(Place.TYPE_BUS_STATION, "bus_station");
        types.put(Place.TYPE_CAFE, "cafe");
        types.put(Place.TYPE_CAMPGROUND, "campground");
        types.put(Place.TYPE_CAR_DEALER, "car_dealer");
        types.put(Place.TYPE_CAR_RENTAL, "car_rental");
        types.put(Place.TYPE_CAR_REPAIR, "car_repair");
        types.put(Place.TYPE_CAR_WASH, "car_wash");
        types.put(Place.TYPE_CASINO, "casino");
        types.put(Place.TYPE_CEMETERY, "cemetery");
        types.put(Place.TYPE_CHURCH, "church");
        types.put(Place.TYPE_CITY_HALL, "city_hall");
        types.put(Place.TYPE_CLOTHING_STORE, "clothing_store");
        types.put(Place.TYPE_CONVENIENCE_STORE, "convenience_store");
        types.put(Place.TYPE_COURTHOUSE, "courthouse");
        types.put(Place.TYPE_DENTIST, "dentist");
        types.put(Place.TYPE_DEPARTMENT_STORE, "department_store");
        types.put(Place.TYPE_DOCTOR, "doctor");
        types.put(Place.TYPE_ELECTRICIAN, "electrician");
        types.put(Place.TYPE_ELECTRONICS_STORE, "electronics_store");
        types.put(Place.TYPE_EMBASSY, "embassy");
        types.put(Place.TYPE_ESTABLISHMENT, "establishment");
        types.put(Place.TYPE_FINANCE, "finance");
        types.put(Place.TYPE_FIRE_STATION, "fire_station");
        types.put(Place.TYPE_FLORIST, "florist");
        types.put(Place.TYPE_FOOD, "food");
        types.put(Place.TYPE_FUNERAL_HOME, "funeral_home");
        types.put(Place.TYPE_FURNITURE_STORE, "furniture_store");
        types.put(Place.TYPE_GAS_STATION, "gas_station");
        types.put(Place.TYPE_GENERAL_CONTRACTOR, "general_contractor");
        types.put(Place.TYPE_GROCERY_OR_SUPERMARKET, "grocery_or_supermarket");
        types.put(Place.TYPE_GYM, "gym");
        types.put(Place.TYPE_HAIR_CARE, "hair_care");
        types.put(Place.TYPE_HARDWARE_STORE, "hardware_store");
        types.put(Place.TYPE_HEALTH, "health");
        types.put(Place.TYPE_HINDU_TEMPLE, "hindu_temple");
        types.put(Place.TYPE_HOME_GOODS_STORE, "home_goods_store");
        types.put(Place.TYPE_HOSPITAL, "hospital");
        types.put(Place.TYPE_INSURANCE_AGENCY, "insurance_agency");
        types.put(Place.TYPE_JEWELRY_STORE, "jewelry_store");
        types.put(Place.TYPE_LAUNDRY, "laundry");
        types.put(Place.TYPE_LAWYER, "lawyer");
        types.put(Place.TYPE_LIBRARY, "library");
        types.put(Place.TYPE_LIQUOR_STORE, "liquor_store");
        types.put(Place.TYPE_LOCAL_GOVERNMENT_OFFICE, "local_government_office");
        types.put(Place.TYPE_LOCKSMITH, "locksmith");
        types.put(Place.TYPE_LODGING, "lodging");
        types.put(Place.TYPE_MEAL_DELIVERY, "meal_delivery");
        types.put(Place.TYPE_MEAL_TAKEAWAY, "meal_takeaway");
        types.put(Place.TYPE_MOSQUE, "mosque");
        types.put(Place.TYPE_MOVIE_RENTAL, "movie_rental");
        types.put(Place.TYPE_MOVIE_THEATER, "movie_theater");
        types.put(Place.TYPE_MOVING_COMPANY, "moving_company");
        types.put(Place.TYPE_MUSEUM, "museum");
        types.put(Place.TYPE_NIGHT_CLUB, "night_club");
        types.put(Place.TYPE_PAINTER, "painter");
        types.put(Place.TYPE_PARK, "park");
        types.put(Place.TYPE_PARKING, "parking");
        types.put(Place.TYPE_PET_STORE, "pet_store");
        types.put(Place.TYPE_PHARMACY, "pharmacy");
        types.put(Place.TYPE_PHYSIOTHERAPIST, "physiotherapist");
        types.put(Place.TYPE_PLACE_OF_WORSHIP, "place_of_worship");
        types.put(Place.TYPE_PLUMBER, "plumber");
        types.put(Place.TYPE_POLICE, "police");
        types.put(Place.TYPE_POST_OFFICE, "post_office");
        types.put(Place.TYPE_REAL_ESTATE_AGENCY, "real_estate_agency");
        types.put(Place.TYPE_RESTAURANT, "restaurant");
        types.put(Place.TYPE_ROOFING_CONTRACTOR, "roofing_contractor");
        types.put(Place.TYPE_RV_PARK, "rv_park");
        types.put(Place.TYPE_SCHOOL, "school");
        types.put(Place.TYPE_SHOE_STORE, "shoe_store");
        types.put(Place.TYPE_SHOPPING_MALL, "shopping_mall");
        types.put(Place.TYPE_SPA, "spa");
        types.put(Place.TYPE_STADIUM, "stadium");
        types.put(Place.TYPE_STORAGE, "storage");
        types.put(Place.TYPE_STORE, "store");
        types.put(Place.TYPE_SUBWAY_STATION, "subway_station");
        types.put(Place.TYPE_SYNAGOGUE, "synagogue");
        types.put(Place.TYPE_TAXI_STAND, "taxi_stand");
        types.put(Place.TYPE_TRAIN_STATION, "train_station");
        types.put(Place.TYPE_TRAVEL_AGENCY, "travel_agency");
        types.put(Place.TYPE_UNIVERSITY, "university");
        types.put(Place.TYPE_VETERINARY_CARE, "veterinary_care");
        types.put(Place.TYPE_ZOO, "zoo");
        types.put(Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_1, "administrative_area_level_1");
        types.put(Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_2, "administrative_area_level_2");
        types.put(Place.TYPE_ADMINISTRATIVE_AREA_LEVEL_3, "administrative_area_level_3");
        types.put(Place.TYPE_COLLOQUIAL_AREA, "colloquial_area");
        types.put(Place.TYPE_COUNTRY, "country");
        types.put(Place.TYPE_FLOOR, "floor");
        types.put(Place.TYPE_GEOCODE, "geocode");
        types.put(Place.TYPE_INTERSECTION, "intersection");
        types.put(Place.TYPE_LOCALITY, "locality");
        types.put(Place.TYPE_NATURAL_FEATURE, "natural_feature");
        types.put(Place.TYPE_NEIGHBORHOOD, "neighborhood");
        types.put(Place.TYPE_POLITICAL, "political");
        types.put(Place.TYPE_POINT_OF_INTEREST, "point_of_interest");
        types.put(Place.TYPE_POST_BOX, "post_box");
        types.put(Place.TYPE_POSTAL_CODE, "postal_code");
        types.put(Place.TYPE_POSTAL_CODE_PREFIX, "postal_code_prefix");
        types.put(Place.TYPE_POSTAL_TOWN, "postal_town");
        types.put(Place.TYPE_PREMISE, "premise");
        types.put(Place.TYPE_ROOM, "room");
        types.put(Place.TYPE_ROUTE, "route");
        types.put(Place.TYPE_STREET_ADDRESS, "street_address");
        types.put(Place.TYPE_SUBLOCALITY, "sublocality");
        types.put(Place.TYPE_SUBLOCALITY_LEVEL_1, "sublocality_level_1");
        types.put(Place.TYPE_SUBLOCALITY_LEVEL_2, "sublocality_level_2");
        types.put(Place.TYPE_SUBLOCALITY_LEVEL_3, "sublocality_level_3");
        types.put(Place.TYPE_SUBLOCALITY_LEVEL_4, "sublocality_level_4");
        types.put(Place.TYPE_SUBLOCALITY_LEVEL_5, "sublocality_level_5");
        types.put(Place.TYPE_SUBPREMISE, "subpremise");
        types.put(Place.TYPE_SYNTHETIC_GEOCODE, "synthetic_geocode");
        types.put(Place.TYPE_TRANSIT_STATION, "transit_station");
    };

    private PlaceTypes() {}

    static String getPlaceTypeById(final int id) {
        final String type = types.get(id);

        return type == null ? types.get(Place.TYPE_OTHER) : type;
    }

}
