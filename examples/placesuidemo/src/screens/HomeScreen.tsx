/**
 * React Native PlacesPicker Demo
 */
import React from 'react'
import {
  StatusBar,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native'
import { Toolbar } from '../components'
import { Color, Style } from '../theme'

// typings
import { NavigationScreenProps } from 'react-navigation'

type Props = NavigationScreenProps

export class HomeScreen extends React.Component<Props> {

  constructor (props: Props) {
    super(props)

    StatusBar.setBackgroundColor(Color.colorPrimaryDark)
  }

  render () {
    return (
      <React.Fragment>
        <Toolbar
          title="Google Places UI Demo"
        />

        <View style={Style.container}>

          <Text style={Style.heading}>
            Tap any link to see Google Places UI in action.
          </Text>

          <TouchableOpacity
            activeOpacity={0.75}
            onPress={() => { this.props.navigation.navigate('Autocomplete') }} >
            <View>
              <Text style={styles.link}>{'‣ PlaceAutocomplete'}</Text>
            </View>
          </TouchableOpacity>

          <TouchableOpacity
            activeOpacity={0.75}
            onPress={() => { this.props.navigation.navigate('Picker') }} >
            <View>
              <Text style={styles.link}>{'‣ PlacePicker'}</Text>
            </View>
          </TouchableOpacity>
        </View>

      </React.Fragment>
    )
  }
}

const styles = StyleSheet.create({
  link: {
    alignSelf: 'stretch',
    paddingVertical: 10,
    fontSize: 18,
  },
})
