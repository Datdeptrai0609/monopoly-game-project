# USAGE (Command Line)

Firstly, move to folder monopoly then compile all Java files by the command: 
```bash
javac -cp "../lib/*;." -d ../out  *.java
```
Next, move to the directory out which we saved all class files in:
```bash
cd ..\out
```
Finally, run the program by the command:
```bash
java -cp "../lib/*;." monopoly.Monopoly
```
Or, you can use any IDE (IntelliJ, Eclipse, etc) to run the game for convenience.
