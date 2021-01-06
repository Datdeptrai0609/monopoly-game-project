import mqtt, { log } from 'mqtt/dist/mqtt';
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

import { Actions } from 'react-native-router-flux';

export default class Waiting extends Component {
    state= {
        show: false,// status show or hide waiting GIF
        move:false,//status for rout to next screen
        client: mqtt.connect("ws://hcmiuiot.tech:8080"),
        PIN:'',
    }

    constructor(props) {
        super(props);
        this.state.client.on('connect', () => {
            // Handle PIN!
            console.log('character connected');
            this.setState({ PIN: this.props.PIN });
            console.log(this.state.PIN);
            this.state.client.subscribe(this.state.PIN + "/connect/ready", (err) => {
                if(!err) {
                }
            })
        })
    }

    UNSAFE_componentWillMount() {
        this.interval = setInterval(() => {
            this.setState({show : true})//show GIF after 1.5s
        },1500);
        this.state.client.on('message', (topic, message) => {
            // message is Buffer
            console.log(`[${topic}] ${message.toString()}`);
            // Handle 6 btn: -------------------------------------------------------------------------------
            if (message.toString() == "1") {
                Actions.GameScreen();
            }
            this.state.client.unsubscribe(this.state.PIN + "/connect/ready");
        });
                 // move to gameScreen if status of move is true
    }

    componentDidUpdate() {
        if (this.state.show == true) {// if set state for show done, clear it
            clearInterval(this.interval)
        }
        if(this.state.move == true) {//if moved to next screen, clear it
            clearInterval(this.intervalMove)
        }
        
    }

    render() {
        return (
            <View
                style ={styles.container}>
            <View
                style={this.state.show ? styles.waiting : styles.hide}>
                <Image
                    source={require('../img/background/loading.gif')}
                />
            </View>
            </View>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        backgroundColor:'#6E1CE9',
        flex:1,
    },
    waiting: {
        display: 'flex',
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    hide: {
        display: 'none'
    }
})