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
import { Router, Scene } from 'react-native-router-flux';

//import screens
import WellcomeScreen from './Component/WellcomeScreen';
import ChooseCharater from './Component/ChooseCharacter';
import * as Animatable from 'react-native-animatable';

export default class App extends Component {
  render() {
    return (
      <Router>
        <Scene key="root" hideNavBar>
          <Scene
            key="Wellcome"
            component={WellcomeScreen}
            hideNavBar
          />
          <Scene
            key="chooseCharacter"
            component={ChooseCharater}
            hideNavBar
          />
        </Scene>
      </Router>
      // <WellcomeScreen/>
  )
}}
