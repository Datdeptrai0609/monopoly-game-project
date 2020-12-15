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
    }

    UNSAFE_componentWillMount() {
        this.interval = setInterval(() => {
            this.setState({show : true})
            },1500);
        
    }

    componentDidUpdate() {
        if (this.state.show == true) {
            clearInterval(this.interval)
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