import mqtt, { log } from 'mqtt/dist/mqtt';
import React, { Component } from 'react';
import {
    StyleSheet,
    View,
    Text,
    ImageBackground,
    TouchableOpacity,
    Dimensions,
} from 'react-native';

import * as Animatable from 'react-native-animatable';
import Character from './Character';
import {  Actions } from 'react-native-router-flux';

export default class ChooseCharacter extends Component {
        state = {
            btnStatus: true,// show/hide status of btn ready
            logoAnimation: 'zoomIn',// set animation for logo
            characterChooseAnimation: 'fadeIn', // set animation for choose character
            waitingAnimation: 'zoomIn',//animation for waiting image GIF
            move:0,
            client: mqtt.connect("ws://hcmiuiot.tech:8080"),
            PIN: '',
            playerId: '',
            click: 0
        }
    
        constructor(props) {
            super(props);
            this.state.client.on('connect', () => {
                // Handle PIN!
                console.log('character connected');
                this.setState({PIN: this.props.PIN});
              this.state.client.subscribe(this.state.PIN+"/connect/order", function (err) {
                if (!err) {
                }
              });
              this.state.client.subscribe(this.state.PIN+"/connect/ready", function (err) {
                if (!err) {
                }
              });
        });

        // Handle comming msg:
        this.state.client.on('message', (topic, message) => {
            // message is Buffer
            console.log(`[${topic}] ${message.toString()}`);
            // Handle 6 btn: -------------------------------------------------------------------------------
            if (topic = this.state.PIN+"/connect/order" && message == "1") {
                // Handle arrived MSG: To disable button.
            }
            if (topic = this.state.PIN+"/connect/ready" && message == "1") {
                // Handle turn to next screen.
            }
          });
    };

    // using props to set status for btn from child class
    setBtnStatus = (child) => {
        this.setState({ btnStatus: child.check,playerId:child.Id });
        console.log(child);
    }

    //send id to mqtt
    sendMqtt = () => {
        //fail here
        this.state.client.publish(`${this.state.PIN}/playerid`, this.state.playerId+"") // Id: 1 -> 6:
        console.log('sent');
        this.setState({click: 1})
    }

    //when onPress "Ready" button to set status for choose view and waiting view
    render() {
        return (
            // add background img view
            <View
                style={styles.container}>
                <ImageBackground
                    source={imgBackground}
                    style={styles.imageBackground}>

                    {/* add and design choose character box */}
                    <View
                        style={styles.chooseContainer}>

                        <Animatable.View
                            animation={this.state.characterChooseAnimation}
                            delay={1000}
                            duration={2000}
                            style={styles.windowsChoose}>
                            <Text
                                style={styles.text}>
                                P i c k    Y o u r    C h a r a c t e r !
                        </Text>

                            {/* render each of character to choose and set status for btn by using 
                            props in character.js */}
                            <Character 
                                sendData={this.setBtnStatus}
                                 
                                />{/*receive data from child and set btn state*/}

                            {/* btn ready view */}
                            <View
                                style={styles.readyContainer}>

                                <TouchableOpacity
                                    disabled={this.state.click == 1 ? true : this.state.btnStatus}
                                    //if disable is true then the button is off 
                                    style={(this.state.btnStatus || this.state.click ==1) ? styles.buttonOff : styles.buttonOn}
                                    onPress ={
                                        () => {
                                            this.sendMqtt();
                                            console.log(this.state.click);
                                        }
                                    }>

                                    <Text style={styles.textReady}>
                                        READY
                                    </Text>

                                </TouchableOpacity>

                            </View>
                        </Animatable.View>

                        {/* add logo monopoly */}

                        <Animatable.Image
                            animation={this.state.logoAnimation}
                            delay={100}
                            duration={1000}
                            source={logoMonoSmall}
                            style={styles.logoImg}>
                        </Animatable.Image>

                    </View>
                </ImageBackground>

            </View>
        )
    }

}


const { height, width } = Dimensions.get('window');
const logoMonoSmall = require('../img/logo/monoLogoSmall.png');
const imgBackground = require('../img/background/backgroundImgBlur.png');

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: "column",
    },
    chooseContainer: {
        display: 'flex',
        alignItems: 'center'
    },

    imageBackground: {
        flex: 1,
        resizeMode: "cover",
        alignItems: 'center',
    },
    logoImg: {
        position: 'absolute',
        marginTop: 45
    },
    windowsChoose: {

        marginTop: 93,
        position: 'absolute',
        width: width * 7 / 8,
        height: height * 0.82,
        backgroundColor: 'rgba(0,0,0,0.5)',
        borderWidth: 4,
        borderRadius: 10,
        borderColor: '#6E1CE9',
        alignItems: 'center'
    },

    readyContainer: {
        flex: 1,
        width: 300,
        height: 400,
        justifyContent: 'center',
        alignItems: 'center'
    },
    text: {
        flex: 0.5,
        fontStyle: 'italic',
        fontSize: 20,
        paddingTop: 40,
        color: 'white',
        fontWeight: 'bold'
    },
    buttonOn: {
        width: 190,
        height: 70,
        backgroundColor: '#00ff48',
        borderRadius: 18,

    },
    buttonOff: {
        width: 190,
        height: 70,
        backgroundColor: 'gray',
        borderRadius: 18,
    },
    textReady: {
        color: 'white',
        fontSize: 50,
        fontWeight: 'bold',
        textAlign: 'center'
    },
})