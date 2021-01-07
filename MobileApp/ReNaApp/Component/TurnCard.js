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
    constructor(props) {
        super(props);

        this.state ={ 
            choose: false,
            PIN: '',
            playerId: '',
            turn: '',
            client: mqtt.connect("ws://broker.hivemq.com:8000")
        }

        this.state.client.on('connect', () => {
            // this.setState({ PIN: this.props.PIN, playerId: this.props.playerId });
            this.state.client.subscribe(String(this.props.PIN) + "/connect/order/" + String(this.props.playerId));
            console.log(String(this.props.PIN) + "/connect/order/" + String(this.props.playerId));
        });
        this.state.client.on('message', (topic, message) => {
            // message is Buffer
            console.log(`[${topic}] ${message.toString()}`); 
            if (message.toString() == '1') {
                choose = require(`../img/background/TurnCard1Chose.png`)
                disable = true;
                this.state.client.publish(String(this.props.PIN) + "/turn/confirm", String(this.props.playerId));
                this.state.client.unsubscribe(String(this.props.PIN) + "/connect/order/" + String(this.props.playerId));
            }
            if (message.toString() == '2') {
                choose = require(`../img/background/TurnCard2Chose.png`)
                disable = true;
                this.state.client.publish(String(this.props.PIN) + "/turn/confirm", String(this.props.playerId));
                this.state.client.unsubscribe(String(this.props.PIN) + "/connect/order/" + String(this.props.playerId));
            }
            if (message.toString() == '3') {
                choose = require(`../img/background/TurnCard3Chose.png`)
                disable = true;
                this.state.client.publish(String(this.props.PIN) + "/turn/confirm", String(this.props.playerId));
                this.state.client.unsubscribe(String(this.props.PIN) + "/connect/order/" + String(this.props.playerId));
            }
            if (message.toString() == '4') {
                choose = require(`../img/background/TurnCard4Chose.png`)
                disable = true;
                this.state.client.publish(String(this.props.PIN) + "/turn/confirm", String(this.props.playerId));
                this.state.client.unsubscribe(String(this.props.PIN) + "/connect/order/" + String(this.props.playerId));
            }
          });
    }



    log = () => {
        console.log("playerId:" + this.props.playerId);
        console.log("PIN:" +this.props.PIN);
            this.state.client.subscribe(String(this.props.PIN) + "/connect/order/" + String(this.props.playerId));
            console.log(String(this.props.PIN) + "/connect/order/" + String(this.props.playerId));
    }

    render() {
        return (
        <View
            style ={styles.container}>
                <Text
                    style={styles.text}>
                   L E T   S E E     Y O U R    T U R N !
                </Text>
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
    text: {
        fontStyle: 'italic',
        fontSize: 20,
        textAlign:'center',
        paddingLeft:10,
        paddingBottom:20,
        color: 'black',
        fontWeight: 'bold'
    },
})