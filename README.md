# Roy Batty

> **Historical desktop-automation project**
>
> A Java utility for recording and replaying keyboard/mouse macros and binding them to hotkeys.

## What it does

The application supports:

- recording keyboard and mouse actions;
- replaying recorded macros;
- saving and loading macros from files;
- assigning macros to keyboard shortcuts;
- stopping a running macro by triggering its hotkey again.

This was a practical automation experiment rather than a framework exercise: build a small end-user tool around global input events, persistence and replay.

## Build

```bash
mvn clean install
```

## Run

```bash
java -jar target/roy_batty.jar
```

## Typical workflow

1. Record a macro in the **Recording** tab.
2. Save it.
3. Test it with **Play**.
4. Assign it to a hotkey.
5. Trigger the hotkey to replay it on demand.

## Status

Historical project, preserved as part of my older desktop/tooling experiments. It is not maintained for current operating systems or Java versions.
