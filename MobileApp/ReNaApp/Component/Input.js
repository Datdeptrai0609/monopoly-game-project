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
    Dimensions,
    View
} from 'react-native';

//add animation lib
import * as Animatable from 'react-native-animatable';
import { Actions } from 'react-native-router-flux';

export default class Login extends Component {
    state = {
        placeholder: 'Game PIN',
        count: 0,
        client: mqtt.connect("ws://broker.hivemq:8000"),
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
        this.state.client.on('message', (topic, message) => {
            if (topic == String(this.props.PIN) + "/gameplayP/" + String(this.props.playerId)+"/select") {
                this.state.client.publish(String(this.props.PIN) + "/gameplayM/" + String(this.props.playerId)+"/select", this.state.input)
            }
        });
    }


    render() {
        return (
            //  {/* create containner  */}
            <View
            style = {styles.textInputView}>
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

            </View>
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
        flex: 1,
        // backgroundColor:'green',
        alignItems: 'center',
        justifyContent: 'center'
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
