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
    Dimensions,
    FlatList
} from 'react-native';

import {
    Header,
    LearnMoreLinks,
    Colors,
    DebugInstructions,
    ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import * as Animatable from 'react-native-animatable';
import List from './Character';

export default class ChooseCharacter extends Component {
    
        
    render() {
        return (
            <View
                style={styles.container}>
                <ImageBackground
                    source={imgBackground}
                    style={styles.imageBackground}>
                    <Animatable.View
                        animation='fadeIn'
                        delay = {700}
                        style = {styles.windowsChoose}>
                        <Text
                            style = {styles.text}>C h o o s e    Y o u r    C h a r a c t e r !</Text>
                        <List/>
                        <View
                            style = {styles.test}>
                            <TouchableOpacity
                                disabled={true}
                                style = {styles.buttonOff}>
                                <Text style = {styles.textReady}> READY </Text>
                            </TouchableOpacity>
                        </View>
                    </Animatable.View>
                    <Animatable.Image
                        animation='zoomIn'
                        delay ={100}
                        duration ={700}
                        source={logoMonoSmall}
                        style={styles.logoImg}>
                    </Animatable.Image>
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
        alignItems:'center',
    },
    logoImg: {
        position:'absolute',
        marginTop: 45
    },
    windowsChoose: {

        marginTop: 93,
        position: 'absolute',
        width: width*7/8,
        height: height*0.82,
        backgroundColor:'rgba(0,0,0,0.5)',
        borderWidth: 4,
        borderRadius: 10,
        borderColor:'#6E1CE9',
        alignItems: 'center'
    },
    playerContainer: {
        width: 300,
        height: 400,
        flex:3,
        flexDirection:'row',
        flexWrap: 'wrap',
        justifyContent:'center',
        alignContent:'center',
        backgroundColor: 'rgba(0,0,0,0.8)'
    },
    
    test: {
        flex:1,
        width: 300,
        height: 400,
        justifyContent: 'center',
        alignItems:'center'
    },
    text:{
        flex:0.2,
        fontStyle:'italic',
        fontSize: 20,
        paddingTop:40,
        color: 'yellow',
        fontWeight:'bold'
    },
    buttonOff: {
        width: 190,
        height: 70,
        backgroundColor:'#00ff48',
        borderRadius: 18,
        
    },
    buttonOn: {
        width: 190,
        height: 70,
        backgroundColor: 'gray',
        borderRadius: 18,
    },
    textReady: {
        color:'white',
        fontSize:50,
        fontWeight:'bold',
        textAlign:'center'
    }


})