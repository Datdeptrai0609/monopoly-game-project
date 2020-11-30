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
    Animated,
    TextInput,
    KeyboardAvoidingView,
    Button,
    Alert,
    TouchableOpacity,
    Keyboard,
    Dimensions
} from 'react-native';

import {
    Header,
    LearnMoreLinks,
    Colors,
    DebugInstructions,
    ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

export default class ChooseCharacter extends Component {
    render() {
        return (
            <View
                style={styles.container}>
                <ImageBackground
                    source={imgBackground}
                    style={styles.imageBackground}>
                    <View
                        style = {styles.windowsChoose}>
                    </View>
                    <Image
                        source={logoMonoSmall}
                        style={styles.logoImg}>
                    </Image>
                </ImageBackground>
            </View>
        )
    }

}

const {height, width}= Dimensions.get('window');
const logoMonoSmall = require('../img/logo/monoLogoSmall.png');
const imgBackground = require('../img/background/backgroundImgBlur.png');

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: "column",
    },
    imageBackground: {
        flex: 1,
        resizeMode: "cover",
        // justifyContent: "center"
        alignItems:'center',
    },
    logoImg: {
        position:'absolute',
        marginTop: 50
    },
    windowsChoose: {
        marginTop: height *1/7,
        position: 'absolute',
        width: width*5/6,
        height: height*3/4,
        backgroundColor:'rgba(0,0,0,0.6)',
        borderWidth: 4,
        borderRadius: 10,
        borderColor:'#FF00DD',
    }


})