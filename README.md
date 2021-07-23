# mouse/keyboard macro recorder/player and hotkey mapper

## Features:
- recording keyboard/mouse macros
- saving/loading macros from files
- mapping macros to hotkey combinations

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

- re-triggering hotkey combination stops running macro
- you can edit roy_batty.conf manually
- you can supply alternative config file with -c option
- you can manually create/edit hotkey assignments, by default they're in assignments.conf