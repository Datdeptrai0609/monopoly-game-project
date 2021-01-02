import mqtt from 'mqtt/dist/mqtt';
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
    Dimensions,
    BackHandler,
    Alert
} from 'react-native';

import {
    Header,
    LearnMoreLinks,
    Colors,
    DebugInstructions,
    ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

export default class GameScreen extends Component {

    state = {
        client: mqtt.connect("ws://hcmiuiot.tech:8080"),
        diceComming: "pin/gameplay/playerid",
        buyComming: "pin/gameplay/playerid/buy",
        payComming: "pin/gameplay/playerid/sell",
        fesComming: "pin/gameplay/playerid/festival",
        busComming: "pin/gameplay/playerid/bus",
        jailComming: "pin/gameplay/playerid/jail",
        loseComming: "pin/gameplay/playerid/lose",
        dicePub: "pin/gameplay/playerid/dice",
        buyPub: "pin/gameplay/playerid/buy",
        payPub: "pin/gameplay/playerid/sell",
        fesPub: "pin/gameplay/playerid/festival",
        busPub: "pin/gameplay/playerid/bus",

    }

    constructor(props) {
        super(props);
        this.state.client.on('connect', () => {
            // Handle PIN!
          this.state.client.subscribe("pin/gameplay/playerid", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe("pin/gameplay/playerid/buy", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe("pin/gameplay/playerid/sell", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe("pin/gameplay/playerid/festival", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe("pin/gameplay/playerid/bus", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe("pin/gameplay/playerid/jail", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe("pin/gameplay/playerid/lose", function (err) {
            if (!err) {
            }
          });
    });

    // Handle comming msg:
    this.state.client.on('message', (topic, message) => {
        // message is Buffer
        console.log(`[${topic}] ${message.toString()}`);
        if (message == "1") {
            //Hanlde Alert!
        }
      });
    };

    render() {
        return (
            <ImageBackground
                style = { styles.container}
                source ={gameImageBackground}>

            </ImageBackground>
        )
    }
}

const gameImageBackground = require('../img/background/playBackground.jpg');
const styles = StyleSheet.create ({
    container: {
        flex:1,
        resizeMode:'cover',
        justifyContent:'center',
        alignItems:'center',
    }
})