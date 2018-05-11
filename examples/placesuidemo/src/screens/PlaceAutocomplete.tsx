/**
 * React Native PlacesPicker Demo
 */
import React from 'react'
import {
  ScrollView,
  StatusBar,
  Picker,
  StyleSheet,
  Switch,
  Text,
  TextInput,
  View,
} from 'react-native'
import Places, {
  // typings
  FilterType,
  InputMode,
  LatLngBounds,
  Place,
  PlaceError,
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
  useBounds: boolean,
  mode: InputMode,
  filterType: FilterType | null,
  country: string,
}

const Sydney: LatLngBounds = {
  southwest: {
    latitude: -33.880490,
    longitude: 151.184363,
  },
  northeast: {
    latitude: -33.858754,
    longitude: 151.229596,
  },
}

export class PlaceAutocomplete extends React.Component<Props, State> {

  static navigationOptions = {
    title: 'PlacePicker Demo',
  }

  constructor (props: Props) {
    super(props)

    StatusBar.setBackgroundColor(Color.colorPrimaryDark)
    this.state = {
      useBounds: false,
      mode: 'fullscreen',
      filterType: null,
      country: '',
      result: `placeAutocomplete takes an object with this props:

mode: 'fullscreen', 'overlay' or one of PlacesUI.Mode values.
bounds: null, LatLngBounds, or LatLng[] (to restrict the result set of the request).
filter: object with 'type' and 'country' properties.
`,
    }
  }

  handleSuccess = (place: Place) => {
    this.setState({
      result: JSON.stringify(place, null, '  '),
    })
  }

  handleError = (error: PlaceError) => {
    this.setState({ result: `${error.code}\n${error.message}` })
  }

  openPicker = () => {
    const { useBounds, mode, filterType, country } = this.state
    const options = {
      bounds: useBounds ? Sydney : null,
      mode,
      filter: {
        type: filterType,
        country: country || null,
      },
    }
    Places.placeAutocomplete(options)
      .then(this.handleSuccess).catch(this.handleError)
  }

  setUseBounds = (useBounds: boolean) => {
    this.setState({ useBounds })
  }

  setMode = (mode: InputMode) => {
    this.setState({ mode })
  }

  setFilterType = (filterType: FilterType) => {
    this.setState({ filterType })
  }

  setCountry = (country: string) => {
    this.setState({ country })
  }

  render () {
    return (
      <React.Fragment>
        <Toolbar
          title="PlaceAutocomplete"
          backButton
          onBackPress={this.props.navigation.goBack}
        />

        <View style={Style.container}>
          <Text style={Style.heading}>
            Set the options and tap Open
          </Text>

          <View style={Style.itemRow}>
            <Text>bounds (limit to Sydney, AU):</Text>
            <Switch
              value={this.state.useBounds}
              onValueChange={this.setUseBounds}
            />
          </View>
          <View style={Style.itemRow}>
            <Text>mode:</Text>
            <Picker
              selectedValue={this.state.mode}
              onValueChange={this.setMode}
              style={styles.picker}
            >
              <Picker.Item label="fullscreen" value="fullscreen" />
              <Picker.Item label="overlay" value="overlay" />
            </Picker>
          </View>
          <View style={Style.itemRow}>
            <Text>filter.type:</Text>
            <Picker
              selectedValue={this.state.filterType}
              onValueChange={this.setFilterType}
              style={styles.picker}
            >
              <Picker.Item label="none" value={null} />
              <Picker.Item label="address" value="address" />
              <Picker.Item label="geocode" value="geocode" />
              <Picker.Item label="establishment" value="establishment" />
              <Picker.Item label="regions" value="regions" />
              <Picker.Item label="cities" value="cities" />
            </Picker>
          </View>
          <View style={Style.itemRow}>
            <Text>filter.country (ISO 3166-1)</Text>
            <TextInput
              autoCapitalize="characters"
              autoCorrect={false}
              value={this.state.country}
              onChangeText={this.setCountry}
              maxLength={2}
              style={{ flexGrow: 1, marginLeft: 8 }}
            />
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

const styles = StyleSheet.create({
  picker: {
    flexGrow: 1,
    height: '100%',
  },
})
