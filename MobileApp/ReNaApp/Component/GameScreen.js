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
import Auto from './Auto';
import Input from './Input'
export default class GameScreen extends Component {

    state = {
      client: mqtt.connect("ws://hcmiuiot.tech:8080"),
        PIN: "",
        playerId: "",
        showHide1: true,
        showHide2: false,
        showHide3: false,
        showHide4: false,
        showHide5: false,
        showHide6: false,
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
          this.state.client.subscribe(this.state.PIN + "/gameplayP/" + this.state.playerId + "/buy", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe(this.state.PIN + "/gameplayP/" + this.state.playerId + "/chance", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe(this.state.PIN + "/gameplayM/" + this.state.playerId + "/sell", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe(this.state.PIN + "/gameplayM/" + this.state.playerId + "/festival", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe(this.state.PIN + "/gameplayM/" + this.state.playerId + "/bus", function (err) {
            if (!err) {
            }
          });
          this.state.client.subscribe(this.state.PIN + "/gameplayM/" + this.state.playerId + "/lose", function (err) {
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
              this.setState({ showHide1: false, showHide3: false, showHide4: false, showHide5: false, showHide2: true, showHide6: false});
              console.log('your turn');
            }else{
              Alert.alert('WAITING TO YOUR TURN');
              this.setState({showHide1: false, showHide3: false, showHide4: false, showHide5: false, showHide2: false, showHide6: false});
            }
        }
        if ((topic == this.state.PIN + "/gameplayP/" + this.state.playerId + "/buy")){
            if(message.toString() == "0") {
              this.setState({ showHide1: false, showHide2: false, showHide3: true, showHide4: false, showHide5: false, showHide6: false})
            }
            if(message.toString() == "1") {
              this.setState({ showHide1: false, showHide2: false, showHide3: false, showHide4: true, showHide5: false, showHide6: false })
            }
          if (message.toString() == "2") {
            this.setState({ showHide1: false, showHide2: false, showHide3: false, showHide4: false, showHide5: true, showHide6: false })
          }
        } else 
      if(topic == this.state.PIN + "/gameplayP/"+this.state.playerId+"/select" && message.toString() == "1") {
            this.setState({ showHide1: false, showHide2: false, showHide3: false, showHide4: false, showHide5: false, showHide6: true })
          }
        if((topic == this.state.PIN+"/gameplayP/"+this.state.playerId+"/chance")) {
          this.setState({ showHide1: false, showHide2: false, showHide3: false, showHide4: false, showHide5: true, showHide6: false })
        }
      if ((topic == this.state.PIN + "/gameplayP/" + this.state.playerId + "/jail")) {
        this.setState({ showHide1: false, showHide2: false, showHide3: true, showHide4: false, showHide5: false, showHide6: false })
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
              <Roll
                PIN={this.state.PIN}
                playerId={this.state.playerId}
              />}
            {this.state.showHide3 &&
              <Buy
                PIN={this.state.PIN}
                playerId={this.state.playerId}
              />}
            {this.state.showHide4 &&
              <Buy
                PIN={this.state.PIN}
                playerId={this.state.playerId}
              />}
            {this.state.showHide5 &&
              <Auto
                PIN={this.state.PIN}
                playerId={this.state.playerId}
              />}
            {this.state.showHide6 &&
              <Input
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