import mqtt from 'mqtt/dist/mqtt';
import React, { Component } from 'react';
import {
    StyleSheet,
    ImageBackground,
    Alert,
} from 'react-native';

import TurnCard from './TurnCard';
import Roll from './Roll';
import Buy from './BuyComponent';
export default class GameScreen extends Component {

    state = {
        client: mqtt.connect("ws://hcmiuiot.tech:8080"),
        PIN: "",
        playerId: "",
        showHide1: true,
        showHide2: false,
        showHide3: false,
        status:""
    }

    //////////////////////////////// TOPIC SUBCRIBE AREA //////////////////////////////////////

    constructor(props) {
        super(props);

        this.state.client.on('connect', () => {
            // Handle PIN!
            console.log('connected');
          this.setState({ PIN: this.props.PIN, playerId: this.props.playerId })

          this.state.client.subscribe(this.state.PIN + "/gameplayP/turn", function (err) {
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
        if (topic == this.state.PIN+"/gameplayP/turn") {
            if (message.toString() == this.props.playerId) {
              this.setState({showHide1: false, showHide2: true});
              console.log('your turn');
            }else{
              this.setState({showHide1: false});
              Alert.alert('WAITING TO YOUR TURN');
            }
        }
        // if topic == -> check msg, if msg == -> hanlde alert, publish message
      });
    };

    render() {
        return (
            <ImageBackground
                style = {styles.container}
                source ={gameImageBackground}>
                {this.state.showHide1 &&
                <TurnCard
                  PIN={this.state.PIN}
                  playerId= {this.state.playerId}
                />}
              {this.state.showHide2 &&
              <Buy
                PIN={this.state.PIN}
                playerId={this.state.playerId}
              />}
                
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
  },
)