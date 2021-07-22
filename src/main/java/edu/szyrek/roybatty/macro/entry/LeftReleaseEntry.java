package edu.szyrek.roybatty.macro.entry;

import edu.szyrek.roybatty.RoyBatty;

import java.awt.*;
import java.awt.event.InputEvent;

public class LeftReleaseEntry extends MouseEntry {

    public LeftReleaseEntry(int x, int y, int time) {
        super(x, y, time);
    }

    public LeftReleaseEntry(final String fromString) {
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
