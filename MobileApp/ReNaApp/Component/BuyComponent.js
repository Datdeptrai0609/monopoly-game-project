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
    TouchableOpacity,
    Alert
} from 'react-native';

export default class GameScreen extends Component {

    constructor(props) {
        super(props);

        this.state ={ 
            choose: false,
            PIN: '',
            playerId: '',
            turn: '',
            client: mqtt.connect("ws://hcmiuiot.tech:8080")
        }

        this.state.client.on('connect', () => {
            // this.setState({ PIN: this.props.PIN, playerId: this.props.playerId });
            this.state.client.subscribe(String(this.props.PIN) + "/gameplayP/" + String(this.props.playerId)+"/buy");
            console.log(String(this.props.PIN) + "/gameplayP/" + String(this.props.playerId)+"/buy");
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
            <View
            style = {styles.box}>
                <Text
                style = {styles.text}>Your Choice?</Text>
                <View
                style = {styles.choose}>
                    <TouchableOpacity
                    style={styles.yes} onPress={() => {
                        this.state.client.publish(String(this.props.PIN) + "/gameplayM/" + String(this.props.playerId)+"/buy", "1");
                        this.state.client.publish(String(this.props.PIN) + "/gameplayM/" + String(this.props.playerId)+"/jail", "1");
                        this.state.client.publish(String(this.props.PIN) + "/gameplayM/" + String(this.props.playerId)+"/dice", "1");
                    }}>
                        <Text
                        style={styles.roll}>YES</Text>
                    </TouchableOpacity>
                    <TouchableOpacity
                        style={styles.no} onPress={() => {this.state.client.publish(String(this.props.PIN) + "/gameplayM/" + String(this.props.playerId)+"/buy", "0");
                        this.state.client.publish(String(this.props.PIN) + "/gameplayM/" + String(this.props.playerId)+"/jail", "0");
                        this.state.client.publish(String(this.props.PIN) + "/gameplayM/" + String(this.props.playerId)+"/dice", "1");
                        }}>
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
        backgroundColor: '#0AB689',
        padding: 10,
        paddingLeft: 26,
        paddingRight: 26,
        borderRadius:22
    },
    no: {
        backgroundColor: '#CD3B3E',
        padding: 10,
        paddingLeft: 33,
        paddingRight: 33,
        borderRadius: 23
    },
    box: {
        borderColor: 'white',
        backgroundColor:'white',
        padding:55,
        borderRadius:25,
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

