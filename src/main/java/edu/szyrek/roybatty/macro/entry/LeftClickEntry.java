package edu.szyrek.roybatty.macro.entry;

import edu.szyrek.roybatty.RoyBatty;

import java.awt.*;
import java.awt.event.InputEvent;

public class LeftClickEntry extends MouseEntry {

    public LeftClickEntry(int x, int y, int time) {
        super(x, y, time);
    }

    public LeftClickEntry(final String fromString) {
        super(fromString);
    }

    @Override
    protected char getLetter() {
        return 'L';
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
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    }
}
