package edu.szyrek.roybatty.macro.entry;

import edu.szyrek.roybatty.RoyBatty;

import java.awt.*;

public class KeyPressEntry extends KeyEntry implements MacroEntry {
    public KeyPressEntry(int key, int time) {
        super(key, time);
    }

    public KeyPressEntry(String string) {
        super(string);
    }

    protected char getLetter() {
        return 'K';
    }

    @Override
    public void performEntry(final Robot bot) {
        try {
            Thread.sleep(this.time);
        } catch (InterruptedException e) {
            RoyBatty.logError(e.getMessage());
            e.printStackTrace();
        }
        bot.keyPress(key);
    }
}
