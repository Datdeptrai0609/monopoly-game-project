import React, { Component } from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  ImageBackground, 
  BackHandler, 
  Alert
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
  backAction = () => {
    Alert.alert("Hold on!", "Are you sure you want to go back?", [
      {
        text: "Cancel",
        onPress: () => null,
        style: "cancel"
      },
      { text: "YES", onPress: () => BackHandler.exitApp() }
    ]);
    return true;
  };

  componentDidMount() {
    BackHandler.addEventListener("hardwareBackPress", this.backAction);
  }

  componentWillUnmount() {
    BackHandler.removeEventListener("hardwareBackPress", this.backAction);
  }
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
            onback = {() => {return false}}
            hideNavBar
          />
        </Scene>
      </Router>
      // <WellcomeScreen/>
  )
}}
