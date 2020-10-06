import React, { Component, version} from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
} from 'react-native';

export default class App extends Component {

  render() {
    return(
      <View style = {styles.main_container}/>
    )
  }
}

const styles = StyleSheet.create({
  main_container: {
    ...StyleSheet.absoluteFill,
          backgroundColor: '#D9E5FF',
  }
})