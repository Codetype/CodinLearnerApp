# Codin Learner
## Installation and configuration

To run project you need Java Virtual Machine and actual Gradle version on your computer.

Firstly clone repository or download zip file with Project.

If you want to use one of Java IDE choose option Import Project > Gradle Project and click in file build.gradle.

In case of usage terminal, go to root Project directory:
```
cd <repository-directory-name>
```
To show all tasks type:
```
gradle tasks
```
Then build gradle Project:
```
gradle build
```
And in the end, run Java Application:
```
gradle run 
```
To run specific task type:
```
gradle <task-name>
```



## Basic Use

Program contains a simple Command Interpreter

To go forward type:
```
go <numbers of steps>
```
For example:
```
go 10
```

To turn left type:
```
left
```

To turn right type:
```
right
```

## More complex commands
Now, it is possible to write some more complicated expression.

For example:
```
go 2 left
``` 

### Loops
To run loop type:
```
repeat 2 [ go 2 left ]
```

### Procedures
There is posibility to write simple procedure:
```
begin line :length
go length
end
```
Execute by:
```
line 2
```
Or procedures with many arguments:
```
begin regular :n :side :angle
repeat n [go side left angle]
end
```
And then called this procedure by another:
```
begin mySquare :side
regular 4 side 90
end
```
```
mySquare 2
```
```
begin myTriangle :side
regular 3 side 120
end
```
```
myTriangle 1
```
You can also call embedded procedures (trinagle, square, pentagon, hexagon), for example:
```
pentagon 1
```