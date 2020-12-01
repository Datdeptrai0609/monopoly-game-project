import React, { Component } from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  ImageBackground,
} from 'react-native';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

// import navigation 
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

//import screens
import WellcomeScreen from './Component/WellcomeScreen';
import ChooseCharater from './Component/ChooseCharacter';
import * as Animatable from 'react-native-animatable';

export default class App extends Component {
  render() {
    return (
        <WellcomeScreen/>
      // <ChooseCharater/>
    )
  }
}

const Stack = createStackNavigator();