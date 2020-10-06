import React, { Component, version} from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  ImageBackground,
} from 'react-native';

export default class App extends Component {

  render() {
    return(
      
      // <View> 
        <ImageBackground 
          source={ require('../monopolyMobileApp/img/backgroundImg.png')}
        style = {styles.image}>

        </ImageBackground>
      // </View>
    )
  }
}

const styles = StyleSheet.create({
  main_container: {
    ...StyleSheet.absoluteFill,
          backgroundColor: '#D9E5FF',
  },
  image: {
    flex:1,
    resizeMode: "cover",
    justifyContent: "center",
  }
})