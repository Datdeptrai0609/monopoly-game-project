import mqtt from 'mqtt/dist/mqtt';
import React, { Component } from 'react';
import {
    SafeAreaView,
    StyleSheet,
    ScrollView,
    View,
    Text,
    StatusBar,
    ImageBackground,
    BackHandler,
    Alert
} from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';

export default class GameScreen extends Component {
    render() {
        return (
            <View
                style = {styles.container}>
                <TouchableOpacity
                style = {styles.yes}>
                    <Text
                    style = {styles.text}>OK</Text>
                </TouchableOpacity>
            </View>
        )
    }
}


const styles = StyleSheet.create({
    container: {
        flex: 1,
        resizeMode: 'cover',
        justifyContent: 'center',
        alignItems: 'center',
    },
    choose: {
        display: 'flex',
        flexDirection: 'row',
        justifyContent: 'space-between'
    },
    yes: {
        backgroundColor: 'green',
        padding: 30,
        paddingLeft: 50,
        paddingRight: 50,
        borderRadius: 20
    },
    
    text: {
        fontStyle: 'italic',
        fontSize: 30,
        textAlign: 'center',
        paddingLeft: 10,
        paddingBottom: 20,
        color: 'white',
        fontWeight: 'bold'
    },
    btn: {
        width: 180,
        height: 180,
        borderRadius: 100,
        alignContent: 'center',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'red',
        marginLeft: 30
    },
    roll: {
        color: 'white',
        fontWeight: 'bold',
        fontSize: 20
    }
},
)