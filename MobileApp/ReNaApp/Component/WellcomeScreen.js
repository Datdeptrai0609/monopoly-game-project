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

// add Logo component

import LogoMono from './Logo';

export default class WellcomeScreen extends Component {
    render() {
        return (
        // create containner 
        <View style = {styles.container}>
        {/* using image background to set wellcome background */}
        <ImageBackground
          style ={styles.image}
          source = {imgWellcome}>
            {/* logo IU and IoT */}
            <View
              style={styles.containerLogo}>
                <Image
                  source={logoIU}
                  style={styles.img}
                />
                <Image
                  style={styles.img}
                  source={logoIoT}
                />
            </View>
            <View>
                <LogoMono/>
            </View>
        </ImageBackground>
    </View>
        )
    }
}

//define img link
const imgWellcome = require('../img/background/backgroundImg.png');
const logoIoT = require('../img/logo/logoIoT.png');
const logoIU = require('../img/logo/IULogo.png');

//define css style 
const styles = StyleSheet.create ({
    container: {
        flex:1,
        flexDirection: "column"
    },
    image: {
        flex:1,
        resizeMode:"cover",
        justifyContent:"center"
    },
    containerLogo: {
        flex: 1,
        flexDirection: "row",
    },
    img: {
        resizeMode:'center',
        width: 55,
        height: 55,
        marginLeft: 5,
        marginTop: 25
    }
})

