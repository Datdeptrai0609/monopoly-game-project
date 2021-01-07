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
            client: mqtt.connect("ws://broker.hivemq:8000")
        }

        this.state.client.on('connect', () => {
            // this.setState({ PIN: this.props.PIN, playerId: this.props.playerId });
            this.state.client.subscribe(String(this.props.PIN) + "/gameplayP/" + String(this.props.playerId)+"/bus");
            this.state.client.subscribe(String(this.props.PIN) + "/gameplayP/" + String(this.props.playerId)+"/festival");
            this.state.client.subscribe(String(this.props.PIN) + "/gameplayP/" + String(this.props.playerId)+"/jail");
            this.state.client.subscribe(String(this.props.PIN) + "/gameplayP/" + String(this.props.playerId)+"/sell");
        });
        this.state.client.on('message', (topic, message) => {
            // message is Buffer
            console.log(`[${topic}] ${message.toString()}`); 
            //
          });
    }

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