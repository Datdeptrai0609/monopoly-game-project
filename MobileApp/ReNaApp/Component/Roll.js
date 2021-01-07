import mqtt from 'mqtt/dist/mqtt';
import React, { Component } from 'react';
import {
    SafeAreaView,
    StyleSheet,
    ScrollView,
    View,
    Text,
    TouchableOpacity,
    StatusBar,
    ImageBackground,
    BackHandler,
    Alert
} from 'react-native';

export default class GameScreen extends Component {


    constructor(props) {
        super(props);

        this.state ={ 
            PIN: '',
            playerId: '',
            turn: '',
            client: mqtt.connect("ws://broker.hivemq:8000")
        }

        this.state.client.on('connect', () => {
            // this.setState({ PIN: this.props.PIN, playerId: this.props.playerId });
            this.state.client.subscribe(String(this.props.PIN) + "/gameplayP/" + String(this.props.playerId)+"/dice");
            console.log(String(this.props.PIN) + "/gameplayP/" + String(this.props.playerId)+"/dice");
        });
        this.state.client.on('message', (topic, message) => {
            // message is Buffer
            console.log(`[${topic}] ${message.toString()}`); 
            //
          });
    }

    send = () => {
        this.state.client.publish(String(this.props.PIN) + "/gameplayM/" + String(this.props.playerId)+"/dice","1");
        console.log(this.props.PIN);
        console.log(this.props.playerId);

        //handle
    }
    render() {
        return (

            <View
                // {/* <Text
                // style = {styles.text}>PRESS TO ROLL A DICE</Text> */}
                // {/* <View> */}
                style = {styles.container}>
                    
                    <TouchableOpacity
                        style={styles.btn}
                        onPress = {() => this.send()}>
                        <View>

                        <Text
                        style = {styles.roll}>ROLL</Text>
                        </View>
                    </TouchableOpacity>
                {/* </View> */}
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
        marginLeft:30,
        borderWidth: 4,
        borderColor: 'yellow'
    },
    roll: {
        color:'white',
        fontWeight:'bold',
        fontSize: 20
    }
},
)
