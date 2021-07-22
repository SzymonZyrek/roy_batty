package edu.szyrek.roybatty.macro;

import edu.szyrek.roybatty.RoyBatty;

import java.awt.*;

public class KeyPressMacro extends KeyMacro implements MacroEntry {
    public KeyPressMacro(int key, int time) {
        super(key, time);
    }

    public KeyPressMacro(String string) {
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
