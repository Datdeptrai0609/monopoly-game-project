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

import * as Animatable from 'react-native-animatable';
import { Actions } from 'react-native-router-flux';

export default class Welcome extends Component {
    constructor(props) {
        super(props);
        this.state = {
            timer: 3,// time to delay logo
            animation: ""// animation of splash screen
        }
    }

    //set waiting time for delay to move to another screen
    UNSAFE_componentWillMount() {
        this.interval = setInterval(// using set interval to delay time 
            () => {
                this.setState((prevState) => ({ timer: prevState.timer - 1 }));// setState time
                if(this.state.timer ===1) {
                    this.setState({animation: 'bounceOut'})//set animation for logo hide
                }
        console.log(this.state.timer); // log time to debug
            },
            1000 // delay 1s
        );

    }

    // after component will mount, use this to update and clear interval
    componentDidUpdate() {
        // compare delay time to 0 if == 0 then do something
        if (this.state.timer === 0) {
            // clear interval because if don't clear interval the component will forever
            clearInterval(this.interval);
            // after clear then router to Login screen
            Actions.Login();
        }
    }


    render() {
        return (
            <View
                style={styles.container}>
                <Animatable.Image
                    animation = {this.state.animation}
                    duration ={900}
                    style={styles.imgLogo}
                    source={logoImg}>
                </Animatable.Image>

                    
                
            </View>
        )
    }
}

const logoImg = require('../img/logo/logoIoT2.png');
const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'rgb(0,0,0)',
        justifyContent: 'center',
        alignItems: 'center',
    },
    imgLogo: {
        resizeMode: 'center',
        width: 500,
        height: 500,
        marginLeft: 2,
        marginTop: 25
    }
})