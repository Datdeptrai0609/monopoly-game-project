import React, { Component } from 'react'
import { Text, View, TouchableOpacity, StyleSheet,Image } from 'react-native'

class List extends Component {
    state = {
        characters: [
            {
                id: 0,
                status: false,
                charac: require('../img/imgCharacter/charac1.png')
            },
            {
                id: 1,
                status: false,
                charac: require('../img/imgCharacter/charac2.png')
            },
            {
                id: 2,
                status: false,
                charac: require('../img/imgCharacter/charac3.png')
            },
            {
                id: 3,
                status: false,
                charac: require('../img/imgCharacter/charac4.png')
            },
            {
                id: 4,
                status: false,
                charac: require('../img/imgCharacter/charac5.png')
            },
            {
                id: 5,
                status: false,
                charac: require('../img/imgCharacter/charac6.png')
            }
        ],
    }

    sendData = () => {
        this.props.setBtnsStatus('true');
    }

    setStatus =(index) => {
        let character = [...this.state.characters];//clone characters
        //set status for each item which chose in clone variable
        character.map((item) => {
            (item.id == index) ? item.status = true : item.status = false;
        });
        // update for state
        this.setState({character});
    }

    render() {
        return (
            //return design character
            <View style={styles.playerContainer}>
                {
                    this.state.characters.map((item, index) => (
                        <TouchableOpacity
                            key={item.id}
                            onPress = {() => this.setStatus(item.id)}
                            style={item.status == true ? styles.characterBoxChose : styles.characterBox}>
                            <Image source={item.charac}/>
                        </TouchableOpacity>
                    ))
                }
            </View>
        )
    }
}
export default List

const buttonStatus = false;

const styles = StyleSheet.create({
    characterBox: {
        width: 120,
        height: 120,
        backgroundColor: 'white',
        margin: 10,
        borderRadius: 30,
        
        alignItems: 'center',
        justifyContent: 'center'
    },
    characterBoxChose: {
        width: 120,
        height: 120,
        backgroundColor: 'gray',
        margin: 10,
        borderWidth: 10,
        borderColor: 'green',
        borderRadius: 30,
        alignItems: 'center',
        justifyContent: 'center',

    },
    playerContainer: {
        width: 300,
        height: 400,
        flex: 3,
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'center',
        alignContent: 'center',
        backgroundColor: 'rgba(0,0,0,0.4)'
    },
})