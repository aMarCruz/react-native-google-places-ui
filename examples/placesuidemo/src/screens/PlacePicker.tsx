/**
 * React Native PlacesPicker Demo
 */
import React from 'react'
import {
  Picker,
  ScrollView,
  StatusBar,
  Text,
  View,
} from 'react-native'
import Places, {
  // typings
  LatLng,
  LatLngBounds,
  Place,
  PlaceError,
  PlacePickerOptions,
} from 'react-native-google-places-ui'
import {
  Button,
  Toolbar,
} from '../components'
import {
  Color,
  Style,
} from '../theme'

// typings
import { NavigationScreenProps } from 'react-navigation'

type Props = NavigationScreenProps

type State = {
  result: string,
  bounds: LatLngBounds | null,
  latlng: LatLng | null,
  useBounds: BoundsFrom,
}

const enum BoundsFrom {
  NONE,
  LOCATION,
  VIEWPORT,
  BOUNDS,
}

export class PlacePicker extends React.Component<Props, State> {

  static navigationOptions = {
    title: 'PlacePicker Demo',
  }

  constructor (props: Props) {
    super(props)

    StatusBar.setBackgroundColor(Color.colorPrimaryDark)
    this.state = {
      bounds: null,
      latlng: null,
      useBounds: BoundsFrom.NONE,
      result: `placePicker takes an object with a property 'bounds' as parameter.

'bounds' can be null (will use the user location and default zoom), a LatLngBounds object, or an array of one or more LatLng elements.

Pass LatLng[] with only one element to use maximum zoom.`,
    }
  }

  handleSuccess = (place: Place) => {
    this.setState({
      bounds: place.viewport || null,
      latlng: place.latlng,
      result: JSON.stringify(place, null, '  '),
    })
  }

  handleError = (error: PlaceError) => {
    this.setState({ result: `${error.code}\n${error.message}` })
  }

  openPicker = () => {
    const { useBounds, bounds, latlng } = this.state

    let options: PlacePickerOptions | null = null
    switch (useBounds) {
      case BoundsFrom.BOUNDS:
        options = { bounds }
        break
      case BoundsFrom.LOCATION:
        options = { bounds: [latlng] }
        break
      case BoundsFrom.VIEWPORT:
        options = { useLastBounds: true }
        break
    }

    Places.placePicker(options).then(this.handleSuccess).catch(this.handleError)
  }

  setUseBounds = (useBounds: BoundsFrom) => {
    this.setState({ useBounds })
  }

  render () {
    return (
      <React.Fragment>
        <Toolbar
          title="PlacePicker"
          backButton
          onBackPress={this.props.navigation.goBack}
        />

        <View style={Style.container}>
          <Text style={Style.heading}>
            To open the PlacePicker tap Open
          </Text>

          <View style={Style.itemRow}>
            <Text>bounds:</Text>
            <Picker
              selectedValue={this.state.useBounds}
              onValueChange={this.setUseBounds}
              style={Style.picker}
            >
              <Picker.Item label="None (current location/default zoom)" value={BoundsFrom.NONE} />
              <Picker.Item label="Last selected location" value={BoundsFrom.LOCATION} />
              <Picker.Item label="Last viewport" value={BoundsFrom.VIEWPORT} />
              <Picker.Item label="For optimal zoom" value={BoundsFrom.BOUNDS} />
            </Picker>
          </View>

          <Button
            outline
            rounded
            text="Open"
            onPress={this.openPicker}
            style={Style.button}
          />
          <Text>
            Result:
          </Text>
          <ScrollView style={Style.resultBox}>
            <Text style={Style.result}>
              {this.state.result}
            </Text>
          </ScrollView>
        </View>
      </React.Fragment>
    )
  }
}
