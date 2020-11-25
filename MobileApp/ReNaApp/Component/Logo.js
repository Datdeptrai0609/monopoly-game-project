import React, { Component } from 'react';
import {
    SafeAreaView,
    StyleSheet,
    ScrollView,
    View,
    Text,
    StatusBar,
    ImageBackground,
    Image
} from 'react-native';

import {
    Header,
    LearnMoreLinks,
    Colors,
    DebugInstructions,
    ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';


export default class LogoMono extends Component {
    render() {
        return(
            <Image
               source = {monoLogo}
               style ={styles.monoImg}
            />
        );
    }
}


//define link
const monoLogo = require('../img/logo/monoLogo.png')

//define css style 
const styles = StyleSheet.create ({
    // monoImg: {
        // width: 10,
        // marginBottom:290
    // }
})