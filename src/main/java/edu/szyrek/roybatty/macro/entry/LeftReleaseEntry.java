package edu.szyrek.roybatty.macro;

import edu.szyrek.roybatty.RoyBatty;

import java.awt.*;
import java.awt.event.InputEvent;

public class LeftReleaseMacro extends MouseMacro {

    public LeftReleaseMacro(int x, int y, int time) {
        super(x, y, time);
    }

    public LeftReleaseMacro(final String fromString) {
        super(fromString);
    }

    @Override
    protected char getLetter() {
        return 'l';
    }

    @Override
    public void performEntry(final Robot bot) {
        try {
            Thread.sleep(this.time);
        } catch (InterruptedException e) {
            RoyBatty.logError(e.getMessage());
            e.printStackTrace();
        }
        bot.mouseMove(this.x, this.y);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
