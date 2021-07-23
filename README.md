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

## Building:

    mvn clean install
	
## Running:

    java -jar target/roy_batty.jar

## Usage:

- record some macros on "Recording" tab
- save them using "Save" button
- test your macros using "Play" functionality
- assign your macros to key combinations on "Hotkeys" tab
- use active hotkeys to replay macros on demand
- save hotkeys tab to restore it on app restart
- use "Config" tab for configurations

## Tips:

- you must set macro to "active" on assignments tab in order to arm it
- re-triggering hotkey combination stops running macro
- "repeat" checkbox makes macro run until re-triggered  
- you can edit roy_batty.conf manually
- you can supply alternative config file with -c option
- you can manually create/edit hotkey assignments, by default they're in ./assignments.conf
- you can manually edid macro files, by default they're in ./macros/ folder