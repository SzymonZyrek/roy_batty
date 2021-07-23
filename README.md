# mouse/keyboard macro recorder/player and hotkey mapper

## Features:

- recording keyboard/mouse macros
- saving/loading macros from files
- mapping macros to hotkey combinations

## Pre-requisites

- java 1.8

## Releases:

### 0.0.1

https://bitbucket.org/Mexorsu/roy_batty/downloads/roy_batty.jar
	
## Running:

    java -jar roy_batty.jar [-c <config_override_path>]
    
or just double-click roy_batty.jar

## Usage:

1. record some macros on "Recording" tab

![alt Record tab](https://bitbucket.org/Mexorsu/roy_batty/downloads/record_screen.png)

2. save them using "Save" button
3. test your macros using "Play" functionality
4. assign your macros to key combinations on "Hotkeys" tab

![alt Hotkeys tab](https://bitbucket.org/Mexorsu/roy_batty/downloads/hotkeys_screen.png)

5. use active hotkeys to replay macros on demand
6. save hotkeys tab to restore it on app restart
7. use "Config" tab for configurations

![alt Config tab](https://bitbucket.org/Mexorsu/roy_batty/downloads/config_screen.png)



## Building:

To build you're own release you are going to need jdk 1.8 and maven.

1. download source package from https://bitbucket.org/Mexorsu/roy_batty/downloads/ or use git to get source
2. cd to projects root directory
3. run ```mvn clean install```
4. built packages path is ./target/roy_batty.jar

## Tips:

- you must set macro to "active" on assignments tab in order to arm it
- re-triggering hotkey combination stops running macro
- "repeat" checkbox makes macro run until re-triggered  
- you can edit roy_batty.conf manually
- you can supply alternative config file with -c option
- you can manually create/edit hotkey assignments, by default they're in ./assignments.conf
- you can manually edid macro files, by default they're in ./macros/ folder