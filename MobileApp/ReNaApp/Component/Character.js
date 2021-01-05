import React, { Component } from 'react'
import { Text, View, TouchableOpacity, StyleSheet,Image, ScrollView } from 'react-native'

class Character extends Component {
    state = {
        characters: [//status of each character
            {
                id: 1,
                name: 'Mina',
                status: false,
                charac: require('../img/imgCharacter/charac1.png'),
            },
            {
                id: 2,
                name: 'Eng Veit',
                status: false,
                charac: require('../img/imgCharacter/charac2.png'),
            },
            {
                id: 3,
                name: 'Mei Mei',
                status: false,
                charac: require('../img/imgCharacter/charac3.png'),
            },
            {
                id: 4,
                name: 'Laughing',
                status: false,
                charac: require('../img/imgCharacter/charac4.png'),
            },
            {
                id: 5,
                name: 'Kuerl',
                status: false,
                charac: require('../img/imgCharacter/charac5.png'),
            },
            {
                id: 6,
                name: 'Tei Tei',
                status: false,
                charac: require('../img/imgCharacter/charac6.png'),
            },
            
        ],
        PINNN: '0',
    }

    constructor(props) {
        super(props);
        
    }

    // pass data from child to parent
    sendBtnStatus = (id) => {
        var data = {
            check: false,
            Id: id
        };
        this.props.sendData(data);
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
                            onPress = {() => {
                                this.setStatus(item.id); 
                                this.sendBtnStatus(item.id);
                            }}
                            style={item.status == true ? styles.characterBoxChose : styles.characterBox}>
                            <View style={styles.characContainer}><Image source={item.charac} /></View>
                            <Text style={styles.nameCharacter}>{item.name}</Text>
                        </TouchableOpacity>
                    ))
                }
            </View>
        )
    }
}
export default Character

const buttonStatus = false;

const styles = StyleSheet.create({
    test: {
        flex:1,
        backgroundColor:'blue'
    },
    characterBox: {
        width: 142,
        height: 150,
        backgroundColor: 'white',
        margin: 4,
        borderRadius: 20,
        alignItems: 'center',
        justifyContent: 'center'
    },
    characterBoxChose: {
        width: 142,
        height: 150,
        backgroundColor: 'white',
        margin: 4,
        borderWidth: 5,
        borderRadius: 20,
        alignItems: 'center',
        justifyContent: 'center',
        borderColor: 'red'

    },
    playerContainer: {
        width: 300,
        height: 400,
        flex: 4,
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'center',
        alignContent: 'center',
    },
    characContainer: {
        paddingTop:10,
        flex:3,
    },
    nameCharacter: {
        flex:0.8,
        fontSize: 14,
        fontWeight:'bold'
        // width: 100,
        // backgroundColor: 'red'
    }

})