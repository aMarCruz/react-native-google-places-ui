/*
  @types/react-navigation does not includes (yet) createStackNavigator so
  we do it here.
*/

declare module 'react-navigation' {
  export function createStackNavigator(
    routeConfigMap: NavigationRouteConfigMap,
    stackConfig?: StackNavigatorConfig,
  ): NavigationContainer;
}

import { createStackNavigator } from 'react-navigation'
export { createStackNavigator }
