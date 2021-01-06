import mqtt, { log } from 'mqtt/dist/mqtt';
import React, { Component } from 'react';
import {
    StyleSheet,
    View,
    Text,
    TouchableOpacity,
    ImageBackground
} from 'react-native';

import * as Animatable from 'react-native-animatable';

export default class TurnCard extends Component {
    state ={ 
    choose: false,
    client:  mqtt.connect("ws://hcmiuiot.tech:8080"),
    PIN:'',
    playerId:'',
    turn:''
    }

    constructor(props) {
        super(props);
        this.state.client.on('connect', () => {
            this.setState({ PIN: this.props.PIN, playerId: this.props.playerId });
            this.state.client.subscribe(this.state.PIN+"/connect/order");
        });
        this.state.client.on('message', (topic, message) => {
            // message is Buffer
            console.log(`[${topic}] ${message.toString()}`); 
            if (message.toString() == '1') {
                choose = require(`../img/background/TurnCard1Chose.png`)
                disable = true;
                this.state.client.publish(this.state.PIN + "/turn/confirm", "1");

            }
            if (message.toString() == '2') {
                choose = require(`../img/background/TurnCard2Chose.png`)
                disable = true;
                this.state.client.publish(this.state.PIN + "/turn/confirm", "1");
            }
            if (message.toString() == '3') {
                choose = require(`../img/background/TurnCard3Chose.png`)
                disable = true;
                this.state.client.publish(this.state.PIN + "/turn/confirm", "1");
            }
            if (message.toString() == '4') {
                choose = require(`../img/background/TurnCard4Chose.png`)
                disable = true;
                    

            }
          });
    }



    log = () => {
        console.log("playerId:" + this.state.playerId);
        console.log("PIN:" +this.state.PIN);
        // if(playerId == turn)
    }

    render() {
        return (
        <View
            style ={styles.container}>
        <TouchableOpacity
            disabled = {disable}
            onPress = {() => {
                this.setState({choose: true});
            this.log();
            }}>  
           <Animatable.Image
                source={this.state.choose? choose: notChoose}
                animation ={animaIn}
                style = {styles.cardContainer}>
           </Animatable.Image>
           
        </TouchableOpacity>
        </View>
        )
    }
}

const animaIn = 'fadeIn';
var disable = false;
const notChoose = require('../img/background/TurnCard.png');
var choose = require(`../img/background/TurnCardChose.png`);
const styles = StyleSheet.create({
    cardContainer: {
        justifyContent:'space-between',
        flexDirection:'row',
        flexWrap:'wrap',
    },
    container: {
        display: 'flex',
        flex: 1,
        justifyContent:'center'
    },
    display: {
    }
})