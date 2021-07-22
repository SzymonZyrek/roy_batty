package edu.szyrek.roybatty.macro;

import edu.szyrek.roybatty.RoyBatty;

import java.awt.*;

public class KeyReleaseMacro extends KeyMacro implements MacroEntry {
    public KeyReleaseMacro(int key, int time) {
        super(key, time);
    }

    public KeyReleaseMacro(String string) {
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
