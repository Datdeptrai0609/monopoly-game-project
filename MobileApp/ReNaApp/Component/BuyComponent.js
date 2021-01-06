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
            <View>
                <Text>BUY THIS HOUSE?</Text>
                <TouchableOpacity>
                    <Text>YES</Text>
                </TouchableOpacity>
                <TouchableOpacity>
                    <Text>NO</Text>
                </TouchableOpacity>
            </View>
        )
    }
}
