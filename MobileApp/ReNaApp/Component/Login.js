import mqtt from 'mqtt/dist/mqtt';
import React, { Component, useContext } from 'react';
import {
    StyleSheet,
    Text,
    ImageBackground,
    Image,
    TextInput,
    KeyboardAvoidingView,
    Alert,
    TouchableOpacity,
    Dimensions
} from 'react-native';

//add animation lib
import * as Animatable from 'react-native-animatable';
import { Actions } from 'react-native-router-flux';

export default class Login extends Component {
    state = {
        placeholder: 'Game PIN',
        roomNumber: 0,
        count: 0,
        client: mqtt.connect("ws://hcmiuiot.tech:8080"),
        PIN: ''
    }

    constructor(props) {
        super(props);
        this.state.client.on('connect', () => {
            // Handle PIN!
          this.state.client.subscribe("onConnect/"+this.state.PIN, function (err) {
            if (!err) {
            }
          });
    });

    // Handle comming msg:
    this.state.client.on('message', (topic, message) => {
        // message is Buffer
        console.log(`[${topic}] ${message.toString()}`);
        if (message == "1") {
            // Turn to next screen ---------------------------------------------------------------------------
        }
      });
};

    setRoomNumber = (text) => {
        this.setState({roomNumber: text, count: this.state.count + 1 });
    }

    render() {
        return (
            //  {/* create containner  */}
                <KeyboardAvoidingView
                    animation={this.state.animation}
                    behavior={Platform.OS == "ios" ? "padding" : "height"}
                    style={styles.container}>
                    {/* using image background to set wellcome background */}
                    <ImageBackground
                        style={styles.imageBackground}
                        source={imgWellcome}>
                        {/* logo IU and IoT */}
                        <Animatable.View
                            animation="fadeIn"
                            style={styles.containerLogo}
                            duration={1000}
                            delay={1600}>
                            <Image
                                source={logoIU}
                                style={styles.imgLogo}
                            />
                            <Image
                                style={styles.imgLogo}
                                source={logoIoT}
                            />
                        </Animatable.View>
                        <Animatable.View
                            animation='zoomIn'
                            duration={500}
                            delay={1000}
                            style={styles.monoImg}>
                        <Image
                            source={monoLogo}
                        />
                        </Animatable.View>
                        <Animatable.View
                            animation='fadeIn'
                            delay={3000}
                            style={styles.textInputView}>
                            <TextInput
                                onChangeText={text => this.setRoomNumber(text)}
                                keyboardType='number-pad'
                                maxLength={4}
                                placeholder={this.state.placeholder}
                                style={styles.textInput} 
                                />
                            <TouchableOpacity
                                onPress={() => {
                                    this.state.count == 4 ? Actions.chooseCharacter() : Alert.alert("Wrong Room Number");
                                    if (this.state.count == 4) {this.state.client.publish("onConnect", "1");}
                                }}
                                style={styles.btnPress}
                            >
                                <Text
                                    style={styles.TextStyle}>PRESS ME</Text>
                            </TouchableOpacity>
                        </Animatable.View>
                    </ImageBackground>
            </KeyboardAvoidingView>
        )
    }
}

//define img link
const monoLogo = require('../img/logo/monoLogo.png');
const imgWellcome = require('../img/background/backgroundImg.png');
const logoIoT = require('../img/logo/logoIoT.png');
const logoIU = require('../img/logo/IULogo.png');
const {height, width} = Dimensions.get('window');

//define css style 
const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: "column",
        alignContent: 'space-between'
    },
    imageBackground: {

        flex: 1,
        resizeMode: "cover",
        justifyContent: "center"
    },
    containerLogo: {
        display: 'flex',
        flex: 1,
        flexDirection: "row",
        // backgroundColor:'blue'
    },
    imgLogo: {
        resizeMode: 'center',
        width: 55,
        height: 55,
        marginLeft: 2,
        marginTop: 25
    },
    monoImg: {

        flex: 1.3,
        paddingLeft: width*0.03,
        // backgroundColor:'red',
        justifyContent: 'center',
        alignItems:'center'
    },
    textInputView: {
        flex: 1.6,
        // backgroundColor:'green',
        alignItems: 'center'
    },
    textInput: {
        backgroundColor: 'white',
        width: 180,
        height: 60,
        borderWidth: 1,
        borderColor: 'black',
        borderRadius: 50,
        fontSize: 25,
        color: 'black',
        textAlign: 'center'
    },
    btnPress: {
        marginTop: 15,
        borderWidth: 1,
        alignItems: 'center',
        padding: 5,
        width: 120,
        height: 40,
        borderRadius: 15,
        borderColor: "#ffd800",
        backgroundColor: '#ffd800'
    },
    TextStyle: {
        fontSize: 18,
        fontWeight: 'bold',
        fontFamily: 'Cochin'


    }
})
