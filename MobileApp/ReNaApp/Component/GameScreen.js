import mqtt from 'mqtt/dist/mqtt';
import React, { Component } from 'react';
import {
    StyleSheet,
    ImageBackground,
} from 'react-native';

import TurnCard from './TurnCard';

export default class GameScreen extends Component {

    state = {
        client: mqtt.connect("ws://hcmiuiot.tech:8080"),
        PIN: "",
        playerId: "",
    }

    //////////////////////////////// TOPIC SUBCRIBE AREA //////////////////////////////////////

    constructor(props) {
        super(props);
        this.state.client.on('connect', () => {
            // Handle PIN!
          this.state.client.subscribe(this.state.PIN + "gameplayM/" + this.state.playerId, function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe(this.state.PIN + "gameplayM/" + this.state.playerId + "buy", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe(this.state.PIN + "gameplayM/" + this.state.playerId + "sell", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe(this.state.PIN + "gameplayM/" + this.state.playerId + "festival", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe(this.state.PIN + "gameplayM/" + this.state.playerId + "bus", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe(this.state.PIN + "gameplayM/" + this.state.playerId + "bus", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe(this.state.PIN + "gameplayM/" + this.state.playerId + "lose", function (err) {
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
        // if topic == -> check msg, if msg == -> hanlde alert, publish message
      });
    };

    render() {
        return (
            <ImageBackground
                style = { styles.container}
                source ={gameImageBackground}>
                <TurnCard/>
                
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