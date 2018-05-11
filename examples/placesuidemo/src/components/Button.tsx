/*
  Botón de acción/comando similar al de React pero más potente.

  @TODO: Posibilidad de evitar doble click
*/
import React from 'react'
import {
  Text,
  TouchableNativeFeedback,
  TouchableOpacity,
  Platform,
  View,
  // typings
  TextStyle,
  TouchableOpacityProperties,
  AccessibilityPropertiesIOS,
  ViewProperties,
} from 'react-native'
import { Color } from '../theme'

type AccessibilityTraitsProp = AccessibilityPropertiesIOS['accessibilityTraits']
type Dict = { [k: string]: any }

type Root = React.Component<ViewProperties>

type Props = TouchableOpacityProperties & {
  text?: string,
  textStyle?: TextStyle,
  size?: 'small' | 'large',
  rippleColor?: string | null,
  block?: boolean,
  outline?: boolean,
  primary?: boolean,
  flat?: boolean,
  raised?: boolean,
  rounded?: boolean,
  disabled?: boolean,
}

const iOS = Platform.OS === 'ios'

export class Button extends React.PureComponent<Props> {

  private _root?: Root

  get root () {
    return this._root
  }

  prepareStyles (props: Props) {
    const assign  = Object.assign
    const ss      = styles

    const button    = { ...ss.base } as Dict
    const text      = { ...ss.text } as Dict
    const result    = { button, text }

    // block y rounded después del tipo ya que pueden sobrescrir valores
    if (props.block) {
      assign(button, ss.block)
    }
    if (props.rounded) {
      button.borderRadius = button.height / 2
      if (iOS && props.raised) {
        button.shadowRadius = 1.2
      }
    }

    // Aplicar size: small (dense) o large
    if (props.size === 'small') {
      assign(result, ss.small)
    } else if (props.size === 'large') {
      assign(result, ss.large)
    }

    // El color es excluyente, el default es "normal" aun no aplicado
    const index = props.primary ? 'primary' : 'normal'
    const { color, backgroundColor } = ss[index]

    if (props.disabled) {
      assign(button, ss.disabled)
    }

    // outline y raised son excluyentes, flat no se incluye pues está ya puesto
    if (props.outline) {
      text.color = button.borderColor = color
      assign(button, ss.outline)

    } else if (props.raised) {
      text.color = color
      button.backgroundColor = button.borderColor = backgroundColor
      assign(button, ss.raised)

    } else {
      text.color = color
      button.backgroundColor = backgroundColor
    }

    console.log(result)
    return result
  }

  setRoot = (root: Root) => {
    this._root = root
  }

  render () {
    const {
      text,
      textStyle,
      style,
      activeOpacity,
      rippleColor,
      disabled,
      onLongPress,
      onPress,
      ...props
    } = this.props
    const rootStyles = this.prepareStyles(this.props)

    const children: JSX.Element | null = text ? (
      <Text
        key="btnText"
        numberOfLines={1}
        style={textStyle ? [rootStyles.text, textStyle] : rootStyles.text}
      >
        {Platform.OS === 'android' ? text.toLocaleUpperCase() : text}
      </Text>
    ) : null

    const press = {} as any
    if (!disabled) {
      press.onPress = onPress
      press.onLongPress = onLongPress
    }

    const pointer = {} as any
    if (disabled) {
      pointer.pointerEvents = 'none'
    }

    if (iOS || Platform.Version <= 21) {
      const accessibilityTraits: AccessibilityTraitsProp =
        iOS ? disabled ? ['button', 'disabled'] : 'button' : undefined

      return (
        <TouchableOpacity
          accessibilityTraits={accessibilityTraits}
          activeOpacity={activeOpacity || 0.5}
          {...press}
        >
          <View ref={this.setRoot} style={[rootStyles.button, style]} {...props} {...pointer}>
            {children}
          </View>
        </TouchableOpacity>
      )
    }

    const ripple = rippleColor
      ? TouchableNativeFeedback.Ripple(rippleColor)
      : TouchableNativeFeedback.SelectableBackground()

    // background={TouchableNativeFeedback.Ripple(theme.androidRippleColor)}
    return (
      <TouchableNativeFeedback
        accessibilityComponentType="button"
        background={ripple}
        {...press}
      >
        <View ref={this.setRoot} style={[rootStyles.button, style]} {...props} {...pointer}>
          {children}
        </View>
      </TouchableNativeFeedback>
    )
  }

}

const styles = {

  // default button (flat)
  base: {
    alignItems: 'center',
    justifyContent: 'center',
    height: 48,
    minWidth: 88,
    paddingVertical: 6,
    paddingHorizontal: 8,
    borderRadius: iOS ? 5 : 2,
  },
  // default text
  text: {
    alignSelf: 'stretch',
    paddingHorizontal: 16,
    color: Color.textColorPrimary,
    fontFamily: 'sans-serif-medium',
    fontSize: 14,
    textAlign: 'center',
    textAlignVertical: 'center',
  },

  // extended styles
  outline: {
    borderWidth: 1.5,
  },
  raised: {
    ...Platform.select({
      ios: {
        shadowColor: '#000000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.2,
        shadowRadius: 1.2,
      },
      android: {
        elevation: 2,
      },
    }),
  },

  block: {
    alignSelf: 'stretch',
    justifyContent: 'center',
  },

  small: {
    button: {
      height: 32,
    },
    text: {
      fontSize: 13,
    },
  },
  large: {
    button: {
      height: 48,
    },
    text: {
      fontSize: 22,
    },
  },

  disabled: {
    opacity: 0.65,
  },

  normal: {
    color: Color.colorPrimary,
    backgroundColor: 'transparent',
  },
  primary: {
    color: Color.textColorPrimary,
    backgroundColor: Color.colorPrimary,
  },

}
