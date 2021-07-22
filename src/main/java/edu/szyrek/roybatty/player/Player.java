package edu.szyrek.roybatty.player;

import edu.szyrek.roybatty.macro.Macro;
import edu.szyrek.roybatty.macro.entry.MacroEntry;

import java.awt.AWTException;
import java.awt.Robot;

public class Player implements Runnable {
    final Macro macro;

    public volatile boolean running = false;

    public Player(final Macro macro) {
        this.macro = macro;
    }

    @Override
    public void run() {
        try {
            final Robot bot = new Robot();
            while (running) {
                for (final MacroEntry entry: macro.getEntries()) {
                    if (!running)
                        break;
                    entry.performEntry(bot);
                }
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        running = false;
    }
}