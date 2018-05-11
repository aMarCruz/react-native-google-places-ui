import { StyleSheet } from 'react-native'

export const Color = {
  colorPrimary: '#3f51b5',
  colorPrimaryDark: '#002984',
  textColorPrimary: '#ffffff',
  textColor: '#000000',
  backgroundColor: '#fafafa',
}

export const Style = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: Color.backgroundColor,
  },
  heading: {
    marginBottom: 20,
  },
  itemRow: {
    alignSelf: 'center',
    alignItems: 'center',
    justifyContent: 'space-between',
    width: '90%',
    height: 38,
    maxWidth: 600,
    marginBottom: 10,
    flexDirection: 'row',
  },
  picker: {
    flexGrow: 1,
    height: '100%',
  },
  button: {
    alignSelf: 'flex-end',
    marginTop: 10,
    marginRight: 5,
  },
  resultBox: {
    alignSelf: 'stretch',
    paddingHorizontal: 10,
    marginTop: 10,
  },
  result: {
    textAlign: 'left',
    fontFamily: 'monospace',
    fontSize: 12,
  },
})
