import React from 'react'
import { StyleSheet, ToolbarAndroid } from 'react-native'
import { Color } from '../theme'

type Props = {
  title: string,
  backButton?: boolean,
  onBackPress?: () => void,
}

export class Toolbar extends React.PureComponent<Props> {
  render () {
    const { title, backButton, onBackPress } = this.props

    return (
      <ToolbarAndroid
        title={title}
        titleColor={Color.textColorPrimary}
        navIcon={backButton ? { uri: 'ic_arrow_back' } : undefined}
        onIconClicked={backButton && onBackPress ? onBackPress : undefined}
        style={styles.toolbar}
      />
    )
  }
}

const styles = StyleSheet.create({
  toolbar: {
    top: 0,
    left: 0,
    right: 0,
    height: 56,
    backgroundColor: Color.colorPrimary,
    elevation: 2,
  },
})
