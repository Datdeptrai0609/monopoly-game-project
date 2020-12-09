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
            timer: 3,
            animation: ""
        }
    }

    UNSAFE_componentWillMount() {
        this.interval = setInterval(
            () => {
                this.setState((prevState) => ({ timer: prevState.timer - 1 }));
                if(this.state.timer ===1) {
                    this.setState({animation: 'bounceOut'})
                }
        console.log(this.state.timer);
            },
            1000
        );

    }

    componentDidUpdate() {
        if (this.state.timer === 0) {
            clearInterval(this.interval);
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