import React, { Component } from 'react';
import {
    SafeAreaView,
    StyleSheet,
    ScrollView,
    View,
    Text,
    StatusBar,
    ImageBackground,
    Image,
    Animated
} from 'react-native';

import {
    Header,
    LearnMoreLinks,
    Colors,
    DebugInstructions,
    ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

// add component
import LogoMono from './Logo';
import FadeinView from './FadeinView'

export default class WellcomeScreen extends Component {
    render() {
        return (
            // create containner 
            <View style={styles.container}>
                {/* using image background to set wellcome background */}
                <ImageBackground
                    style={styles.image}
                    source={imgWellcome}>
                    {/* logo IU and IoT */}
                    <FadeinView
                        style={styles.containerLogo}
                        duration={1000}
                        delay={3000}>
                        <Image
                            source={logoIU}
                            style={styles.img}
                        />
                        <Image
                            style={styles.img}
                            source={logoIoT}
                        />
                    </FadeinView>
                    <FadeinView
                        duration={2000}
                        delay={1000}
                        style={styles.monoImg}>
                        <LogoMono />
                    </FadeinView>
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
const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: "column"
    },
    image: {
        display: 'flex',
        flex: 1,
        resizeMode: "cover",
        justifyContent: "center"
    },
    containerLogo: {
        display: 'flex',
        flex: 1,
        flexDirection: "row",
    },
    img: {
        resizeMode: 'center',
        width: 55,
        height: 55,
        marginLeft: 5,
        marginTop: 25
    },
    monoImg: {
        width: 200,
        marginLeft: 60,
        flex: 2,
        // justifyContent:'center'
    }
})

