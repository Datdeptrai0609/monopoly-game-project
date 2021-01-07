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
        count: 0,
        client: mqtt.connect("ws://hcmiuiot.tech:8080"),
        input: ''
    }

    constructor(props) {
        super(props);
        this.state.client.on('connect', () => {
            // connect to broker
            console.log('connected');
        });

        // Handle comming msg:
        this.state.client.on('message', (topic, message) => {
            // message is Buffer
            console.log(`[${topic}] ${message.toString()}`);
        });
    };

    setRoomNumber = (text) => {
        this.setState({ input: text });
    }
    //when press button
    onPressBtn = () => {
       
    }


    render() {
        return (
            //  {/* create containner  */}
            
                    <Animatable.View
                        animation='fadeIn'
                        delay={3000}
                        style={styles.textInputView}>
                        <TextInput
                            onChangeText={text => this.setRoomNumber(text)}
                            value={this.state.PIN}
                            keyboardType='number-pad'
                            maxLength={4}
                            placeholder={this.state.placeholder}
                            style={styles.textInput}
                        />
                        <TouchableOpacity
                            onPress={() => {
                               this.onPressBtn();
                            }}
                            style={styles.btnPress}
                        >
                            <Text
                                style={styles.TextStyle}>PRESS ME</Text>
                        </TouchableOpacity>
                    </Animatable.View>
        )
    }
}

const { height, width } = Dimensions.get('window');

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
        paddingLeft: width * 0.03,
        // backgroundColor:'red',
        justifyContent: 'center',
        alignItems: 'center'
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
