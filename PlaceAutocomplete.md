## PlaceAutocomplete

The autocomplete widget is a search dialog with built-in autocomplete functionality. As a user enters search terms, the widget presents a list of predicted places to choose from. When the user makes a selection, a `Place` instance is returned, which your app can then use to get details about the selected place.

![PlacePicker](images/acw_fullscreen.jpg)
![PlacePicker](images/acw_overlay.jpg)

To open this widget use the `placeAutocomplete` function. Its basic syntax is:
```js
placeAutocomplete(options?: PlaceAutocompleteOptions | null): Promise<Place>
```

where `options` is a plain object with the following optional properties:

Property | Type                     | Description
---------|--------------------------|------------
bounds   | `LatLngBounds` or `LatLng[]` | Bias results to a specific region.
mode     | string or number | Presentation mode. It can be 'fullscreen' (default) or 'overlay'.
filter   | plain object     | This can have a `country` and/or `type` properties.
filter.country | string     | ISO 3166-1 Alpha-2 country code.
filter.type    | string or number | Limit the results to a specific place type.<br>See Filter Types.

#### Filter Types

* `address`<br>
    Only return geocoding results with a precise address.

* `establishment`<br>
    Only return results that are classified as businesses.


* `geocode`<br>
    Only return geocoding results, rather than business results.
    For example, parks, cities and street addresses.

* `cities`<br>
    Return any result matching the following place types (from the `PlaceType` enum):
    - LOCALITY
    - ADMINISTRATIVE_AREA_LEVEL_3

* `regions`<br>
    Return any result matching the following place types (from the `PlaceType` enum):
    - LOCALITY
    - SUBLOCALITY
    - POSTAL_CODE
    - COUNTRY
    - ADMINISTRATIVE_AREA_LEVEL_1
    - ADMINISTRATIVE_AREA_LEVEL_2

#### Example
```js
import { placeAutocomplete } from 'react-native-google-places-ui'
// ...etc

    setResult(place) {
      console.log('Selected:', place.address)
      this.setState({ place })
    }

    selectCity() {
      const options = {
        mode: 'overlay',
        filter: {
          country: 'US',
          type: 'cities',
        }
      }
      placeAutocomplete(options)
        .then(this.setResult)
        .catch((error) => {
          if (error.code !== 'RESULT_CANCELED') {
            console.warn(`${error.code}: ${error.message}`)
          }
        })
    }
```
