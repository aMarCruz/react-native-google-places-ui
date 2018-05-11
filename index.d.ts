declare module "react-native-google-places-ui" {

  export interface PlaceError extends Error {
    code: string,
  }

  export type FilterType = 'address' | 'cities' | 'establishment' | 'geocode' | 'regions' | '';
  export type InputMode = 'fullscreen' | 'overlay';

  export interface LatLng {
    latitude: number;
    longitude: number;
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
     */
    placeTypes: string[];
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
    website?: string;
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
    placeAutocomplete(options: PlaceAutocompleteOptions | null): Promise<Place>;
    placePicker(options: PlacePickerOptions | null): Promise<Place>;
    // not docummented yet
    buildBounds(coords: LatLng[]): Promise<LatLngBounds>;
    getLastBounds(): Promise<LatLngBounds>;
    setLogLevel(level: number): void;
  }

  export const placeAutocomplete: (options?: PlaceAutocompleteOptions | null) => Promise<Place>;
  export const placePicker: (options?: PlacePickerOptions | null) => Promise<Place>;

  const PlacesUI: PlacesUIStatic
  export default PlacesUI
}
