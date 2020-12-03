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
        ]
    }

    setStatus =() => {
        // this.state.characters.map((item) => {
        //     console.log('       ' + item);
        //     let characters = {...this.state.characters};
        //     characters.
        // })

        let character = {...this.state.characters};
        this.setState({character})
        character.map((item) => console.log(item));
       console.log(character);
    }

    render() {
        return (
            <View style={styles.playerContainer}>
                {
                    this.state.characters.map((item, index) => (
                        <TouchableOpacity
                            key={item.id}
                            onPress = {() => this.setStatus()}
                            style={item.status == true ? styles.characterBoxChoose : styles.characterBox}>
                            <Image source={item.charac}/>
                        </TouchableOpacity>
                    ))
                }
            </View>
        )
    }
}
export default List

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
    characterBoxChoose: {
        width: 120,
        height: 120,
        backgroundColor: 'white',
        margin: 10,
        borderWidth: 10,
        borderColor: 'green',
        borderRadius: 30,
        alignItems: 'center',
        justifyContent: 'center'
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