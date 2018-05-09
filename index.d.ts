declare module "react-native-google-places-ui" {

  export interface PlaceError extends Error {
    code: string,
  }

  export interface PlaceType {
    readonly ACCOUNTING: number;
    readonly ADMINISTRATIVE_AREA_LEVEL_1: number;
    readonly ADMINISTRATIVE_AREA_LEVEL_2: number;
    readonly ADMINISTRATIVE_AREA_LEVEL_3: number;
    readonly AIRPORT: number;
    readonly AMUSEMENT_PARK: number;
    readonly AQUARIUM: number;
    readonly ART_GALLERY: number;
    readonly ATM: number;
    readonly BAKERY: number;
    readonly BANK: number;
    readonly BAR: number;
    readonly BEAUTY_SALON: number;
    readonly BICYCLE_STORE: number;
    readonly BOOK_STORE: number;
    readonly BOWLING_ALLEY: number;
    readonly BUS_STATION: number;
    readonly CAFE: number;
    readonly CAMPGROUND: number;
    readonly CAR_DEALER: number;
    readonly CAR_RENTAL: number;
    readonly CAR_REPAIR: number;
    readonly CAR_WASH: number;
    readonly CASINO: number;
    readonly CEMETERY: number;
    readonly CHURCH: number;
    readonly CITY_HALL: number;
    readonly CLOTHING_STORE: number;
    readonly COLLOQUIAL_AREA: number;
    readonly CONVENIENCE_STORE: number;
    readonly COUNTRY: number;
    readonly COURTHOUSE: number;
    readonly DENTIST: number;
    readonly DEPARTMENT_STORE: number;
    readonly DOCTOR: number;
    readonly ELECTRICIAN: number;
    readonly ELECTRONICS_STORE: number;
    readonly EMBASSY: number;
    readonly ESTABLISHMENT: number;
    readonly FINANCE: number;
    readonly FIRE_STATION: number;
    readonly FLOOR: number;
    readonly FLORIST: number;
    readonly FOOD: number;
    readonly FUNERAL_HOME: number;
    readonly FURNITURE_STORE: number;
    readonly GAS_STATION: number;
    readonly GENERAL_CONTRACTOR: number;
    readonly GEOCODE: number;
    readonly GROCERY_OR_SUPERMARKET: number;
    readonly GYM: number;
    readonly HAIR_CARE: number;
    readonly HARDWARE_STORE: number;
    readonly HEALTH: number;
    readonly HINDU_TEMPLE: number;
    readonly HOME_GOODS_STORE: number;
    readonly HOSPITAL: number;
    readonly INSURANCE_AGENCY: number;
    readonly INTERSECTION: number;
    readonly JEWELRY_STORE: number;
    readonly LAUNDRY: number;
    readonly LAWYER: number;
    readonly LIBRARY: number;
    readonly LIQUOR_STORE: number;
    readonly LOCALITY: number;
    readonly LOCAL_GOVERNMENT_OFFICE: number;
    readonly LOCKSMITH: number;
    readonly LODGING: number;
    readonly MEAL_DELIVERY: number;
    readonly MEAL_TAKEAWAY: number;
    readonly MOSQUE: number;
    readonly MOVIE_RENTAL: number;
    readonly MOVIE_THEATER: number;
    readonly MOVING_COMPANY: number;
    readonly MUSEUM: number;
    readonly NATURAL_FEATURE: number;
    readonly NEIGHBORHOOD: number;
    readonly NIGHT_CLUB: number;
    readonly OTHER: number;
    readonly PAINTER: number;
    readonly PARK: number;
    readonly PARKING: number;
    readonly PET_STORE: number;
    readonly PHARMACY: number;
    readonly PHYSIOTHERAPIST: number;
    readonly PLACE_OF_WORSHIP: number;
    readonly PLUMBER: number;
    readonly POINT_OF_INTEREST: number;
    readonly POLICE: number;
    readonly POLITICAL: number;
    readonly POSTAL_CODE: number;
    readonly POSTAL_CODE_PREFIX: number;
    readonly POSTAL_TOWN: number;
    readonly POST_BOX: number;
    readonly POST_OFFICE: number;
    readonly PREMISE: number;
    readonly REAL_ESTATE_AGENCY: number;
    readonly RESTAURANT: number;
    readonly ROOFING_CONTRACTOR: number;
    readonly ROOM: number;
    readonly ROUTE: number;
    readonly RV_PARK: number;
    readonly SCHOOL: number;
    readonly SHOE_STORE: number;
    readonly SHOPPING_MALL: number;
    readonly SPA: number;
    readonly STADIUM: number;
    readonly STORAGE: number;
    readonly STORE: number;
    readonly STREET_ADDRESS: number;
    readonly SUBLOCALITY: number;
    readonly SUBLOCALITY_LEVEL_1: number;
    readonly SUBLOCALITY_LEVEL_2: number;
    readonly SUBLOCALITY_LEVEL_3: number;
    readonly SUBLOCALITY_LEVEL_4: number;
    readonly SUBLOCALITY_LEVEL_5: number;
    readonly SUBPREMISE: number;
    readonly SUBWAY_STATION: number;
    readonly SYNAGOGUE: number;
    readonly SYNTHETIC_GEOCODE: number;
    readonly TAXI_STAND: number;
    readonly TRAIN_STATION: number;
    readonly TRANSIT_STATION: number;
    readonly TRAVEL_AGENCY: number;
    readonly UNIVERSITY: number;
    readonly VETERINARY_CARE: number;
    readonly ZOO: number;
  }

  export type FilterType = 'address' | 'cities' | 'establishment' | 'geocode' | 'regions' | '';
  export type InputMode = 'fullscreen' | 'overlay';

  export interface LatLng {
    latitude;
    longitude;
  }

  export interface LatLngBounds {
    southwest: LatLng;
    northeast: LatLng;
  }

  export interface Place {
    /**
     * Unique id of this place.
     * This ID can be passed to the Places API to lookup the same place at a later time.
     */
    id: string;
    /**
     * Human readable address for this Place. May be empty.
     */
    address: string;
    /**
     * The attributions to be shown to the user if data from the `Place` is used.
     */
    attributions: string;
    /**
     * Location of this Place.
     */
    latlng: LatLng;
    /**
     * The locale in which the names and addresses were localized.
     */
    locale?: string;
    /**
     * Name of this Place.
     * The name is localized according to the locale property.
     */
    name: string;
    /**
     * The place's phone number in international format or `undefined` if no phone number is known.
     */
    phoneNumber?: string;
    /**
     * List of place types for this Place.
     * The elements of this list are drawn from `PlaceType` constants.
     */
    placeTypes: number[];
    /**
     * Price level on a scale from 0 to 4 or a negative value if no price level is known.
     */
    priceLevel: number;
    /**
     * Place's rating, from 1.0 to 5.0, based on aggregated user reviews, or a negative value
     * if the place has no ratings.
     */
    rating: number;
    /**
     * Viewport of a size that is suitable for displaying this Place.
     */
    viewport?: LatLngBounds;
    /**
     * The URI of the website of this place or `undefined` if no website is known.
     */
    websiteUri?: string;
  }

  export type PlacePickerOptions = {
    /**
     * Determines the coordinates of the initial viewport.
     * If you pass an array of `LatLng` with only one element, the max zoom will be used.
     */
    bounds?: LatLngBounds | Array<LatLng | null> | null,
    /**
     * Use the last viewport of the map at the time the user's last selection was made.
     */
    useLastBounds?: boolean,
  };

  export type PlaceAutocompleteOptions = {
    /**
     * Area restriction.
     */
    bounds?: LatLngBounds | Array<LatLng | null> | null,
    /**
     * If falsy or omitted, overlay mode will be used.
     */
    mode?: InputMode | null,
    /**
     * Filter to use for restricting the returned predictions.
     * If null, a filter with no constraints will be used.
     */
    filter?: {
      /**
       * The country to restrict results to.
       * This should be a ISO 3166-1 Alpha-2 country code (case insensitive).
       * If this is not set, no country filtering will take place.
       */
      country?: string | null,
      /**
       * Allows you to restrict the result set of a Place Autocomplete request.
       * See FilterType constants.
       */
      type?: FilterType | null,
    }
  };

  export interface PlacesUIStatic {
    readonly PlaceType: PlaceType;

    placeAutocomplete(options: PlaceAutocompleteOptions | null): Promise<Place>;
    placePicker(options: PlacePickerOptions | null): Promise<Place>;

    // not docummented
    buildBounds(coords: LatLng[]): Promise<LatLngBounds>;
    getLastBounds(): Promise<LatLngBounds>;
    setLogLevel(level: number): void;
  }

  export const PlaceType: PlaceType;
  export const placeAutocomplete: (options?: PlaceAutocompleteOptions | null) => Promise<Place>;
  export const placePicker: (options?: PlacePickerOptions | null) => Promise<Place>;

  const PlacesUI: PlacesUIStatic
  export default PlacesUI
}
