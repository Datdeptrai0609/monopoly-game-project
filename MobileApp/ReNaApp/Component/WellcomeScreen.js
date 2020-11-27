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
    Alert
} from 'react-native';

import {
    Header,
    LearnMoreLinks,
    Colors,
    DebugInstructions,
    ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

//add animation lib
import * as Animatable from 'react-native-animatable';

// add component
import LogoMono from './Logo';

export default class WellcomeScreen extends Component {
    render() {
        return (
            //  {/* create containner  */}
            <KeyboardAvoidingView
                behavior={'height'}
                style={styles.container}>
                {/* using image background to set wellcome background */}
                <ImageBackground
                    style={styles.image}
                    source={imgWellcome}>
                    {/* logo IU and IoT */}
                    <Animatable.View 
                        animation = "fadeIn"
                        style={styles.containerLogo}
                        duration={1000}
                        delay={1600}>
                        <Image
                            source={logoIU}
                            style={styles.img}
                        />
                        <Image
                            style={styles.img}
                            source={logoIoT}
                        />
                    </Animatable.View>
                    <Animatable.View
                        animation ="zoomIn"
                        duration={500}
                        delay={1000}
                        style={styles.monoImg}>
                        <LogoMono />
                    </Animatable.View>
                    <Animatable.View
                        style= {styles.textInputView}>
                        <TextInput style = {styles.textInput}/>
                        <Button
                            title="Press me"
                            onPress={() => Alert.alert('Simple Button pressed')}
                        />
                    </Animatable.View>
                </ImageBackground>
            </KeyboardAvoidingView>
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
        flexDirection: "column",
        alignContent:'space-between'
    },
    image: {
        
        flex: 1,
        resizeMode: "cover",
        justifyContent: "center"
    },
    containerLogo: {
        display: 'flex',
        flex: 1.3,
        flexDirection: "row",
        // backgroundColor:'blue'
    },
    img: {
        resizeMode: 'center',
        width: 55,
        height: 55,
        marginLeft: 5,
        marginTop: 25
    },
    monoImg: {
        paddingLeft: 60,
        flex: 1,
        // marginTop: 50,
        // backgroundColor:'red',
        justifyContent:'center'
    },
    textInputView:{
        flex:1.6,
        // backgroundColor:'green',
        opacity: 0.3,
        alignItems:'center'
    },
    textInput: {
        width: 180,
        height: 60,
        borderWidth: 1,
        borderColor: 'black',
        borderRadius: 50,
        fontSize: 20,
        color:'black',
    }
})
