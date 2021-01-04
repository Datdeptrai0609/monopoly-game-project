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
    }

    UNSAFE_componentWillMount() {
        this.interval = setInterval(() => {
            this.setState({show : true})//show GIF after 1.5s
        },1500);
        this.intervalMove = setInterval(() => {
            this.setState({move: true}) //set state move to true after 1.5s
            if (this.state.move == true) {
                Actions.GameScreen(); // move to gameScreen if status of move is true
            }
        },3500);
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