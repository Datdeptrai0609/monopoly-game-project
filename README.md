# :fire: MONOPOLY EXTRA PLUSUBTRACT :fire: 

![Java CI](https://github.com/Laughing6901/monopoly-game-project/workflows/Java%20CI/badge.svg) ![react-native-android-build-apk](https://github.com/Laughing6901/monopoly-game-project/workflows/react-native-android-build-apk/badge.svg)

## Summary

[MONOPOLY PLUSUBTRACT](https://en.wikipedia.org/wiki/Monopoly_(game)) is a project to make a game about the billionaire chess. This project is for Object-Oriented Programing. This game have two part. Part one is Desktop Application to show the game. Part two is Mobile Application to control the game. Using [ReactNative](https://reactnative.dev/), which is Framework of Javascript, to make Mobile Application. Using [LibGDX](https://libgdx.badlogicgames.com/), which is Framwork of Java, to make Desktop Application. 


## Installation

You can install this game with git.

    git clone https://github.com/Laughing6901/monopoly-game-project.git

## Usage

### Launch Java App

To play the game, you must launch core of the game on a desktop.

Firstly, move to the folder which contain core game by the command:

    cd JavaApp/PlayScreen
   
Then, run the game by using gradle:

    ./gradlew desktop:run
    
### Troubleshooting

If you don't have permission to run ./gradlew, try this command before run gradlew:
    
    chmod +x gradlew
    
In windows, you maybe cannot use the command. Try:

    .\gradle desktop:run

Next step, move to another devices to launch mobile app. Have fun with our Monopoly Plusubtract !!! :smirk_cat:
  
### Launch Mobile App

To connect and handling the game, you must launch Mobile application in Mobile App folder. In terminal, you can do this:

    cd MobileApp

Then you see two folder ReNaApp and monopoly design


After that:

    cd ReNaApp

Finally, you can run mobile app

    react-native run android

or: 

    npm android 

## More Information

You can find diagrams, documents and some information in folder Report. This folder contains all our documents for this game. If you want to understand our code more detailed, you should go to here.
