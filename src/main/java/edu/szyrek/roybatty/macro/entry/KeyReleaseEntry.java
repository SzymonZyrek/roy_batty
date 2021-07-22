package edu.szyrek.roybatty.macro.entry;

import edu.szyrek.roybatty.RoyBatty;

import java.awt.*;

public class KeyReleaseEntry extends KeyEntry implements MacroEntry {
    public KeyReleaseEntry(int key, int time) {
        super(key, time);
    }

    public KeyReleaseEntry(String string) {
        super(string);
    }

    protected char getLetter() {
        return 'k';
    }

    @Override
    public void performEntry(final Robot bot) {
        try {
            Thread.sleep(this.time);
        } catch (InterruptedException e) {
            RoyBatty.logError(e.getMessage());
            e.printStackTrace();
        }
        bot.keyRelease(key);
    }
}
