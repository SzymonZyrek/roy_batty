package edu.szyrek.roybatty.macro.entry;

import edu.szyrek.roybatty.RoyBatty;

import java.awt.*;
import java.awt.event.InputEvent;

public class RightReleaseEntry extends MouseEntry {
    public RightReleaseEntry(int x, int y, int time) {
        super(x, y, time);
    }

    public RightReleaseEntry(final String fromString) {
        super(fromString);
    }

    @Override
    protected char getLetter() {
        return 'r';
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
        bot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
    }
}
