## Meet ```roy_batty```, a lightweight, cross-platform keyboard and mouse macro manager

## Features:

- Recording of arbitrary keyboard/mouse user input
- Saving/loading it in form of simple text macro files
- Mapping createad files to trigger key combinations

## Pre-requisites

- Running:
```jre 1.8```
- Building:
```jdk 1.8```, ```maven```

## Releases:

```0.0.1``` 
https://bitbucket.org/Mexorsu/roy_batty/downloads/roy_batty.jar

## Running:

    java -jar roy_batty.jar [-c <config_override_path>]
    
or just double-click roy_batty.jar

## Usage:

1. Record some input using ```Recording``` tab

![alt Record tab](https://bitbucket.org/Mexorsu/roy_batty/downloads/record_screen.png)

2. Save it to a macro with ```Save``` button
3. Test your macros using ```Play``` button
4. Assign your macros to key combinations on ```Hotkeys``` tab

![alt Hotkeys tab](https://bitbucket.org/Mexorsu/roy_batty/downloads/hotkeys_screen.png)

5. Use active hotkey combinations to replay macros on demand
6. Save ```Hotkeys``` configurations with ```Save``` button to automatically restore it on app restart
7. Use ```Config``` tab for configurations

![alt Config tab](https://bitbucket.org/Mexorsu/roy_batty/downloads/config_screen.png)



## Building:

To build you're own release you are going to need ```jdk 1.8``` and ```maven```.

1. download source package from https://bitbucket.org/Mexorsu/roy_batty/downloads/ or use ```git``` to get source
2. cd to projects root directory
3. run ```mvn clean install```
4. built packages path is ```./target/roy_batty.jar```

## Tips:

- you need to set macro to ```active``` on ```Assignments``` tab in order to arm it
- re-triggering hotkey combination stops running macro
- ```repeat``` checkbox makes macro run until re-triggered
- you can use pre-created of network-shared macros by copying them to ```./macros``` dir
- you can edit ```roy_batty.conf``` manually
- tweaking configuration (especially by manually editing config file) may sometimes require app restart for new values to "kick in"
- you can supply alternative config file with ```[-c <config_file_path>]``` option
- you can manually create/edit hotkey assignments, by default they're in ```./assignments.conf```
- you can manually edit macro files, by default they're in ```./macros/``` folder. legal entries are:

```
    M <X> <Y> <T>
    R <T>
    r <T>
    L <T>
    l <T>
    W <V> <T> 
    K <V> <T>
    k <V> <T>
```

where: 
- ```M``` is mouse movement action, where ```[X,Y]``` are the target position coordinates
- ```R,r``` are right mouse button clicked and released actions, respectively
- ```L,l``` are left mouse button clicked and released actions, respectively
- ```W``` is mouse wheel movement action, where ```<V>``` is movement amount (can be negative or positive)
- ```K,k``` are keyboard keys pressed and released actions, respectively, where ```<V>``` is a key code
- ```<T>``` is time which given action should take, in milliseconds
