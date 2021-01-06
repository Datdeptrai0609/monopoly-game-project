import mqtt from 'mqtt/dist/mqtt';
import React, { Component } from 'react';
import {
    StyleSheet,
    View,
    TouchableOpacity
} from 'react-native';

import * as Animatable from 'react-native-animatable';

export default class TurnCard extends Component {
    state ={ 
        card: [
        {
            id:0,
            turn: 0,
            notChoose: notChoose,
            chose:choose,
            animation: '',
            status:false,
        },
        {
            id: 1,
            turn: 0,
            notChoose: notChoose,
            chose: choose,
            animation: '',
            status: false,
        },
        {
            id: 2,
            turn: 0,
            notChoose: notChoose,
            chose: choose,
            animation: '',
            status: false,
        },
        {
            id: 3,
            turn: 0,
            notChoose: notChoose,
            chose: choose,
            animation: '',
            status: false,
        },
    ],
    client:  mqtt.connect("ws://hcmiuiot.tech:8080"),
    PIN: ""
    }

    constructor(props) {
        super(props);
        this.state.client.on('connect', () => {
            this.state.client.subscribe(this.state.PIN+"connect/order");
        });
        this.state.client.on('message', (topic, message) => {
            // message is Buffer
            console.log(`[${topic}] ${message.toString()}`);
            // Handle turnCard?
          });
    }

    render() {
        return (
        <View
            style ={styles.container}>

           <Animatable.View
                animation ={animaIn}
                style = {styles.cardContainer}>
                {this.state.card.map((item,index) => (
                    <TouchableOpacity
                        key={item.id}>
                        <Animatable.Image
                            animation = {item.animation}
                            source ={item.status? item.chose:item.notChoose}
                            style = {styles.card}>

                        </Animatable.Image>
                    </TouchableOpacity>
                ))}
           </Animatable.View>
        </View>
        )
    }
}

const animaIn = 'fadeIn';
const notChoose = require('../img/background/TurnCard.png');
const choose = require('../img/background/TurnCardChose.png');

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
    }
})