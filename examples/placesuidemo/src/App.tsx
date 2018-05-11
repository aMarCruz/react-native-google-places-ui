/**
 * React Native PlacesPicker Demo
 */
import { YellowBox } from 'react-native'
import { createStackNavigator } from './createStackNavigator'
import { HomeScreen } from './screens/HomeScreen'
import { PlacePicker } from './screens/PlacePicker'
import { PlaceAutocomplete } from './screens/PlaceAutocomplete'
import { Color } from './theme'

// Stop warning comming from old RN code
YellowBox.ignoreWarnings(['Warning: isMounted(...) is deprecated', 'Module RCTImageLoader'])

export default createStackNavigator(
  {
    Home: {
      screen: HomeScreen,
    },
    Autocomplete: {
      screen: PlaceAutocomplete,
    },
    Picker: {
      screen: PlacePicker,
    },
  },
  {
    initialRouteName: 'Home',
    headerMode: 'none',
    cardStyle: {
      backgroundColor: Color.backgroundColor,
    },
    navigationOptions: {
      title: 'Google Places UI Demo',
      headerTintColor: Color.textColorPrimary,
      headerStyle: {
        backgroundColor: Color.colorPrimary,
      },
    },
  }
)
