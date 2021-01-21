import mqtt from 'mqtt/dist/mqtt';
import React, { Component } from 'react';
import * as Animatable from 'react-native-animatable';
import {
    SafeAreaView,
    StyleSheet,
    ScrollView,
    View,
    Text,
    StatusBar,
    ImageBackground,
    BackHandler,
    Alert,
    TouchableOpacity
} from 'react-native';

export default class GameScreen extends Component {

    constructor(props) {
        super(props);

        this.state ={ 
            choose: false,
            PIN: '',
            playerId: '',
            turn: '',
            client: mqtt.connect("ws://hcmiuiot.tech:8080")
        }
    }

    render() {
        return (
            <View style ={styles.container}>
                <Animatable.Image
                        source={wait}
                        style = {styles.cardContainer}
                >
                </Animatable.Image>
            </View>
        )
    }
}

const wait = require('../img/gameplay/alert.png');

const styles = StyleSheet.create({
    container: {
        display: 'flex',
        flex: 1,
        justifyContent:'center'
    },
    cardContainer: {
        height: 572,
        width: 310,
    },
},)