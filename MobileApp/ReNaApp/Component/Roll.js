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


    send = () => {
        
    }
    render() {
        return (
            <View>
                <Text
                style = {styles.text}>PRESS TO ROLL A DICE</Text>
                <View
                style = {styles.btn}>
                    <TouchableOpacity
                        onPress = {() => this.send()}>
                        <Text
                        style = {styles.roll}>ROLL</Text>
                    </TouchableOpacity>

                </View>
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
    }, text: {
        fontStyle: 'italic',
        fontSize: 20,
        textAlign: 'center',
        paddingLeft: 10,
        paddingBottom: 20,
        color: 'black',
        fontWeight: 'bold'
    },
    btn: {
        width: 180,
        height:180,
        borderRadius: 100,
        alignContent: 'center',
        justifyContent: 'center',
        alignItems:'center',
        backgroundColor: 'red',
        marginLeft:30
    },
    roll: {
        color:'white',
        fontWeight:'bold',
        fontSize: 20
    }
},
)
