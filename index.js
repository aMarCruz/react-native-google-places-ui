import { NativeModules } from 'react-native'

const PlacesUI = NativeModules.GooglePlacesUI

export default PlacesUI

export function placesAutocomplete (options) {
  return PlacesUI.placesPickerSimple(options || null)
}

export function placePicker (options) {
  return PlacesUI.placePicker(options || null)
}

export const PlaceType = PlacesUI.PlaceType
