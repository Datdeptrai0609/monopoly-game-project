/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

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

import WellcomeScreen from './Component/WellcomeScreen';
import ChooseCharacter from './Component/ChooseCharacter';


export default class App extends Component {
  render() {
    return (
      <ChooseCharacter/>
    )
  }
}
