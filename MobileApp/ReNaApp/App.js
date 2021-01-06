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
import Login from './Component/Login';
import ChooseCharater from './Component/ChooseCharacter';
import * as Animatable from 'react-native-animatable';
import Welcome from './Component/Welcome';
import Waiting from './Component/WaitingScreen';
import GameScreen from './Component/GameScreen';


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
            key="Welcome"
            component={Welcome}
            hideNavBar
          />
          <Scene
            key="Login"
            component={Login}
            hideNavBar
          />
          <Scene
            key="chooseCharacter"
            component={ChooseCharater}
            onback = {() => {return false}}
            hideNavBar
          />
          <Scene
            key="waiting"
            component={Waiting}
            onback={() => { return false }}
            hideNavBar
          />
          <Scene
            key="GameScreen"
            component={GameScreen}
            hideNavBar
          />
        </Scene>
      </Router>
      // <WellcomeScreen/>
  )
}}
