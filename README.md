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
>>> go <numbers of steps>
```
For example:
```
>>> go 10
```

To turn left type:
```
>>> left
```

To turn right type:
```
>>> right
```