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
            <View
            style = {styles.box}>
                <Text
                style = {styles.text}>BUY THIS LAND?</Text>
                <View
                style = {styles.choose}>
                    <TouchableOpacity
                    style={styles.yes}>
                        <Text
                        style={styles.roll}>YES</Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={styles.no}>
                        <Text
                        style={styles.roll}>NO</Text>
                    </TouchableOpacity>
                </View>
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
    },
    choose: {
        display: 'flex',
        flexDirection: 'row',
        justifyContent:'space-between'
    },
    yes:{
        backgroundColor: 'green',
        padding: 10,
        paddingLeft: 30,
        paddingRight:30,
        borderRadius:10
    },
    no: {
        backgroundColor: 'red',
        padding: 10,
        paddingLeft: 30,
        paddingRight: 30,
        borderRadius: 10
    },
    box: {
        borderColor: 'white',
        backgroundColor:'white',
        padding:30,
        borderRadius:20
    },
     text: {
        fontStyle: 'italic',
        fontSize: 30,
        textAlign: 'center',
        paddingLeft: 10,
        paddingBottom: 20,
        color: 'black',
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

